package org.autoservicio.backendcontratoservicio.interfaces.gestionclientes;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.ContratosModel;
import org.autoservicio.backendcontratoservicio.response.ContratoResponse;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;

import java.util.List;

public interface IContratosRepo {
    List<ServicioContratadoRequest> listadosContratos();
    ContratoResponse buscarpor_contratos(Integer id_cliente,Integer nrocontrato);
    List<ServicioContratadoRequest> buscar_servicio_pornrocontrato(Integer nrocontrato);
    responseModel registrarcontrato(Integer op, ContratosModel obj);
}
