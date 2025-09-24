package org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza;

import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Clientes_morosidad_extModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Detalle_facturas_moraModel;
import org.autoservicio.backendcontratoservicio.response.Clientes_morosidad_ext;
import org.autoservicio.backendcontratoservicio.response.Detalle_facturas_mora;
import org.autoservicio.backendcontratoservicio.response.Sp_kpis_mora_total;

import java.util.List;

public interface ISeguimientoMorosidad {

    List<Clientes_morosidad_ext> Clientes_morosidad_extModel(Clientes_morosidad_extModel enviodatos);
    Sp_kpis_mora_total kpis_mora_total(Integer solo_con_saldo);

    List<Detalle_facturas_mora> detalle_facturas_mora(Detalle_facturas_moraModel enviodatos);
}
