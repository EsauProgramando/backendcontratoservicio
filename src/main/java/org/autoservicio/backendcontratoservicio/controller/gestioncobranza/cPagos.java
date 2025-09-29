package org.autoservicio.backendcontratoservicio.controller.gestioncobranza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CorteServicioModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosEnvio;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosRevisionEnvio;
import org.autoservicio.backendcontratoservicio.service.GoogleDriveService;
import org.autoservicio.backendcontratoservicio.service.SParamae;
import org.autoservicio.backendcontratoservicio.service.gestioncobranza.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.FileOutputStream;
import java.util.Base64;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pagos")
public class cPagos {
    private final PagosService service;
    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private SParamae service_paramae;

    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarpagos(
            @PathVariable Integer op,
            @RequestBody PagosEnvio form
    ) {
        return service_paramae.buscar_x_ID("DRV", "CONTRA")
                .flatMap(param -> Mono.fromCallable(() -> {
                    String folderId = param.getValorstring();

                    if (form.getAdjunto_boleta() != null && !form.getAdjunto_boleta().isBlank()) {
                        String[] parts = form.getAdjunto_boleta().split(",", 2);
                        String metadata = parts.length > 1 ? parts[0] : "";
                        String base64Data = parts.length > 1 ? parts[1] : parts[0];
                        String extension = detectarExtension(metadata);

                        byte[] fileBytes = Base64.getDecoder().decode(base64Data);
                        java.io.File tempFile = java.io.File.createTempFile("pago_boleta_", "." + extension);
                        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                            fos.write(fileBytes);
                        }

                        String webViewLink = googleDriveService.uploadFile(tempFile, folderId);
                        String fileId = extractFileId(webViewLink);

                        form.setAdjunto_boleta(webViewLink);
                        form.setId_adjuntaboleta(fileId);

                        tempFile.delete();
                    }

                    return form; // devuelve PagosEnvio
                }).subscribeOn(Schedulers.boundedElastic()))
                .flatMap(pagosProcesado -> service.registrarpagos(op, pagosProcesado)) // ahora devuelve Mono<genericModel<responseModel>>
                .flatMap(GenericoException::success) // convierte a Mono<ResponseEntity<genericModel<responseModel>>>
                .doOnSuccess(r -> log.info("Operación exitosa"))
                .doOnError(err -> log.error("Error en Operación", err))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrar_revision/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarpagos_revision(
            @PathVariable Integer op,
            @RequestBody PagosRevisionEnvio form
    ) {
        return this.service.registrarpagos_revision(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    // Utilidad para detectar extensión por metadata del data URI
    private String detectarExtension(String metadata) {
        if (metadata == null) return "bin";
        if (metadata.contains("application/pdf")) return "pdf";
        if (metadata.contains("image/png")) return "png";
        if (metadata.contains("image/jpeg")) return "jpg";
        return "bin";
    }

    private String extractFileId(String url) {
        String regex = "[-\\w]{25,}"; // IDs de Drive ~25+ chars
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group() : null;
    }

}
