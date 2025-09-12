package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class CortesServicioRequest {
    private Integer id_corte;
    private Integer id_tipo;
    private Integer id_cliente;
    private String motivo_corte;
    private String url_fotocorte;
    private String id_fotocorte;
    private String fecha_corte;
    private Integer tipo_corte;
    private String responsable_corte;
    private Integer estado_corte;
    private String fecha_reconexion;
    private String observaciones;
    private String fechareg;
    private String creador;
    private String nombre_completo;
    private String nomestado_corte;
    private String tipo_servicio;
    private String nrodocident;
    private Integer id_reapertura;
    private String estadocliente;
}
