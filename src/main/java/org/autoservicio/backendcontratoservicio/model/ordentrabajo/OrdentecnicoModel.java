package org.autoservicio.backendcontratoservicio.model.ordentrabajo;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class OrdentecnicoModel {
    private String id_tecnico,tecnico,estado;
    private Integer cantidad;
}
