package org.autoservicio.backendcontratoservicio.service.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BajaMorosidadModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BusquedaMorosidad;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesenvioListadofiltro;
import org.autoservicio.backendcontratoservicio.repository.gestioncobranza.BajaMorosidadRepository;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.autoservicio.backendcontratoservicio.response.ListadomorosidadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BajaMorosidadService {
    @Autowired
    private BajaMorosidadRepository repo;

    public Mono<responseModel> registrarMorosidad(Integer op, BajaMorosidadModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarMorosidad(op,obj));
    }

    public Mono<responseModel> actualizarEstado( BajaMorosidadModel obj) {
        return Mono.fromCallable(() -> this.repo.actualizarEstado(obj));
    }

    public Mono<List<ListadomorosidadRequest>> busqueda_morosidad(BusquedaMorosidad enviodatos) {
        return Mono.fromCallable(() -> this.repo.busqueda_morosidad(enviodatos));
    }

}
