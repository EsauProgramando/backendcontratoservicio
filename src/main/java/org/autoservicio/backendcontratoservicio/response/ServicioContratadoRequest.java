package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ServicioContratadoRequest {
    private Integer id_contrato, id_cliente;
    private String cliente,fecha_contrato,observaciones,
             tipo_servicio,plan,velocidad;
    private Double precio_mensual;
    private String fecha_activacion;
    private Boolean estado_contrato, estado_servicio,ip_fija;
    private Integer id_servicio_contratado,id_plan,
            id_velocidad,id_tipo;
    private double cargo_instalacion;
    private String equipo_mac,notas;
    private Integer estareg;


}
