package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Sp_kpis_mora_total {
    private Integer clientes_totales,clientes_morosos;
    private Double pct_mora_cartera,deuda_total_mora_soles;
}
