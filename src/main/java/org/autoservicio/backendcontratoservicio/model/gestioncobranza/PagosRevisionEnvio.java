package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class PagosRevisionEnvio {
    private Long id_pago ;
    private Long id_factura ;
    private Long id_cliente;
    private Long id_metodo ;
    private String fecha_pago;
    private Double monto_pagado;
    private String numero_operacion;
    private String adjunto_boleta;
    private String id_adjuntaboleta;
    private String observaciones;
    private String creador;
    private String ticket;
    private Double monto_confirmado;
    private String fecharevision;
    private String observacion_vache;
    private String motivo_rechaso;
}
