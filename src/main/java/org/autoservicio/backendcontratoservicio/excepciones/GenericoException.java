package org.autoservicio.backendcontratoservicio.excepciones;

import org.autoservicio.backendcontratoservicio.config.genericModel;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class GenericoException {
    public static <D> genericModel<D> buildResponse(boolean success, String message, D data) {
        return genericModel.<D>builder()
                .success(success)
                .mensaje(message)
                .data(data)
                .build();
    }

    public static <R> Mono<ResponseEntity<genericModel<R>>> success(R data) {
        return Mono.just(ResponseEntity.ok().body(buildResponse(true, "EXITO", data)));
    }

    public static <R> Mono<ResponseEntity<genericModel<R>>> error(Throwable e) {
        return Mono.just(ResponseEntity.ok().body(buildResponse(false, e.getMessage(), null)));
    }
}
