package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ReaperturaServicioModel {
    private Integer id_reapertura;           // id_reapertura
    private Integer id_tipo;                 // id_tipo
    private Integer id_cliente;              // id_cliente
    private String fecha_reconexion;  // fecha_reconexion
    private String motivo_reconexion;     // motivo_reconexion
    private String observaciones;        // observaciones
    private Integer estado_reconexion;    // estado_reconexion (0 = pendiente, 1 = reconectado)
    private String responsable_reconexion; // responsable_reconexion
    private String fechareg;      // fechareg
    private String creador;
    private Integer id_corte;// creador
    private Integer id_estado;

}
