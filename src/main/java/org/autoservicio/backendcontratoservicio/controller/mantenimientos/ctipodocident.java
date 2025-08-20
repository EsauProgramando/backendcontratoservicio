package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipodocidentModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.TipodocidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tipodocident")
@RequiredArgsConstructor
public class ctipodocident {

    private final TipodocidentService service;


    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<TipodocidentModel>>>> obtener_listadotipodocident() {
        return this.service.listadotipodocident()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
