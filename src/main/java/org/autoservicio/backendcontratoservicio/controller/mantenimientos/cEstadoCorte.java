package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadoCorteModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.EstadoCorteServicie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/estado_corte")
@RequiredArgsConstructor
public class cEstadoCorte {
    private final EstadoCorteServicie service;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<EstadoCorteModel>>>> obtener_listadoestadoCorte() {
        return this.service.listadoestadoCorte()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
