package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ITipo_materialRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Tipo_materialModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Tipo_materialRepository extends IConfigGeneric implements ITipo_materialRepo {
    @Override
    public responseModel registrartipomaterial(Integer op, Tipo_materialModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String bitacura = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_tipomaterial(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, bitacura);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public Tipo_materialModel obtenertipomaterial(String idtecnico) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM tipo_material WHERE idtipomaterial = ?";
            return this.jTemplate().queryForObject(query,
                    new BeanPropertyRowMapper<Tipo_materialModel>(Tipo_materialModel.class),
                    idtecnico
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<Tipo_materialModel> listatipomaterials() {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM tipo_material";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Tipo_materialModel>(Tipo_materialModel.class)
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
