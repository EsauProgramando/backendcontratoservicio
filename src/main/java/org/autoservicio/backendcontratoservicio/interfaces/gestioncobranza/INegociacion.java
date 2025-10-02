package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BuscarNegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.NegociacionModel;
import org.autoservicio.backendcontratoservicio.response.Negociacion_clientesRequest;

import java.util.List;

public interface INegociacion {
    responseModel generarnegociacion(NegociacionModel obj);
    List<Negociacion_clientesRequest> busqueda_negociacion(BuscarNegociacion enviodatos);
}
