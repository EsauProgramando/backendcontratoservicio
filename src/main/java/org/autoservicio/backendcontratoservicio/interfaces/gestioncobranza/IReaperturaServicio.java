package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.ReaperturaServicioModel;

public interface IReaperturaServicio {
    responseModel registrarreapertura(Integer op, ReaperturaServicioModel obj);
    responseModel actualizarObservacionReapertura(ReaperturaServicioModel obj);
}
