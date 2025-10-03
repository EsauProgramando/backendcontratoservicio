package org.autoservicio.backendcontratoservicio.response;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ClienteContratoxServicio {
    private Integer id_contrato,id_tipo;
    private String descripcion;
}
