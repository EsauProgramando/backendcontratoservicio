package org.autoservicio.backendcontratoservicio.model.mantenimientos;

import io.swagger.v3.oas.models.security.SecurityScheme;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class BitacoraServicio {
    private Integer id_bitacora,id_cliente;
    private String fechareg,bitacoraTipos;
    private String detalle,responsable,evidencia;
}
