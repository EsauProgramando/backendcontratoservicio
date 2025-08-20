package org.autoservicio.backendcontratoservicio.model.mantenimientos;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Plan_servicioModel {
    private Integer id_plan;
    private String descripcion;
    private Integer estareg;
}