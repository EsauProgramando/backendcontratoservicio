package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BajaMorosidadModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BusquedaMorosidad;
import org.autoservicio.backendcontratoservicio.response.ListadomorosidadRequest;

import java.util.List;

public interface IBajaMorosidad {
    responseModel registrarMorosidad(Integer op, BajaMorosidadModel obj);
    responseModel actualizarEstado(BajaMorosidadModel obj);
    List<ListadomorosidadRequest> busqueda_morosidad(BusquedaMorosidad enviodatos);
}
