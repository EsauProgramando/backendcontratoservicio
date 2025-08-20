package org.autoservicio.backendcontratoservicio.service.gestionclientes;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.ContratosModel;
import org.autoservicio.backendcontratoservicio.repository.gestionclientes.ContratosRepository;
import org.autoservicio.backendcontratoservicio.response.ContratoResponse;
import org.autoservicio.backendcontratoservicio.response.Detalle_contratoxservicioRequest;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
@Service
public class ContratosService {
    @Autowired
    private ContratosRepository repo;

    public Mono<List<ServicioContratadoRequest>> listadosContratos() {
        return Mono.fromCallable(() -> this.repo.listadosContratos());
    }
    public Mono<Detalle_contratoxservicioRequest> buscar_contratoxservicio(
            Integer id_cliente, Integer nrocontrato
    ) {
        return Mono.fromCallable(() -> {
            var cab = repo.buscarpor_contratos(id_cliente, nrocontrato);
            var ite = repo.buscar_servicio_pornrocontrato(nrocontrato);
            return new Detalle_contratoxservicioRequest(cab, ite);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<responseModel> registrarcontrato(Integer op, ContratosModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarcontrato(op,obj));
    }

    public Mono<ContratoResponse> buscarpor_contratos(Integer id_cliente, Integer nrocontrato) {
        return Mono.fromCallable(() -> this.repo.buscarpor_contratos(id_cliente, nrocontrato));
    }

}
