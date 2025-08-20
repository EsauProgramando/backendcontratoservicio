package org.autoservicio.backendcontratoservicio.service.mantenimientos;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Plan_servicioModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.Plan_servicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class Plan_servicioService {
    @Autowired
    private Plan_servicioRepository repo;

    public Mono<List<Plan_servicioModel>> listadoPlan_servicio() {
        return Mono.fromCallable(() -> this.repo.listadoPlan_servicio());
    }

    public Mono<responseModel> registrarPlan_servicio(Integer op, Plan_servicioModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarPlan_servicio(op,obj));
    }
}
