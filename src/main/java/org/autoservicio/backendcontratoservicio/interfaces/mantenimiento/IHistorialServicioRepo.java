package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.HistorialServicioModel;

import java.util.List;

public interface IHistorialServicioRepo {
    responseModel registrarRegistrar_historial(HistorialServicioModel obj);
    List<HistorialServicioModel> obterhistorial_porcliente(Integer id_cliente);
}
