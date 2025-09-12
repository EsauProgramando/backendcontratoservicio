package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ItipoCorteModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipoCorteModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoCorteRepository extends IConfigGeneric implements ItipoCorteModel {

    @Override
    public List<TipoCorteModel> listadotipocorte() {
        try {
            String query = "select * from tipos_corte order by id_tipo_corte  desc";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<TipoCorteModel>(TipoCorteModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
