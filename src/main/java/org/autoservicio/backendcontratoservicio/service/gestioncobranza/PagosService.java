package org.autoservicio.backendcontratoservicio.service.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosEnvio;
import org.autoservicio.backendcontratoservicio.repository.gestioncobranza.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PagosService {
    @Autowired
    private PagosRepository repo;

    public Mono<responseModel> registrarpagos(Integer op, PagosEnvio obj) {
        return Mono.fromCallable(() -> this.repo.registrarpagos(op,obj));
    }
}
