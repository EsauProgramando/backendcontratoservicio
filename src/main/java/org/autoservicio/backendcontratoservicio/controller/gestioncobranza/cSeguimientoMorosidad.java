package org.autoservicio.backendcontratoservicio.controller.gestioncobranza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Clientes_morosidad_extModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Detalle_facturas_moraModel;
import org.autoservicio.backendcontratoservicio.response.Clientes_morosidad_ext;
import org.autoservicio.backendcontratoservicio.response.Detalle_facturas_mora;
import org.autoservicio.backendcontratoservicio.response.Sp_kpis_mora_total;
import org.autoservicio.backendcontratoservicio.service.gestioncobranza.SeguimientoMorosidadServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seguimiento_morosidad")
public class cSeguimientoMorosidad {
    private final SeguimientoMorosidadServicio service;

    @PostMapping("/buscar_clientes_morosidad")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<Clientes_morosidad_ext>>>> Clientes_morosidad_extModel(
            @RequestBody Clientes_morosidad_extModel ob
    ) {
        return this.service.Clientes_morosidad_extModel(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }

    @GetMapping("/sp_kpis_mora_total/{solo_con_saldo}")
    public @ResponseBody Mono<ResponseEntity<genericModel<Sp_kpis_mora_total>>> kpis_mora_total
            (@PathVariable Integer solo_con_saldo) {
        return this.service.kpis_mora_total(solo_con_saldo)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }

    @PostMapping("/buscar_detalle_facturas_mora")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<Detalle_facturas_mora>>>> detalle_facturas_mora(
            @RequestBody Detalle_facturas_moraModel ob
    ) {
        return this.service.detalle_facturas_mora(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
