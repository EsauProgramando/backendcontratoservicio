package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ClienteCortesPentientesRequest {
   Integer id_cliente;
   String nombre_completo,nrodocident;
   Integer id_tipo;
   String desctipo;
   Integer id_factura,id_contrato;
   String periodo_mas_antiguo;
   Double saldo,monto_factura, saldo_factura;
   String fecha_venc_mas_antigua;
   Integer dias_mora;
   String estado_factura;
    Integer id_corte, n_periodos_vencidos;
    String estadocliente,email,telefono;
    Double deuda_total,monto_total;
}
