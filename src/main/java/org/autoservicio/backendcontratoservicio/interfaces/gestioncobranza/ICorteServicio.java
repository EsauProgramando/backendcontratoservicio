package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CorteServicioModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesenvioListadofiltro;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesfiltroEnvio;
import org.autoservicio.backendcontratoservicio.response.ClienteCortesPentientesRequest;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;

import java.util.List;

public interface ICorteServicio {
    responseModel registrarcorteservicio(Integer op, CorteServicioModel obj);
    List<ClienteCortesPentientesRequest> obtener_cortes_pendientes_filtro(CortesfiltroEnvio enviodatos);
    List<CortesServicioRequest> obtener_cortes_listados(CortesenvioListadofiltro enviodatos);
    CortesServicioRequest obtener_cortes_id_corte(Integer id_corte);

}
