package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CallesModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CoberturaModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.CoberturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cobertura")
@RequiredArgsConstructor
public class cCobertura {
    private final CoberturaService service;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<CoberturaModel>>>> obtener_listaCoberturas() {
        return this.service.listaCoberturas()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
