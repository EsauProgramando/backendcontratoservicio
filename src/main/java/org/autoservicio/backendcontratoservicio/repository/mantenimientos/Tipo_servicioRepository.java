package org.autoservicio.backendcontratoservicio.repository.mantenimientos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ITipo_servicioRepo;

import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_servicioModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Tipo_servicioRepository extends IConfigGeneric  implements ITipo_servicioRepo {
    @Override
    public List<Tipo_servicioModel> listadotipo_servicio() {
        try {
            String query = "select * from tipo_servicio WHERE estareg=1";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Tipo_servicioModel>(Tipo_servicioModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public responseModel registrarTipo_Servicio(Integer op, Tipo_servicioModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String tipo_servicio = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_tipo_servicio(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, tipo_servicio);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }
}
