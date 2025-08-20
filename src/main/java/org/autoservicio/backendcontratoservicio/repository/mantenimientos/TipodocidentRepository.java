package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.Itipodocident;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.TipodocidentModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipodocidentRepository extends IConfigGeneric implements Itipodocident {

    @Override
    public List<TipodocidentModel> listadotipodocident() {
        try {
            String query = "select * from tipodocident order by tipodocident desc";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<TipodocidentModel>(TipodocidentModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
