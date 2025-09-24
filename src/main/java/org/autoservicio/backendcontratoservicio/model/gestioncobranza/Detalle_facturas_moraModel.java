package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Detalle_facturas_moraModel {
    private Integer id_cliente,id_contrato,solo_con_saldo;
    private String estado;
}
