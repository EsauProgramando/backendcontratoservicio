package org.autoservicio.backendcontratoservicio.controller.gestioncobranza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BajaMorosidadModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BusquedaMorosidad;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesenvioListadofiltro;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.autoservicio.backendcontratoservicio.response.ListadomorosidadRequest;
import org.autoservicio.backendcontratoservicio.service.gestioncobranza.BajaMorosidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baja_morosidad")
public class cBajaMorosidad {
    private final BajaMorosidadService service;

    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarMorosidad(
            @PathVariable Integer op,
            @RequestBody BajaMorosidadModel form
    ) {
        return this.service.registrarMorosidad(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/actualizarestado")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> actualizarreapertura(
            @RequestBody BajaMorosidadModel form
    ) {
        return this.service.actualizarEstado(form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    @PostMapping("/buscar_morosidad")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<ListadomorosidadRequest>>>> busqueda_morosidad(
            @RequestBody BusquedaMorosidad ob
    ) {
        return this.service.busqueda_morosidad(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }

}
