package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Velocidad_servicioModel;
import java.util.List;
public interface IVelocidadRepo {
    List<Velocidad_servicioModel> listadovelocidad();
    responseModel registrarVelicidad(Integer op, Velocidad_servicioModel obj);
}
