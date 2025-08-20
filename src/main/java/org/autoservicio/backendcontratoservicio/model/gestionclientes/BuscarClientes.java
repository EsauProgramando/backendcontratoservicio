package org.autoservicio.backendcontratoservicio.model.gestionclientes;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class BuscarClientes {
    private Integer id;
    private String nombres,apellidos,nombre_completo;
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
