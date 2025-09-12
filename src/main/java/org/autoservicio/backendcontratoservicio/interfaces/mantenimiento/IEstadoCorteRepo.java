package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadoCorteModel;
import java.util.List;

public interface IEstadoCorteRepo {
    List<EstadoCorteModel> listadEstadoCorte();
}
