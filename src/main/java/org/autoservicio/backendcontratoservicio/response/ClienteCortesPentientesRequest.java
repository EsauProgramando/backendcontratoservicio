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
   String periodo;
   Double saldo;
   String fecha_vencimiento;
   Integer dias_mora;
   String estado;
    Integer id_corte;
    String estadocliente,email,telefono;
}
