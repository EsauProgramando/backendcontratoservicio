package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ITecnicos;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TecnicoRepository extends IConfigGeneric implements ITecnicos {
    @Override
    public responseModel registrartecnico(Integer op, TecnicoModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String bitacura = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_tecnico(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, bitacura);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public TecnicoModel obtenertecnico(String idtecnico) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM tecnicos WHERE idtecnico = ?";
            return this.jTemplate().queryForObject(query,
                    new BeanPropertyRowMapper<TecnicoModel>(TecnicoModel.class),
                    idtecnico
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<TecnicoModel> listatecnicos() {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM tecnicos";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<TecnicoModel>(TecnicoModel.class)
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
