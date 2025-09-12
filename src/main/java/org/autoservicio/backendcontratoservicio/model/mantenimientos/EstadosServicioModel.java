package org.autoservicio.backendcontratoservicio.model.mantenimientos;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class EstadosServicioModel {
    private Integer id_estado;
    private String descripcion,creador,fechareg;
}
