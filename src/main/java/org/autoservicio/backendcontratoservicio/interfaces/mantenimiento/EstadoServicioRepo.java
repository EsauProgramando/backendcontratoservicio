package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadosServicioModel;
import java.util.List;

public interface EstadoServicioRepo {
    List<EstadosServicioModel> listadoEstadosServicio();
}
