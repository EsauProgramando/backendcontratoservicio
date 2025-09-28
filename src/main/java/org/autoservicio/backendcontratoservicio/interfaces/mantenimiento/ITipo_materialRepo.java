package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_materialModel;

import java.util.List;

public interface ITipo_materialRepo {
    responseModel registrartipomaterial(Integer op, Tipo_materialModel obj);
    Tipo_materialModel obtenertipomaterial(String idtipomaterial);
    List<Tipo_materialModel> listatipomaterials();
}
