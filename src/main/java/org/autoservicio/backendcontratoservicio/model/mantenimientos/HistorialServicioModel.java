package org.autoservicio.backendcontratoservicio.model.mantenimientos;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class HistorialServicioModel {
    Integer id_historial,id_cliente;
    String tipo_accion,fecha_accion,descripcion,estado_servicio;
}
