package org.autoservicio.backendcontratoservicio.interfaces.gestionclientes;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.Buscarpagosenlinea;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ActuatulizarFacturaModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CobranzaEnvio;
import org.autoservicio.backendcontratoservicio.response.FacturacionRequest;
import java.util.List;

public interface IFacturacion {
    List<FacturacionRequest> obtener_facturas_x_contrato(Integer id_contrato,Integer id_cliente);
    List<FacturacionRequest> obtener_facturas_x_contrato_filtro(CobranzaEnvio enviodatos);
    List<FacturacionRequest> obtener_buscar_facturas_pagos_enlinea(Buscarpagosenlinea enviodatos);
    responseModel actualizar_factura(ActuatulizarFacturaModel enviodatos);
}
