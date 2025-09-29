package org.autoservicio.backendcontratoservicio.service.gestionclientes;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.Buscarpagosenlinea;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ActuatulizarFacturaModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CobranzaEnvio;
import org.autoservicio.backendcontratoservicio.repository.gestionclientes.FacturacionRepository;
import org.autoservicio.backendcontratoservicio.response.FacturacionRequest;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FacturacionService {
    @Autowired
    private FacturacionRepository repo;

    public Mono<List<FacturacionRequest>> obtener_facturas_x_contrato(Integer id_contrato, Integer id_cliente) {
        return Mono.fromCallable(() -> this.repo.obtener_facturas_x_contrato(id_contrato,id_cliente));
    }

    public Mono<List<FacturacionRequest>> obtener_facturas_x_contrato_filtro(CobranzaEnvio enviodatos) {
        return Mono.fromCallable(() -> this.repo.obtener_facturas_x_contrato_filtro(enviodatos));
    }
    public Mono<List<FacturacionRequest>> obtener_buscar_facturas_pagos_enlinea(Buscarpagosenlinea enviodatos) {
        return Mono.fromCallable(() -> this.repo.obtener_buscar_facturas_pagos_enlinea(enviodatos));
    }

    public Mono<responseModel> actualizar_factura(ActuatulizarFacturaModel enviodatos) {
        return Mono.fromCallable(() -> this.repo.actualizar_factura(enviodatos));
    }


}
