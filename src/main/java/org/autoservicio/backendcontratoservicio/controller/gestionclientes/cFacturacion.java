package org.autoservicio.backendcontratoservicio.controller.gestionclientes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.Buscarpagosenlinea;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CobranzaEnvio;
import org.autoservicio.backendcontratoservicio.response.FacturacionRequest;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.autoservicio.backendcontratoservicio.service.gestionclientes.FacturacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/facturacion")
@RequiredArgsConstructor
public class cFacturacion {
    private final FacturacionService service;

    @GetMapping("/obtener_facturas_x_contrato/{id_contrato}/{id_cliente}")
public Mono<ResponseEntity<genericModel<List<FacturacionRequest>>>> obtener_facturas_x_contrato(
        @PathVariable Integer id_contrato,
        @PathVariable Integer id_cliente) {
        return this.service.obtener_facturas_x_contrato(id_contrato ,id_cliente)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/buscar_facturas")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<FacturacionRequest>>>> ver_repo_med_instalacion(
            @RequestBody CobranzaEnvio ob
    ) {
        return this.service.obtener_facturas_x_contrato_filtro(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }
    @PostMapping("/buscar_facturas_enlinea")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<FacturacionRequest>>>> obtener_buscar_facturas_pagos_enlinea(
            @RequestBody Buscarpagosenlinea ob
    ) {
        return this.service.obtener_buscar_facturas_pagos_enlinea(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }
}
