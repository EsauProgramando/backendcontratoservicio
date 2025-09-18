package org.autoservicio.backendcontratoservicio.repository.ordentrabajo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.ordetrabajo.IOrdentrabajoRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TecnicoModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdentrabajoRepository extends IConfigGeneric implements IOrdentrabajoRepo {
    @Override
    public responseModel registrarordentrabajo(Integer op, OrdentrabajoModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String bitacura = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_ordentrabajo(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, bitacura);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public OrdentrabajoModel obtenerordentrabajo(String idordentrabajo) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM tecnicos WHERE idordentrabajo = ?";
            return this.jTemplate().queryForObject(query,
                    new BeanPropertyRowMapper<OrdentrabajoModel>(OrdentrabajoModel.class),
                    idordentrabajo
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
    @Override
    public List<OrdentrabajoModel> obtenerordentrabajo_historial(String idordentrabajo) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM historial_orden WHERE idordentrabajo = ?";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<OrdentrabajoModel>(OrdentrabajoModel.class),
                    idordentrabajo
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<OrdentrabajoModel> listaordentrabajos() {
//        try {
//            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
//            String query = "SELECT * FROM ordentrabajo";
//            return this.jTemplate().query(query,
//                    new BeanPropertyRowMapper<OrdentrabajoModel>(OrdentrabajoModel.class)
//            );
//        } catch (Exception ex) {
//
//            throw new RepositorioException("error en listado: "+ex.getMessage());
//        }

        try {
            String query = "CALL sp_buscar_orden_trabajo()";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<OrdentrabajoModel>(OrdentrabajoModel.class)
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
