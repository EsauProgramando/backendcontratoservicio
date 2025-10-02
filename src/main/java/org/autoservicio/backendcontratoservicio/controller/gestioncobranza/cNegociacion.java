package org.autoservicio.backendcontratoservicio.controller.gestioncobranza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BuscarNegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.NegociacionModel;
import org.autoservicio.backendcontratoservicio.response.Negociacion_clientesRequest;
import org.autoservicio.backendcontratoservicio.service.gestioncobranza.NegociacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/negociacion")
public class cNegociacion {

    private final NegociacionService service;

    @PostMapping("/guardar_negociacion")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> generarnegociacion(
            @RequestBody NegociacionModel form
    ) {
        return this.service.generarnegociacion(form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    @PostMapping("/buscar_negociacion")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<Negociacion_clientesRequest>>>> busqueda_negociacion(
            @RequestBody BuscarNegociacion ob
    ) {
        return this.service.busqueda_negociacion(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }

}
