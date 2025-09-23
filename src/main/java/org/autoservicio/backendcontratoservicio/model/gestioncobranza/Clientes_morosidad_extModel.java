package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Clientes_morosidad_extModel {
    private Integer solo_con_saldo;
    private String nombre_completo,estado;
}
