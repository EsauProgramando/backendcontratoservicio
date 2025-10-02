package org.autoservicio.backendcontratoservicio.service.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BuscarNegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.NegociacionModel;
import org.autoservicio.backendcontratoservicio.repository.gestioncobranza.NegociacionRepository;
import org.autoservicio.backendcontratoservicio.response.Negociacion_clientesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class NegociacionService {
    @Autowired
    private NegociacionRepository repo;

    public Mono<responseModel> generarnegociacion(NegociacionModel obj) {
        return Mono.fromCallable(() -> this.repo.generarnegociacion(obj));
    }
    public Mono<List<Negociacion_clientesRequest>> busqueda_negociacion(BuscarNegociacion enviodatos) {
        return Mono.fromCallable(() -> this.repo.busqueda_negociacion(enviodatos));
    }


}
