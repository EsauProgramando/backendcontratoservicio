package org.autoservicio.backendcontratoservicio.controller.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_materialModel;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.TecnicoService;
import org.autoservicio.backendcontratoservicio.service.mantenimientos.Tipo_materialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tipo-material")
@RequiredArgsConstructor
public class cTipo_material {

    private final Tipo_materialService service;

    @PostMapping("/registrar/{op}")
    public @ResponseBody Mono<ResponseEntity<genericModel<responseModel>>> registrartipo(
            @PathVariable Integer op,
            @RequestBody Tipo_materialModel form
    ) {
        return this.service.registrartipomaterial(op,form)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/buscar/{idtipo}")
    public Mono<ResponseEntity<genericModel<Tipo_materialModel>>> obtenertecnico(
            @PathVariable String idtipo
    ) {
        return this.service.obtenertipomaterial(idtipo)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
    @GetMapping("/listado")
    public Mono<ResponseEntity<genericModel<List<Tipo_materialModel>>>> obtenertecnico(
    ) {
        return this.service.listatipomaterials()
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
