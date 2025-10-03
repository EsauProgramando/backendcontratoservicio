package org.autoservicio.backendcontratoservicio.interfaces.ordetrabajo;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;

import java.util.List;

public interface IOrdentrabajoRepo {
    responseModel registrarordentrabajo(Integer op, OrdentrabajoModel obj);
    OrdentrabajoModel obtenerordentrabajo(String idordentrabajo);
    List<OrdentrabajoModel> listaordentrabajos(ListaOrdenRequest request);
    List<OrdentrabajoModel> obtenerordentrabajo_historial(String idordentrabajo);
    List<OrdentrabajoModel> obtener_x_estado_tecnico(String estado, String idtecnico);
    List<OrdentrabajoModel> obtener_x_estado_ejecucion_tecnico(String estado, String idtecnico);
    List<OrdentecnicoModel> obtener_reporte_x_estado_tecnico(String estado, String idtecnico);
}
