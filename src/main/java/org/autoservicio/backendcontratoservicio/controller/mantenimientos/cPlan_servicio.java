package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Plan_servicioModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.Plan_servicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/plan_servicio")
@RequiredArgsConstructor
public class cPlan_servicio {
    private final Plan_servicioService service;

    @GetMapping("/listar")
    public Mono<ResponseEntity<genericModel<List<Plan_servicioModel>>>> obtener_listadoPlan_servicio() {
        return this.service.listadoPlan_servicio()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarPlan_servicio(
            @PathVariable Integer op,
            @RequestBody Plan_servicioModel form
    ) {
        return this.service.registrarPlan_servicio(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
