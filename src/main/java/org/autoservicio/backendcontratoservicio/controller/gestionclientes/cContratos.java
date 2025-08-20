package org.autoservicio.backendcontratoservicio.controller.gestionclientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.ContratosModel;
import org.autoservicio.backendcontratoservicio.response.Detalle_contratoxservicioRequest;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.autoservicio.backendcontratoservicio.service.gestionclientes.ContratosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/contratos_x_servicio")
@RequiredArgsConstructor
public class cContratos {
    private final ContratosService service;
    @Value("${app.upload.dir}")
    private String uploadDir;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<ServicioContratadoRequest>>>> obtener_listadosContratos() {
        return this.service.listadosContratos()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/detalle_contratos_x_servicio/{id_cliente}/{nrocontrato}")
    public Mono<ResponseEntity<genericModel<Detalle_contratoxservicioRequest>>> buscar_contratoxservicio(
            @PathVariable Integer id_cliente,
            @PathVariable Integer nrocontrato
    ) {
        return service.buscar_contratoxservicio(id_cliente, nrocontrato)
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
        return Mono.fromCallable(() -> {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(contratoJson, ContratosModel.class);
                })
                .flatMap(contrato -> {
                    if (op == 2 && contrato.getId_contrato() != null) {
                        // Edición: buscamos el contrato existente
                        return service.buscarpor_contratos(
                                        contrato.getId_cliente(),
                                        contrato.getId_contrato()
                                )
                                .flatMap(existente -> {
                                    try {
                                        // soporte contrato
                                        if (fileContrato != null && !fileContrato.isEmpty()) {
                                            if (existente.getUrl_soporte_contrato() != null)
                                                deleteFile(existente.getUrl_soporte_contrato());
                                            contrato.setUrl_soporte_contrato(saveFile(fileContrato, "contratos"));
                                        } else {
                                            contrato.setUrl_soporte_contrato(existente.getUrl_soporte_contrato());
                                        }

                                        // documento
                                        if (fileDocumento != null && !fileDocumento.isEmpty()) {
                                            if (existente.getUrl_documento() != null)
                                                deleteFile(existente.getUrl_documento());
                                            contrato.setUrl_documento(saveFile(fileDocumento, "documentos"));
                                        } else {
                                            contrato.setUrl_documento(existente.getUrl_documento());
                                        }

                                        // croquis
                                        if (fileCroquis != null && !fileCroquis.isEmpty()) {
                                            if (existente.getUrl_croquis() != null)
                                                deleteFile(existente.getUrl_croquis());
                                            contrato.setUrl_croquis(saveFile(fileCroquis, "croquis"));
                                        } else {
                                            contrato.setUrl_croquis(existente.getUrl_croquis());
                                        }

                                        return Mono.just(contrato);
                                    } catch (IOException e) {
                                        return Mono.error(new RuntimeException("Error guardando archivo", e));
                                    }
                                });
                    } else {
                        // Nuevo registro (op = 1): no buscar, solo guardar archivos
                        try {
                            if (fileContrato != null && !fileContrato.isEmpty())
                                contrato.setUrl_soporte_contrato(saveFile(fileContrato, "contratos"));
                            if (fileDocumento != null && !fileDocumento.isEmpty())
                                contrato.setUrl_documento(saveFile(fileDocumento, "documentos"));
                            if (fileCroquis != null && !fileCroquis.isEmpty())
                                contrato.setUrl_croquis(saveFile(fileCroquis, "croquis"));
                            return Mono.just(contrato);
                        } catch (IOException e) {
                            return Mono.error(new RuntimeException("Error guardando archivo", e));
                        }
                    }
                })
                // Guardamos el contrato en la base de datos
                .flatMap(c -> service.registrarcontrato(op, c))
                .flatMap(GenericoException::success)
                .doOnSuccess(r -> log.info("Operación exitosa"))
                .doOnError(err -> log.error("Error en Operación: {}", err.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    // Guardar archivo con UUID y extensión si existe
    private String saveFile(MultipartFile file, String subDir) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Path dirPath = Paths.get(uploadDir, subDir);
        Files.createDirectories(dirPath);

        // Obtener extensión de forma segura
        String extension = "";
        String originalName = file.getOriginalFilename();
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        // Generar nombre único sin depender del cliente
        String filename = UUID.randomUUID().toString() + extension;

        Path filepath = dirPath.resolve(filename);

        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        return subDir + "/" + filename; // Ruta relativa
    }


    // 🔥 Eliminar archivo físico
    private void deleteFile(String relativePath) {
        try {
            Path path = Paths.get(uploadDir, relativePath);
            Files.deleteIfExists(path);
            log.info("Archivo eliminado: {}", relativePath);
        } catch (IOException e) {
            log.warn("No se pudo eliminar archivo: {}", relativePath, e);
        }
    }

    private String generateFileName(String originalName) {
        String extension = "";
        int i = originalName.lastIndexOf('.');
        if (i >= 0) {
            extension = originalName.substring(i);
        }
        return UUID.randomUUID().toString() + extension;
    }


}
