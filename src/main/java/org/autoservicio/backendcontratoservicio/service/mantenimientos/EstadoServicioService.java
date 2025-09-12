package org.autoservicio.backendcontratoservicio.service.mantenimientos;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadosServicioModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Plan_servicioModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.EstadoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EstadoServicioService {
    @Autowired
    private EstadoServicioRepository repo;

    public Mono<List<EstadosServicioModel>> listadoEstadosServicio() {
        return Mono.fromCallable(() -> this.repo.listadoEstadosServicio());
    }
}
