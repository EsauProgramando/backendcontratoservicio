package org.autoservicio.backendcontratoservicio.model.mantenimientos;


@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class TipoCorteModel {
    private Integer id_tipo_corte;
    private String descripcion,creador,fechareg;
}
