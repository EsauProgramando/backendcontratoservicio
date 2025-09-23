package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Detalle_facturas_mora {
    private Integer id_factura,id_contrato;
    private String codigo_factura,tipo_servicio,periodo;
    private String fecha_emisión,fecha_vencimiento;
    private Double monto,saldo;
    private Integer dias_mora;
    private String estado;
}
