package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CallesModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipocalleModel;

import java.util.List;

public interface ICalles {
    List<CallesModel> listadocalles();
    responseModel registrarcalles(Integer op, CallesModel obj);
    List<TipocalleModel> listadotipocalles();
    responseModel registrartipocalles(Integer op, TipocalleModel obj);
}
