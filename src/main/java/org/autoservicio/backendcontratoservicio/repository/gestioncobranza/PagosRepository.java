package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.Ipagos;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosEnvio;
import org.springframework.stereotype.Repository;

@Repository
public class PagosRepository extends IConfigGeneric implements Ipagos {
    @Override
    public responseModel registrarpagos(Integer op, PagosEnvio obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String pagos = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_pagos(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, pagos);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }
}
