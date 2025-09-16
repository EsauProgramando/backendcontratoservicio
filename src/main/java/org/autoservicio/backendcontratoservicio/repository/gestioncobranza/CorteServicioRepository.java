package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.ICorteServicio;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CorteServicioModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesenvioListadofiltro;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesfiltroEnvio;
import org.autoservicio.backendcontratoservicio.response.ClienteCortesPentientesRequest;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CorteServicioRepository extends IConfigGeneric implements ICorteServicio {
    @Override
    public responseModel registrarcorteservicio(Integer op, CorteServicioModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String corte = mapper.writeValueAsString(obj);

            // Llamada al procedimiento almacenado
            String sql = "CALL usp_registrar_cortes(?, ?)";

            // Usamos update para ejecutar la consulta, ya que no esperamos un valor de retorno directo
            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, corte);
            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            // Captura y manejo de excepciones
            throw new RepositorioException("Error al registrar corte: " + ex.getMessage());
        }
    }

    @Override
    public List<ClienteCortesPentientesRequest> obtener_cortes_pendientes_filtro(CortesfiltroEnvio enviodatos) {
        //validar si viene vacio ponerle null
        if( enviodatos.getNombre_completo().isEmpty() ) {
            enviodatos.setNombre_completo(null);
        }

        if( enviodatos.getEstado().isEmpty() ) {
            enviodatos.setEstado(null);
        }
        try {
            String query = "CALL sp_clientes_para_corte_detalle_auto(?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<ClienteCortesPentientesRequest>(ClienteCortesPentientesRequest.class),
                    enviodatos.getEstado(), enviodatos.getNombre_completo()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
    @Override
    public List<CortesServicioRequest> obtener_cortes_listados(CortesenvioListadofiltro enviodatos) {
        //validar si viene vacio ponerle null
        if( enviodatos.getNombre_completo().isEmpty() ) {
            enviodatos.setNombre_completo(null);
        }


        try {
            String query = "CALL sp_buscar_cortes_servicio(?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<CortesServicioRequest>(CortesServicioRequest.class),
                    enviodatos.getNombre_completo(),enviodatos.getEstado_corte()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public CortesServicioRequest obtener_cortes_id_corte(Integer id_corte) {
      //es select * from cortes_servicio where id_corte=?
        try {
            String query = "SELECT * FROM cortes_servicio WHERE id_corte=?";
            return this.jTemplate().queryForObject(query,
                    new BeanPropertyRowMapper<CortesServicioRequest>(CortesServicioRequest.class),
                    id_corte
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
