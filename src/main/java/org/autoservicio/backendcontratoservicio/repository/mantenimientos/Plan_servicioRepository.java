package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.IPlan_servicioRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.Plan_servicioModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class Plan_servicioRepository extends IConfigGeneric implements IPlan_servicioRepo{
    @Override
    public List<Plan_servicioModel> listadoPlan_servicio() {
        try {
            String query = "select * from plan_servicio WHERE estareg=1";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Plan_servicioModel>(Plan_servicioModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public responseModel registrarPlan_servicio(Integer op, Plan_servicioModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String plan_servicio = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_plan_servicio(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, plan_servicio);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }
}
