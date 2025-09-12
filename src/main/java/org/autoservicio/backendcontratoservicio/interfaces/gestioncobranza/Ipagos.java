package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosEnvio;

public interface Ipagos {
    responseModel registrarpagos(Integer op, PagosEnvio obj);
}
