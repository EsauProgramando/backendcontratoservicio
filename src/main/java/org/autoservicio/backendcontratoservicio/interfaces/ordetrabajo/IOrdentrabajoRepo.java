package org.autoservicio.backendcontratoservicio.interfaces.ordetrabajo;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;

import java.util.List;

public interface IOrdentrabajoRepo {
    responseModel registrarordentrabajo(Integer op, OrdentrabajoModel obj);
    OrdentrabajoModel obtenerordentrabajo(String idordentrabajo);
    List<OrdentrabajoModel> listaordentrabajos();
}
