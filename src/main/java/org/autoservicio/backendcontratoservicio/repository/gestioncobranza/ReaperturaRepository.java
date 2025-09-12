package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.IReaperturaServicio;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ReaperturaServicioModel;
import org.springframework.stereotype.Repository;

@Repository
public class ReaperturaRepository extends IConfigGeneric implements IReaperturaServicio {
    @Override
    public responseModel registrarreapertura(Integer op, ReaperturaServicioModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String reapertura = mapper.writeValueAsString(obj);
             //imprimir en consola
            System.out.println("reapertura "+reapertura);
            String sql = "CALL usp_registrar_reapertura(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, reapertura);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public responseModel actualizarObservacionReapertura(ReaperturaServicioModel obj) {
        //tabla reapertura_servicio id_reapertura, id_tipo ,id_cliente campo para actualizar observaciones
        try {
           //quiero actulizar nativamente
            String sql = "UPDATE reapertura_servicio SET observaciones = ? " +
                    "WHERE id_reapertura = ? AND id_tipo = ? AND id_cliente = ?";
            int rowsAffected = this.jTemplate().update(sql, obj.getObservaciones(), obj.getId_reapertura(), obj.getId_tipo(), obj.getId_cliente());
            String mensaje = (rowsAffected > 0) ? "Observacion actualizada correctamente" : "No se encontro el registro para actualizar";
            return responseModel.builder()
                    .response(mensaje)
                    .build();


        } catch (Exception ex) {
            throw new RepositorioException("Error al actualizar observacion de reapertura: " + ex.getMessage());
        }
    }
}
