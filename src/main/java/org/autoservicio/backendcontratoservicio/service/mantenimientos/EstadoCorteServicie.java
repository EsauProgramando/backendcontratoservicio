package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadoCorteModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.EstadoCorteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EstadoCorteServicie {
    @Autowired
    private EstadoCorteRepository repo;

    public Mono<List<EstadoCorteModel>> listadoestadoCorte() {
        return Mono.fromCallable(() -> this.repo.listadEstadoCorte());

    }
}
