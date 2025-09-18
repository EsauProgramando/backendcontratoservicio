package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.IBajaMorosidad;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BajaMorosidadModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BusquedaMorosidad;
import org.autoservicio.backendcontratoservicio.response.ListadomorosidadRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BajaMorosidadRepository extends IConfigGeneric implements IBajaMorosidad {
    @Override
    public responseModel registrarMorosidad(Integer op, BajaMorosidadModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String baja = mapper.writeValueAsString(obj);

            // Llamada al procedimiento almacenado
            String sql = "CALL usp_registrar_bajas_morosidad(?, ?)";

            // Usamos update para ejecutar la consulta, ya que no esperamos un valor de retorno directo
            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, baja);
            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            // Captura y manejo de excepciones
            throw new RepositorioException("Error al registrar baja: " + ex.getMessage());
        }
    }

    @Override
    public responseModel actualizarEstado(BajaMorosidadModel obj) {
        try {
            // Update en baja_morosidad
            String sql1 = "UPDATE baja_morosidad SET estado = ? WHERE id_baja = ? AND id_cliente = ?";
            int rows1 = this.jTemplate().update(sql1, obj.getEstado(), obj.getId_baja(), obj.getId_cliente());

            // Update en clientes
            String sql2 = "UPDATE clientes SET estado_usuario = ? WHERE id = ?";
            int rows2 = this.jTemplate().update(sql2, obj.getEstado(), obj.getId_cliente());

            String mensaje = (rows1 > 0 || rows2 > 0)
                    ? "Actualización realizada correctamente"
                    : "No se encontró registro para actualizar";

            return responseModel.builder()
                    .response(mensaje)
                    .build();

        } catch (Exception ex) {
            throw new RepositorioException("Error al actualizar: " + ex.getMessage());
        }

    }

    @Override
    public List<ListadomorosidadRequest> busqueda_morosidad(BusquedaMorosidad enviodatos) {
        //validar si viene vacio ponerle null
        if( enviodatos.getNombre_completo().isEmpty() ) {
            enviodatos.setNombre_completo(null);
        }

        if( enviodatos.getEstado().isEmpty() ) {
            enviodatos.setEstado(null);
        }
        if( enviodatos.getMotivos().isEmpty() ) {
            enviodatos.setMotivos(null);
        }
        try {
            String query = "CALL buscar_bajas_morosidad(?,?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<ListadomorosidadRequest>(ListadomorosidadRequest.class),
                    enviodatos.getNombre_completo(),enviodatos.getMotivos(),enviodatos.getEstado()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
