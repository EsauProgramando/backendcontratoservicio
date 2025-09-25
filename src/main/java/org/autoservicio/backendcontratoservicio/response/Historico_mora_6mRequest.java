package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Historico_mora_6mRequest {
    private String periodo;
    private Double deuda_mora_soles,pct_morosos;
    private Integer clientes_con_factura,clientes_morosos;

}
