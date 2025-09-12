package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.HistorialServicioModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.HistorialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/historial_servicio")
@RequiredArgsConstructor
public class cHistorialServicio {
    private final HistorialService service;

    @PostMapping("/registrar")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarRegistrar_historial(
            @RequestBody HistorialServicioModel form
    ) {
        return this.service.registrarRegistrar_historial(form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    @GetMapping("/obtener_historial/{id_cliente}")
    public Mono<ResponseEntity<genericModel<java.util.List<HistorialServicioModel>>>> obterhistorial_porcliente(
            @PathVariable Integer id_cliente
    ) {
        return this.service.obterhistorial_porcliente(id_cliente)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
