package org.autoservicio.backendcontratoservicio.repository.mantenimientos;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.IEstadoCorteRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadoCorteModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstadoCorteRepository extends IConfigGeneric implements IEstadoCorteRepo {
    @Override
    public List<EstadoCorteModel> listadEstadoCorte() {
        try {
            String query = "select * from estados_corte";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<EstadoCorteModel>(EstadoCorteModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
