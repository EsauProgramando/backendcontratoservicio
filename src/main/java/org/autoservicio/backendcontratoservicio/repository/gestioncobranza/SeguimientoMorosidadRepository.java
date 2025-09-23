package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.ISeguimientoMorosidad;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Clientes_morosidad_extModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Detalle_facturas_moraModel;
import org.autoservicio.backendcontratoservicio.response.Clientes_morosidad_ext;
import org.autoservicio.backendcontratoservicio.response.Detalle_facturas_mora;
import org.autoservicio.backendcontratoservicio.response.Sp_kpis_mora_total;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeguimientoMorosidadRepository  extends IConfigGeneric implements ISeguimientoMorosidad {

    @Override
    public List<Clientes_morosidad_ext> Clientes_morosidad_extModel(Clientes_morosidad_extModel enviodatos) {
        //validar si viene vacio ponerle null
        if( enviodatos.getNombre_completo().isEmpty() ) {
            enviodatos.setNombre_completo(null);
        }


        try {
            String query = "CALL sp_clientes_morosidad_ext(?,?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Clientes_morosidad_ext>(Clientes_morosidad_ext.class),
                    enviodatos.getSolo_con_saldo(), enviodatos.getNombre_completo(), enviodatos.getEstado()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<Sp_kpis_mora_total> kpis_mora_total(Integer solo_con_saldo) {
        //validar si viene vacio ponerle null

        try {
            String query = "CALL sp_kpis_mora_total(?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Sp_kpis_mora_total>(Sp_kpis_mora_total.class),
                    solo_con_saldo
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<Detalle_facturas_mora> detalle_facturas_mora(Detalle_facturas_moraModel enviodatos) {


        try {
            String query = "CALL sp_detalle_facturas_mora(?,?,?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Detalle_facturas_mora>(Detalle_facturas_mora.class),
                    enviodatos.getId_cliente(), enviodatos.getId_contrato(), enviodatos.getSolo_con_saldo(), enviodatos.getEstado()
            );
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
