package org.autoservicio.backendcontratoservicio.repository.mantenimientos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.IVelocidadRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Velocidad_servicioModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VelocidadRepository  extends IConfigGeneric implements IVelocidadRepo {
    @Override
    public List<Velocidad_servicioModel> listadovelocidad() {
        try {
            String query = "select * from velocidad_servicio WHERE estareg=1";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Velocidad_servicioModel>(Velocidad_servicioModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public responseModel registrarVelicidad(Integer op, Velocidad_servicioModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String velocidad = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_velocidad_servicio(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, velocidad);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }
}
