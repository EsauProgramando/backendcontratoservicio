package org.autoservicio.backendcontratoservicio.response;


@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ListadomorosidadRequest {
    private String nombre_completo,fecharebaja;
    private Integer id_baja,id_cliente;
    private String fechareg,estado,observacion,motivo, desctipo,email,nrodocident,telefono;
}
