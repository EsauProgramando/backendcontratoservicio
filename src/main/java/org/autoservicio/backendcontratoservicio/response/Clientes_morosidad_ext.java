package org.autoservicio.backendcontratoservicio.response;


@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Clientes_morosidad_ext {
    private Integer id_cliente;
    private String nombres,apellidos;
    private Integer tipodocident;
    private String nrodocident,desctipo;
    private Integer id_contrato;
    private Double deuda_actual_soles;
    private Integer dias_en_mora;
    private String  ultimo_pago,estado;
    private Integer facturas_en_mora;

}