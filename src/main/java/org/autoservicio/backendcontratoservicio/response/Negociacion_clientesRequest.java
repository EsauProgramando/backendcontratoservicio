package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Negociacion_clientesRequest {
    private Integer id_negociacion,id_cliente,id_contrato,id_tipo;
    private String  canal_preferido,acciones_negociacion;
    private Integer auto_recordatorio,frecuencia_dias;
    private String fecha_inicio,fecha_fin,estado;
    private Double montopagar_inicial,monto_total;
    private String fecha_vencimiento_nuevo;
    private Integer periodo_gracia,tipodocident;
    private String observaciones,usuario_crea,fechareg,email,telefono,nrodocident,nombre_completo,desctipo;
}
