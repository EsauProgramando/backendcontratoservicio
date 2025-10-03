package org.autoservicio.backendcontratoservicio.service.ordentrabajo;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.repository.ordentrabajo.OrdentrabajoRepository;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrdentrabajoService {
    @Autowired
    private OrdentrabajoRepository repo;
    public Mono<responseModel> registrarordentrabajo(Integer op, OrdentrabajoModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarordentrabajo(op,obj));
    }
    public Mono<OrdentrabajoModel> obtenerordentrabajo(String idordentrabajo) {
        return Mono.fromCallable(() -> this.repo.obtenerordentrabajo(idordentrabajo));
    }
    public Mono<List<OrdentrabajoModel>> listaordentrabajos(ListaOrdenRequest request) {
        return Mono.fromCallable(() -> this.repo.listaordentrabajos(request));
    }
    public Mono<List<OrdentrabajoModel>> obtenerordentrabajo_historial(String idordentrabajo) {
        return Mono.fromCallable(() -> this.repo.obtenerordentrabajo_historial(idordentrabajo));
    }
    public Mono<List<OrdentrabajoModel>> obtener_x_estado_tecnico(String estado,String idtecnico) {
        return Mono.fromCallable(() -> this.repo.obtener_x_estado_tecnico(estado,idtecnico));
    }
    public Mono<List<OrdentrabajoModel>> obtener_x_estado_ejecucion_tecnico(String estado,String idtecnico) {
        return Mono.fromCallable(() -> this.repo.obtener_x_estado_ejecucion_tecnico(estado,idtecnico));
    }
    public Mono<List<OrdentecnicoModel>> obtener_reporte_x_estado_tecnico(String estado, String idtecnico) {
        return Mono.fromCallable(() -> this.repo.obtener_reporte_x_estado_tecnico(estado,idtecnico));
    }
}
