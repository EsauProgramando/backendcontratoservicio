package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_materialModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.TecnicoRepository;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.Tipo_materialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class Tipo_materialService {
    @Autowired
    private Tipo_materialRepository repo;
    public Mono<responseModel> registrartipomaterial(Integer op, Tipo_materialModel obj) {
        return Mono.fromCallable(() -> this.repo.registrartipomaterial(op,obj));
    }
    public Mono<Tipo_materialModel> obtenertipomaterial(String idtipomaterial) {
        return Mono.fromCallable(() -> this.repo.obtenertipomaterial(idtipomaterial));
    }
    public Mono<List<Tipo_materialModel>> listatipomaterials() {
        return Mono.fromCallable(() -> this.repo.listatipomaterials());
    }
}
