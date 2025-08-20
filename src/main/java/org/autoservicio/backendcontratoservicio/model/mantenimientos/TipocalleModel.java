package org.autoservicio.backendcontratoservicio.model.mantenimientos;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class TipocalleModel {
    private Integer tipocalle;
    private String 	descripcion;
    private Integer estareg;
    private String creador,	fechareg;
}