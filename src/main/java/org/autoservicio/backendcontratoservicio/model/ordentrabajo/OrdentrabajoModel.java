package org.autoservicio.backendcontratoservicio.model.ordentrabajo;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class OrdentrabajoModel {
    private String idordentrabajo,fechareg,fechaorden,creador,estado,motivo,id_tecnico;
    private Integer id_cliente,id_tipo;
    private String nombre_completo,servicio,direccion,tipo,tecnico,path_imagen,observacion;
    private String estadoejecucion,idejecucion;
}
