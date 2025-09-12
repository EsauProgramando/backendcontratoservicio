package org.autoservicio.backendcontratoservicio.controller.gestionclientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.DriveFileInfo;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.ContratosModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipocalleModel;
import org.autoservicio.backendcontratoservicio.response.Detalle_contratoxservicioRequest;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.autoservicio.backendcontratoservicio.service.GoogleDriveService;
import org.autoservicio.backendcontratoservicio.service.SParamae;
import org.autoservicio.backendcontratoservicio.service.gestionclientes.ContratosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/api/contratos_x_servicio")
@RequiredArgsConstructor
public class cContratos {
    private final ContratosService service;
    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private SParamae service_paramae;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<ServicioContratadoRequest>>>> obtener_listadosContratos() {
        return this.service.listadosContratos()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/detalle_contratos_x_servicio/{id_cliente}/{id_contrato}")
    public Mono<ResponseEntity<genericModel<Detalle_contratoxservicioRequest>>> buscar_contratoxservicio(
            @PathVariable Integer id_cliente,
            @PathVariable Integer id_contrato
    ) {
        return service.buscar_contratoxservicio(id_cliente, id_contrato)
                .flatMap(GenericoException::success)
                .doOnSuccess(resp -> log.info("Operación exitosa"))
                .doOnError(err -> log.error("Error en operación: {}", err.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrar/{op}")
    public Mono<ResponseEntity<genericModel<responseModel>>> registrarContrato(
            @PathVariable Integer op,
            @RequestPart("form") String contratoJson,
            @RequestPart(value = "fileContrato", required = false) MultipartFile fileContrato,
            @RequestPart(value = "fileDocumento", required = false) MultipartFile fileDocumento,
            @RequestPart(value = "fileCroquis", required = false) MultipartFile fileCroquis
    ) {
        return Mono.fromCallable(() -> new ObjectMapper().readValue(contratoJson, ContratosModel.class))
                .flatMap(contrato ->
                        service_paramae.buscar_x_ID("DRV", "CONTRA")
                                .flatMap(param -> Mono.fromCallable(() -> {
                                    String folderId = param.getValorstring();
                                    String prefijo = contrato.getId_contrato() + "_" + contrato.getId_cliente() + "_";
                                    // fileContrato
                                    if (fileContrato != null && !fileContrato.isEmpty()) {
                                        java.io.File tempFile = java.io.File.createTempFile(prefijo + "contrato-", fileContrato.getOriginalFilename());
                                        fileContrato.transferTo(tempFile);

                                        String webViewLink = googleDriveService.uploadFile(tempFile, folderId);
                                        String fileId = extractFileId(webViewLink);

                                        contrato.setUrl_soporte_contrato(webViewLink);
                                        contrato.setId_soporte_contrato(fileId);

                                        tempFile.delete();
                                    }

// fileDocumento
                                    if (fileDocumento != null && !fileDocumento.isEmpty()) {
                                        java.io.File tempFile = java.io.File.createTempFile(prefijo + "documento-", fileDocumento.getOriginalFilename());
                                        fileDocumento.transferTo(tempFile);

                                        String webViewLink = googleDriveService.uploadFile(tempFile, folderId);
                                        String fileId = extractFileId(webViewLink);

                                        contrato.setUrl_documento(webViewLink);
                                        contrato.setId_documento(fileId);

                                        tempFile.delete();
                                    }

// fileCroquis
                                    if (fileCroquis != null && !fileCroquis.isEmpty()) {
                                        java.io.File tempFile = java.io.File.createTempFile(prefijo + "croquis-", fileCroquis.getOriginalFilename());
                                        fileCroquis.transferTo(tempFile);

                                        String webViewLink = googleDriveService.uploadFile(tempFile, folderId);
                                        String fileId = extractFileId(webViewLink);

                                        contrato.setUrl_croquis(webViewLink);
                                        contrato.setId_croquis(fileId);

                                        tempFile.delete();
                                    }
                                    return contrato;

                                }).subscribeOn(Schedulers.boundedElastic()))
                )
                .flatMap(c -> service.registrarcontrato(op, c))
                .flatMap(GenericoException::success)
                .doOnSuccess(r -> log.info("Operación exitosa"))
                .doOnError(err -> log.error("Error en Operación: {}", err.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    //extra extraer id de la imagen

    String extractFileId(String url) {
        String regex = "[-\\w]{25,}"; // Los IDs de Drive son de 25+ caracteres (letras, números, guiones, guiones bajos)
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    @GetMapping("/generar-facturas/{id_contrato}/{id_cliente}")
    public Mono<ResponseEntity<genericModel<responseModel>>> generar_facturas_contrato(
            @PathVariable Integer id_contrato,
            @PathVariable Integer id_cliente) {
        return this.service.generar_facturas_contrato(id_contrato,id_cliente)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    @GetMapping("/mostrar")
    public ResponseEntity<byte[]> mostrarImagen(@RequestParam String id) {
        String urlDrive = "https://drive.google.com/uc?export=view&id=" + id;
        try {
            byte[] imagenBytes = new RestTemplate().getForObject(urlDrive, byte[].class);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(imagenBytes);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
