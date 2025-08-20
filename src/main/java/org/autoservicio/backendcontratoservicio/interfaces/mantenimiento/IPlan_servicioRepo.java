package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Plan_servicioModel;

import java.util.List;

public interface IPlan_servicioRepo {
    List<Plan_servicioModel> listadoPlan_servicio();
    responseModel registrarPlan_servicio(Integer op, Plan_servicioModel obj);
}
