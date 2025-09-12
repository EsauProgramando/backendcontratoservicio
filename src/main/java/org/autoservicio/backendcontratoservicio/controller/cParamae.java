package org.autoservicio.backendcontratoservicio.controller;
import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.autoservicio.backendcontratoservicio.excepciones.GenericoException;
import org.autoservicio.backendcontratoservicio.service.SParamae;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import org.autoservicio.backendcontratoservicio.model.paramaeModel;
@Slf4j
@RestController
@RequestMapping("/api/paramae")
@RequiredArgsConstructor
public class cParamae {
    private final SParamae service_paramae;

    @GetMapping("/{codpar}/{tippar}")
    public Mono<ResponseEntity<genericModel<paramaeModel>>> obtener_listado_parametro(
            @PathVariable String codpar,
            @PathVariable String tippar
    ) {
        return this.service_paramae.buscar_x_ID(codpar,tippar)
                .flatMap(GenericoException::success)
                .doOnSuccess(response -> log.info("Operación exitosa"))
                .doOnError((Throwable error) -> log.error("Error en Operación: {}", error.getMessage()))
                .onErrorResume(GenericoException::error);
    }
}
