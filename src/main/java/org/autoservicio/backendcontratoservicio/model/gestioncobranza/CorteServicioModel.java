package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class CorteServicioModel {
    private Integer id_corte;                // id_corte
    private Integer id_tipo;                 // id_tipo
    private Integer id_cliente;              // id_cliente
    private String motivo_corte;          // motivo_corte
    private String url_fotocorte;          // url_fotocorte
    private String id_fotocorte;          // id_fotocorte
    private String fecha_corte;             // fecha_corte
    private Integer tipo_corte;              // tipo_corte
    private String responsable_corte;    // responsable_corte
    private Integer estado_corte;            // estado_corte
    private String fecha_reconexion;        // fecha_reconexion
    private String observaciones;        // observaciones
    private String fechareg;               // fechareg
    private String creador;              // creador
    private Integer id_estado;
    private Integer id_factura;
}
