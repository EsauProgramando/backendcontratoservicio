package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ICoberturaRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CoberturaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CoberturaService {
    @Autowired
    private ICoberturaRepo repo;

    public Mono<List<CoberturaModel>> listaCoberturas() {
        return Mono.fromCallable(() -> this.repo.listaCoberturas());

    }
}
