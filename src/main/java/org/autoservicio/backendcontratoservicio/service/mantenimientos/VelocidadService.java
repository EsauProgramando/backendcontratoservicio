package org.autoservicio.backendcontratoservicio.service.mantenimientos;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Velocidad_servicioModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.VelocidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;
@Service
public class VelocidadService {
    @Autowired
    private VelocidadRepository repo;

    public Mono<List<Velocidad_servicioModel>> listadovelocidad() {
        return Mono.fromCallable(() -> this.repo.listadovelocidad());
    }

    public Mono<responseModel> registrarVelicidad(Integer op, Velocidad_servicioModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarVelicidad(op,obj));
    }
}
