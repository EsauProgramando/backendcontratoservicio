package org.autoservicio.backendcontratoservicio.response;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ContratoResponse {
    private Integer id_contrato;
    private Integer id_cliente;
    private String cliente;
    private String fecha_contrato,nrodocident;
    private String observaciones,nombre_completo,
            referencia,telefono,fechareg,descripcioncalle,descripcioncorta,destipocalle,
                  email,fechabaja;
    private Integer estado_contrato;
    private Boolean estareg;
   private String  url_soporte_contrato,url_documento,
            url_croquis;
}
