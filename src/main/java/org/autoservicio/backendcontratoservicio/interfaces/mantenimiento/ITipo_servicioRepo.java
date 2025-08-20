package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_servicioModel;

import java.util.List;

public interface ITipo_servicioRepo {
    List<Tipo_servicioModel> listadotipo_servicio();
    responseModel registrarTipo_Servicio(Integer op, Tipo_servicioModel obj);
}
