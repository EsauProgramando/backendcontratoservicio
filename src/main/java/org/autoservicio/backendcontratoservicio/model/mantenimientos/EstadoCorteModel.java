package org.autoservicio.backendcontratoservicio.model.mantenimientos;

import org.autoservicio.backendcontratoservicio.repository.mantenimientos.TipoCorteRepository;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class EstadoCorteModel {
    private Integer id_estado_corte;
    private String descripcion,creador,fechareg;
}
