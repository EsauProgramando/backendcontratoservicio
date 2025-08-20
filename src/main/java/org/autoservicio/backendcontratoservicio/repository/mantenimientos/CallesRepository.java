package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ICalles;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CallesModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipocalleModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CallesRepository  extends IConfigGeneric implements ICalles {
    @Override
    public List<CallesModel> listadocalles() {
        try {
            String query = "SELECT \n" +
                    "     c.codcalle,\n" +
                    "    c.tipocalle,\n" +
                    "    tc.descripcioncorta AS destipocalle,\n" +
                    "    c.descripcioncalle,\n" +
                    "    c.estareg\n" +
                    "FROM calles AS c\n" +
                    "JOIN tipocalle AS tc \n" +
                    "    ON tc.tipocalle = c.tipocalle WHERE c.estareg=1 ";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<CallesModel>(CallesModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public responseModel registrarcalles(Integer op, CallesModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String calles = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_calles(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, calles);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public List<TipocalleModel> listadotipocalles() {
        try {
            String query = "SELECT * FROM tipocalle WHERE estareg=1";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<TipocalleModel>(TipocalleModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public responseModel registrartipocalles(Integer op, TipocalleModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String calles = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_tipocalles(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, calles);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }
}
