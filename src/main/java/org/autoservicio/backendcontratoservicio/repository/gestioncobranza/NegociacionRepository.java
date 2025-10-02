package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.INegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BuscarNegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.NegociacionModel;
import org.autoservicio.backendcontratoservicio.response.ListadomorosidadRequest;
import org.autoservicio.backendcontratoservicio.response.Negociacion_clientesRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NegociacionRepository extends IConfigGeneric implements INegociacion {
    @Override
    public responseModel generarnegociacion(NegociacionModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String negociacion = mapper.writeValueAsString(obj);

            String sql = "CALL sp_crear_negociacion_json(?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, negociacion);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public List<Negociacion_clientesRequest> busqueda_negociacion(BuscarNegociacion enviodatos) {
        //validar si viene vacio ponerle null
        if( enviodatos.getNombre_completo().isEmpty() ) {
            enviodatos.setNombre_completo(null);
        }

        if( enviodatos.getEstado().isEmpty() ) {
            enviodatos.setEstado(null);
        }
        try {
            String query = "CALL buscar_negociacion_clientes(?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Negociacion_clientesRequest>(Negociacion_clientesRequest.class),
                    enviodatos.getNombre_completo(),enviodatos.getEstado()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
