package org.autoservicio.backendcontratoservicio.interfaces;

import org.autoservicio.backendcontratoservicio.model.ConfiguracionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IConfiguracionRepo extends JpaRepository<ConfiguracionModel, Long> {
    List<ConfiguracionModel> findByGrupo(String grupo);
    Optional<ConfiguracionModel> findByGrupoAndClave(String grupo, String clave);
}
