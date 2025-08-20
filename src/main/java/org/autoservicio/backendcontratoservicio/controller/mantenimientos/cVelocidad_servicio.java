package org.autoservicio.backendcontratoservicio.controller.mantenimientos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Velocidad_servicioModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.VelocidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/velocidad_servicio")
@RequiredArgsConstructor
public class cVelocidad_servicio {
    private final VelocidadService service;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<Velocidad_servicioModel>>>> obtener_listadovelocidad() {
        return this.service.listadovelocidad()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarVelidadServicio(
            @PathVariable Integer op,
            @RequestBody Velocidad_servicioModel form
    ) {
        return this.service.registrarVelicidad(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
