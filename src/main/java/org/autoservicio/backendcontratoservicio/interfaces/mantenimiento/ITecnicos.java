package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;

import java.util.List;

public interface ITecnicos {
    responseModel registrartecnico(Integer op, TecnicoModel obj);
    TecnicoModel obtenertecnico(String idtecnico);
    List<TecnicoModel> listatecnicos();
}
