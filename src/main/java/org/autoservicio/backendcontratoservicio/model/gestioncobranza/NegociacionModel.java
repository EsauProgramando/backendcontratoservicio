package org.autoservicio.backendcontratoservicio.model.gestioncobranza;

import java.util.List;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class NegociacionModel {
    private Long id_cliente;
    private Long id_contrato;
    private Long id_tipo;
    private String canal_preferido;
    private String acciones_negociacion;
    private Double montopagar_inicial;
    private String fecha_vencimiento_nuevo; // formato yyyy-MM-dd
    private Integer periodo_gracia;
    private String usuario_crea;
    private String observaciones;
    private Integer auto_recordatorio;
    private Integer frecuencia_dias;
    private Double monto_total;
    private List<FacturasNociacionItemModel> facturas;

}
