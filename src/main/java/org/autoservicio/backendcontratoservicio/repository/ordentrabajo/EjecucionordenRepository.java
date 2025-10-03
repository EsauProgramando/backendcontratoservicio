package org.autoservicio.backendcontratoservicio.repository.ordentrabajo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.ordetrabajo.IEjecucionordenRepo;
import org.autoservicio.backendcontratoservicio.interfaces.ordetrabajo.IOrdentrabajoRepo;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.EjecucionordenModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.HistorialejecucionModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.MaterialesejecModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EjecucionordenRepository extends IConfigGeneric implements IEjecucionordenRepo {
    @Override
    public responseModel registrarejecucionorden(Integer op, EjecucionordenModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String bitacura = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_ejecucion_ordentrabajo(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, bitacura);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public EjecucionordenModel obtenerejecucionorden(String idejecucionorden) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM ejecucionot WHERE idejecucion = ?";
            return this.jTemplate().queryForObject(query,
                    new BeanPropertyRowMapper<EjecucionordenModel>(EjecucionordenModel.class),
                    idejecucionorden
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
    @Override
    public List<MaterialesejecModel> obtenerejecucionmateriales(String idejecucionorden) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "CALL sp_obtener_materiales_x_ejecucionot(?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<MaterialesejecModel>(MaterialesejecModel.class),
                    idejecucionorden
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
    @Override
    public List<HistorialejecucionModel> obtenerejecucionhistorial(String idejecucionorden) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM historial_ejecucion WHERE idejecucion = ?";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<HistorialejecucionModel>(HistorialejecucionModel.class),
                    idejecucionorden
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
    @Override
    public List<EjecucionordenModel> listaejecucions(ListaOrdenRequest request) {

        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String ordentrabajo = mapper.writeValueAsString(request);

            String sql = "CALL sp_buscar_ejecucion_orden_trabajo(?)";

            return this.jTemplate().query(
                    sql,
                    new BeanPropertyRowMapper<>(EjecucionordenModel.class),
                    ordentrabajo
            );
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }
    @Override
    public List<HistorialejecucionModel> obtenerejecucion_historial(String idordentrabajo) {
        try {
            //SELECT * FROM bitacora_servicio WHERE id_cliente = ?
            String query = "SELECT * FROM historial_ejecucion WHERE idejecucion = ?";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<HistorialejecucionModel>(HistorialejecucionModel.class),
                    idordentrabajo
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

}
