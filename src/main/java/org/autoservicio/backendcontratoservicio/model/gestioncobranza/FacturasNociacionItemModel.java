package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class FacturasNociacionItemModel {
    private Long id_factura;
    private Double monto_fraccionado;
    private String fecha_vencimiento_nuevo; // formato yyyy-MM-dd
}
