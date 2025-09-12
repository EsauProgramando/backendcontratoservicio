package org.autoservicio.backendcontratoservicio.repository.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.mantenimiento.EstadoServicioRepo;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.EstadosServicioModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstadoServicioRepository extends IConfigGeneric implements EstadoServicioRepo {
    @Override
    public List<EstadosServicioModel> listadoEstadosServicio() {
        try {
            String query = "select * from estados_servicio order by fechareg desc";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<EstadosServicioModel>(EstadosServicioModel.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }
}
