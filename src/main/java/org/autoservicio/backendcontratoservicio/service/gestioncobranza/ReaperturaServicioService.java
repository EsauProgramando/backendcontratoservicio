package org.autoservicio.backendcontratoservicio.service.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ReaperturaServicioModel;
import org.autoservicio.backendcontratoservicio.repository.gestioncobranza.ReaperturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReaperturaServicioService {
    @Autowired
    private ReaperturaRepository repo;

    public Mono<responseModel> registrarreapertura(Integer op, ReaperturaServicioModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarreapertura(op,obj));
    }

    public Mono<responseModel> actualizarObservacionReapertura( ReaperturaServicioModel obj) {
        return Mono.fromCallable(() -> this.repo.actualizarObservacionReapertura(obj));
    }
}
