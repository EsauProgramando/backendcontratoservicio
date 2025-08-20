package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ClientesRequest {
    private Integer id;
    private String nombres,apellidos;
    private Integer tipodocident;
    private String nrodocident,email,telefono;
    private Integer codcalle;
    private String nrocalle,referencia,latitud,longitud;
    private Integer estareg;
    private String fechareg,creador,fechabaja,observacion_baja;
    private String destipocalle,descripcioncalle,
            descripcioncorta,destipodocident,
            tipodocidenabr, direccion;
}
