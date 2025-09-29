package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ActuatulizarFacturaModel {
    private String ticket;
    private String estado;
    private String fecha_pago;
    private String motivo_rechaso;
    private String observacion_vache;
    private Integer id_factura;
    private Integer id_cliente;
    private Integer id_contrato;
}
