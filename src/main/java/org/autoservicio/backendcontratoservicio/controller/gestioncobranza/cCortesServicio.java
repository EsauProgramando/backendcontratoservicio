package org.autoservicio.backendcontratoservicio.controller.gestioncobranza;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CorteServicioModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesenvioListadofiltro;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesfiltroEnvio;
import org.autoservicio.backendcontratoservicio.response.ClienteCortesPentientesRequest;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.autoservicio.backendcontratoservicio.service.GoogleDriveService;
import org.autoservicio.backendcontratoservicio.service.SParamae;
import org.autoservicio.backendcontratoservicio.service.gestioncobranza.CorteServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corte_servicio")
public class cCortesServicio {
    private final CorteServicioService service;
    @Autowired
    private SParamae service_paramae;
    @Autowired
    private GoogleDriveService googleDriveService;
    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrarcorteservicio(
            @PathVariable Integer op,
            @RequestBody CorteServicioModel form
    ) {
        return this.service.registrarcorteservicio(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @PostMapping("/buscar_pendiente_cortes")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<ClienteCortesPentientesRequest>>>> obtener_cortes_pendientes_filtro(
            @RequestBody CortesfiltroEnvio ob
    ) {
        return this.service.obtener_cortes_pendientes_filtro(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }
    @PostMapping("/buscar_cortes_listado")
    public @ResponseBody Mono<ResponseEntity<genericModel<List<CortesServicioRequest>>>> obtener_cortes_listados(
            @RequestBody CortesenvioListadofiltro ob
    ) {
        return this.service.obtener_cortes_listados(ob)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }
    @GetMapping("/buscar_cortes_id/{id_corte}")
    public @ResponseBody Mono<ResponseEntity<genericModel<CortesServicioRequest>>> obtener_cortes
            (@PathVariable Integer id_corte) {
        return this.service.obtener_cortes_id_corte(id_corte)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError(error -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);

    }
}
