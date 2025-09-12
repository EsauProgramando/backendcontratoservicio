package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipoCorteModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.TipoCorteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TipoCorteService {
    @Autowired
    private TipoCorteRepository repo;

    public Mono<List<TipoCorteModel>> listadotipocorte() {
        return Mono.fromCallable(() -> this.repo.listadotipocorte());

    }
}
