package org.autoservicio.backendcontratoservicio.util;

import lombok.RequiredArgsConstructor;
import org.autoservicio.backendcontratoservicio.interfaces.IConfiguracionRepo;
import org.autoservicio.backendcontratoservicio.model.ConfiguracionModel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class ConfiguracionUtil {
    private final IConfiguracionRepo configuracionRepository;

    public Map<String, String> obtenerGrupo(String grupo) {
        return configuracionRepository.findByGrupo(grupo)
                .stream()
                .collect(Collectors.toMap(ConfiguracionModel::getClave, ConfiguracionModel::getValor));
    }

    public String obtenerValor(String grupo, String clave) {
        return configuracionRepository.findByGrupoAndClave(grupo, clave)
                .map(ConfiguracionModel::getValor)
                .orElse(null);
    }
}
