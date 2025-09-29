package org.autoservicio.backendcontratoservicio.service.ordentrabajo;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.EjecucionordenModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.HistorialejecucionModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.repository.ordentrabajo.EjecucionordenRepository;
import org.autoservicio.backendcontratoservicio.repository.ordentrabajo.OrdentrabajoRepository;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EjecucionordenService {
    @Autowired
    private EjecucionordenRepository repo;
    public Mono<responseModel> registrarejecucionorden(Integer op, EjecucionordenModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarejecucionorden(op,obj));
    }
    public Mono<EjecucionordenModel> obtenerejecucionorden(String idejecucionorden) {
        return Mono.fromCallable(() -> this.repo.obtenerejecucionorden(idejecucionorden));
    }
    public Mono<List<EjecucionordenModel>> listaejecucionordens(ListaOrdenRequest request) {
        return Mono.fromCallable(() -> this.repo.listaejecucions(request));
    }
    public Mono<List<HistorialejecucionModel>> obtenerejecucionorden_historial(String idejecucionorden) {
        return Mono.fromCallable(() -> this.repo.obtenerejecucion_historial(idejecucionorden));
    }
}
