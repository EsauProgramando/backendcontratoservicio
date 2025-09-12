package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.IHistorialServicioRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.BitacoraServicio;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.HistorialServicioModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistorialServicioRepository  extends IConfigGeneric implements IHistorialServicioRepo {
    @Override
    public responseModel registrarRegistrar_historial(HistorialServicioModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String historial = mapper.writeValueAsString(obj);

            String sql = "CALL registrar_historial_servicio(?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class,historial);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public List<HistorialServicioModel> obterhistorial_porcliente(Integer id_cliente) {
        //es select * from historial_servicio where id_cliente=?
        try {
            String query = "SELECT * FROM historial_servicio WHERE id_cliente = ?";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<HistorialServicioModel>(HistorialServicioModel.class),
                    id_cliente
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
