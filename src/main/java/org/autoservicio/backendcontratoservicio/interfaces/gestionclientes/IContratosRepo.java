package org.autoservicio.backendcontratoservicio.interfaces.gestionclientes;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.ContratosModel;
import org.autoservicio.backendcontratoservicio.response.ContratoResponse;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;

import java.util.List;

public interface IContratosRepo {
    List<ServicioContratadoRequest> listadosContratos();
    ContratoResponse buscarpor_contratos(Integer id_cliente,Integer id_contrato);
    List<ServicioContratadoRequest> buscar_servicio_pornrocontrato(Integer id_contrato );
    responseModel registrarcontrato(Integer op, ContratosModel obj);
    responseModel generar_facturas_contrato(Integer id_contrato, Integer id_cliente);
    List<ServicioContratadoRequest> buscar_servicio_x_codigo_factura(String codigo_factura );
}
