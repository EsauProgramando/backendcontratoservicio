package org.autoservicio.backendcontratoservicio.service.mantenimientos;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadosServicioModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.HistorialServicioModel;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.HistorialServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class HistorialService {
    @Autowired
    private HistorialServicioRepository repo;

    public Mono<responseModel> registrarRegistrar_historial(HistorialServicioModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarRegistrar_historial(obj));
    }

    public Mono<List<HistorialServicioModel>> obterhistorial_porcliente(Integer id_cliente) {
        return Mono.fromCallable(() -> this.repo.obterhistorial_porcliente(id_cliente));
    }
}
