package org.autoservicio.backendcontratoservicio.service.mantenimientos;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_servicioModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.Tipo_servicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class Tipo_serviceService {
    @Autowired
    private Tipo_servicioRepository repo;

    public Mono<List<Tipo_servicioModel>> listadotipo_servicio() {
        return Mono.fromCallable(() -> this.repo.listadotipo_servicio());
    }

    public Mono<responseModel> registrarTipo_Servicio(Integer op, Tipo_servicioModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarTipo_Servicio(op,obj));
    }
}
