package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.BitacoraServicio;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository repo;
    public Mono<responseModel> registrartecnico(Integer op, TecnicoModel obj) {
        return Mono.fromCallable(() -> this.repo.registrartecnico(op,obj));
    }
    public Mono<TecnicoModel> obtenertecnico(String idtecnico) {
        return Mono.fromCallable(() -> this.repo.obtenertecnico(idtecnico));
    }
    public Mono<List<TecnicoModel>> listatecnicos() {
        return Mono.fromCallable(() -> this.repo.listatecnicos());
    }
}
