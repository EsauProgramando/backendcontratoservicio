package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.ICoberturaRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CoberturaModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoberturaRepository extends IConfigGeneric implements ICoberturaRepo {
    @Override
    public List<CoberturaModel> listaCoberturas() {
        try {
            String query = "select * from cobertura WHERE estareg=1";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<CoberturaModel>(CoberturaModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
