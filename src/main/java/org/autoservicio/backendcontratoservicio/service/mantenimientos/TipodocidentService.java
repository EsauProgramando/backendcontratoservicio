package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipodocidentModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.TipodocidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TipodocidentService {
    @Autowired
    private TipodocidentRepository repo;

    public Mono<List<TipodocidentModel>> listadotipodocident() {
        return Mono.fromCallable(() -> this.repo.listadotipodocident());

    }
}
