package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CallesModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipocalleModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.CallesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calles")
@RequiredArgsConstructor
public class cCalles {
    private final CallesService service;


    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<CallesModel>>>> obtener_listadocalles() {
        return this.service.listadocalles()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarcalles(
            @PathVariable Integer op,
            @RequestBody CallesModel form
    ) {
        return this.service.registrarcalles(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/listartipocalle")
    public Mono<ResponseEntity<genericModel<List<TipocalleModel>>>> obtener_listadotipocalles() {
        return this.service.listadotipocalles()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrartipocalle/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrartipocalles(
            @PathVariable Integer op,
            @RequestBody TipocalleModel form
    ) {
        return this.service.registrartipocalles(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
