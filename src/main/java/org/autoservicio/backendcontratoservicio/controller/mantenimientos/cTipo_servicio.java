package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_servicioModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.Tipo_serviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tipo_servicio")
@RequiredArgsConstructor
public class cTipo_servicio {
    private final Tipo_serviceService service;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<Tipo_servicioModel>>>> obtener_listadotipo_servicio() {
        return this.service.listadotipo_servicio()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarTipo_Servicio(
            @PathVariable Integer op,
            @RequestBody Tipo_servicioModel form
    ) {
        return this.service.registrarTipo_Servicio(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
