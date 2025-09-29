package org.autoservicio.backendcontratoservicio.model.ordentrabajo;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class MaterialesejecModel {
    private Integer idtipo;
    private String tipo,marca,serie;
    private Integer cantidad;
    private String nota;
}
