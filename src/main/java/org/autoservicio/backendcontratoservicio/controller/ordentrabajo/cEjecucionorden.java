package org.autoservicio.backendcontratoservicio.controller.ordentrabajo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.EjecucionordenModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.HistorialejecucionModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;
import org.autoservicio.backendcontratoservicio.service.ordentrabajo.EjecucionordenService;
import org.autoservicio.backendcontratoservicio.service.ordentrabajo.OrdentrabajoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ejecucion-ot")
@RequiredArgsConstructor
public class cEjecucionorden {
    private final EjecucionordenService service;
    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarbitacura(
            @PathVariable Integer op,
            @RequestBody EjecucionordenModel form
    ) {
        return this.service.registrarejecucionorden(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/buscar/{idejecucionorden}")
    public Mono<ResponseEntity<genericModel<EjecucionordenModel>>> obtenertecnico(
            @PathVariable String idejecucionorden
    ) {
        return this.service.obtenerejecucionorden(idejecucionorden)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/listado")
    public Mono<ResponseEntity<genericModel<List<EjecucionordenModel>>>> obtenertecnico(
            @RequestBody ListaOrdenRequest request
    ) {
        return this.service.listaejecucionordens(request)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/buscar-historial/{idejecucionorden}")
    public Mono<ResponseEntity<genericModel<List<HistorialejecucionModel>>>> obtenerejecucionorden_historial(
            @PathVariable String idejecucionorden
    ) {
        return this.service.obtenerejecucionorden_historial(idejecucionorden)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
