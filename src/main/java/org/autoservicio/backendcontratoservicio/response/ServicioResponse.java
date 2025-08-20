package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ServicioResponse {
    private String tipo_servicio;
    private String plan;
    private String velocidad;
    private Double precio_mensual;
    private String fecha_activacion;
    private Integer estado_servicio,id_tipo,id_plan,id_velocidad;
    private Boolean ip_fija;

}
