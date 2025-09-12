package org.autoservicio.backendcontratoservicio.controller.gestioncobranza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ReaperturaServicioModel;
import org.autoservicio.backendcontratoservicio.service.gestioncobranza.ReaperturaServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reapetura_servicio")
public class cReaperturaServicio {
    private final ReaperturaServicioService service;

    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarreapertura(
            @PathVariable Integer op,
            @RequestBody ReaperturaServicioModel form
    ) {
        return this.service.registrarreapertura(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    @PostMapping("/actualizar")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> actualizarreapertura(
            @RequestBody ReaperturaServicioModel form
    ) {
        return this.service.actualizarObservacionReapertura(form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

}
