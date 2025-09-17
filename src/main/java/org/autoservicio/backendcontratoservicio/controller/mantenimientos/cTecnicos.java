package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.TecnicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tecnicos")
@RequiredArgsConstructor
public class cTecnicos {
    private final TecnicoService service;

    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarbitacura(
            @PathVariable Integer op,
            @RequestBody TecnicoModel form
    ) {
        return this.service.registrartecnico(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/buscar/{idtecnico}")
    public Mono<ResponseEntity<genericModel<TecnicoModel>>> obtenertecnico(
            @PathVariable String idtecnico
    ) {
        return this.service.obtenertecnico(idtecnico)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/listado")
    public Mono<ResponseEntity<genericModel<List<TecnicoModel>>>> obtenertecnico(
    ) {
        return this.service.listatecnicos()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
