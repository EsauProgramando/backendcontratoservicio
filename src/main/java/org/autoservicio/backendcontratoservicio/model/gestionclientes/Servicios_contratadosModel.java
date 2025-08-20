package org.autoservicio.backendcontratoservicio.model.gestionclientes;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Servicios_contratadosModel {
    private Integer id_servicio_contratado,id_contrato,id_plan,
            id_velocidad,id_tipo;
    private double precio_mensual,cargo_instalacion;
    private String equipo_mac,fecha_activacion,notas;
    private Integer estareg;
    private Boolean ip_fija;
}

