package org.autoservicio.backendcontratoservicio.controller.ordentrabajo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.TecnicoService;
import org.autoservicio.backendcontratoservicio.service.ordentrabajo.OrdentrabajoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/orden-trabajo")
@RequiredArgsConstructor
public class cOrdentrabajo {
    private final OrdentrabajoService service;

    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarbitacura(
            @PathVariable Integer op,
            @RequestBody OrdentrabajoModel form
    ) {
        return this.service.registrarordentrabajo(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/buscar/{idordentrabajo}")
    public Mono<ResponseEntity<genericModel<OrdentrabajoModel>>> obtenertecnico(
            @PathVariable String idordentrabajo
    ) {
        return this.service.obtenerordentrabajo(idordentrabajo)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/listado")
    public Mono<ResponseEntity<genericModel<List<OrdentrabajoModel>>>> obtenertecnico(
    ) {
        return this.service.listaordentrabajos()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/buscar-historial/{idordentrabajo}")
    public Mono<ResponseEntity<genericModel<List<OrdentrabajoModel>>>> obtenerordentrabajo_historial(
            @PathVariable String idordentrabajo
    ) {
        return this.service.obtenerordentrabajo_historial(idordentrabajo)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
