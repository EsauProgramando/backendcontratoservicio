package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CallesModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipocalleModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.CallesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CallesService {
    @Autowired
    private CallesRepository repo;

    public Mono<List<CallesModel>> listadocalles() {
        return Mono.fromCallable(() -> this.repo.listadocalles());

    }
    public Mono<responseModel> registrarcalles(Integer op, CallesModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarcalles(op,obj));
    }
    public Mono<List<TipocalleModel>> listadotipocalles() {
        return Mono.fromCallable(() -> this.repo.listadotipocalles());

    }
    public Mono<responseModel> registrartipocalles(Integer op, TipocalleModel obj) {
        return Mono.fromCallable(() -> this.repo.registrartipocalles(op,obj));
    }
}
