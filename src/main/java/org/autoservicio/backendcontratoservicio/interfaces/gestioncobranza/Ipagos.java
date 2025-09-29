package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosEnvio;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.PagosRevisionEnvio;

public interface Ipagos {
    responseModel registrarpagos(Integer op, PagosEnvio obj);
    responseModel registrarpagos_revision(Integer op, PagosRevisionEnvio obj);
}
