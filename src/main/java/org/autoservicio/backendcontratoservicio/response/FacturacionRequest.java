package org.autoservicio.backendcontratoservicio.response;

import java.math.BigDecimal;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class FacturacionRequest {
    private Integer id_factura,id_tipo;
    private String codigo_factura;
    private Integer id_contrato,id_cliente;
    private String nombre_completo, periodo,fecha_emision,fecha_vencimiento;
    private BigDecimal monto,saldo;
    private String estado;
    private Integer dias_mora;
    private String observaciones;
    private Boolean estareg;
    private String fechareg,desctipo,email;
    private Integer tipodocident;
    private String nrodocident,direccion,url_pdf;
}
