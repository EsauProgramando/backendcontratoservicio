package org.autoservicio.backendcontratoservicio.repository.gestionclientes;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestionclientes.IFacturacion;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.Buscarpagosenlinea;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ActuatulizarFacturaModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CobranzaEnvio;
import org.autoservicio.backendcontratoservicio.response.FacturacionRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FacturacionRepository extends IConfigGeneric implements IFacturacion {
    @Override
    public List<FacturacionRequest> obtener_facturas_x_contrato(Integer id_contrato , Integer id_cliente) {
        try {
            String query = "CALL sp_obtener_facturas_x_contrato(?, ?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<FacturacionRequest>(FacturacionRequest.class),id_contrato,id_cliente );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<FacturacionRequest> obtener_facturas_x_contrato_filtro(CobranzaEnvio enviodatos) {
        try {
            String query = "CALL sp_buscar_facturas(?,?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<FacturacionRequest>(FacturacionRequest.class),
                    enviodatos.getNombre_completo(),enviodatos.getEstado(),enviodatos.getId_tipo()
                    );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<FacturacionRequest> obtener_buscar_facturas_pagos_enlinea(Buscarpagosenlinea enviodatos) {
        try {
           //validar si viene vacio ponerle null
         if( enviodatos.getNombre_completo().isEmpty() ) {
             enviodatos.setNombre_completo(null);
         }

            if( enviodatos.getCodigo_factura().isEmpty() ) {
                enviodatos.setCodigo_factura(null);
            }


            String query = "CALL sp_buscar_facturas_pagos_enlinea(?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<FacturacionRequest>(FacturacionRequest.class),
                    enviodatos.getNombre_completo(),enviodatos.getCodigo_factura()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public responseModel actualizar_factura(ActuatulizarFacturaModel enviodatos) {
        try {
            //quiero actulizar nativamente
            String sql = "UPDATE facturas SET ticket = ?,estado=?,fecha_pago=?,motivo_rechaso=?,observacion_vache=? " +
                    "WHERE id_factura  = ? AND id_contrato  = ? AND id_cliente = ?";
            int rowsAffected = this.jTemplate().update(sql, enviodatos.getTicket(), enviodatos.getEstado(), enviodatos.getFecha_pago(),
                    enviodatos.getMotivo_rechaso(),enviodatos.getObservacion_vache(),enviodatos.getId_factura(),enviodatos.getId_contrato(),
                    enviodatos.getId_cliente());
            String mensaje = (rowsAffected > 0) ? "Observacion actualizada correctamente" : "No se encontro el registro para actualizar";
            return responseModel.builder()
                    .response(mensaje)
                    .build();


        } catch (Exception ex) {
            throw new RepositorioException("Error al actualizar observacion de reapertura: " + ex.getMessage());
        }
    }
}
