package org.autoservicio.backendcontratoservicio.model.gestioncobranza;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class BajaMorosidadModel {
    private Long id_baja;
    private Long id_cliente;
    private String fecharebaja;
    private Long id_tipo;
    private String estado;       // 'ACTIVO','INHABILITADO','INDEFINIDO'
    private String observacion;
    private String motivo;
    private String creador;
}
