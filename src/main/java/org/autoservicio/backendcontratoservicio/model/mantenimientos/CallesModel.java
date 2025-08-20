package org.autoservicio.backendcontratoservicio.model.mantenimientos;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class CallesModel {
    private Integer codcalle,tipocalle;
    private String descripcioncalle,destipocalle;
    private String estareg;
    private String creador;
}