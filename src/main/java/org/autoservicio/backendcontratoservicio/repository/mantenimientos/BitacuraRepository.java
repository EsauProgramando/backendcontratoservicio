package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.BitacuraServicioRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.BitacoraServicio;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BitacuraRepository extends IConfigGeneric implements BitacuraServicioRepo {
    @Override
    public responseModel registrarbitacura(Integer op, BitacoraServicio obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String bitacura = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_bitacora(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, bitacura);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public List<BitacoraServicio> obtnerbitacoraid_cliente(Integer id_cliente) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM bitacora_servicio WHERE id_cliente = ?";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<BitacoraServicio>(BitacoraServicio.class),
                    id_cliente
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }


}
