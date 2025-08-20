package org.autoservicio.backendcontratoservicio.response;

import java.util.List;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Detalle_contratoxservicioRequest {
    private ContratoResponse cab;
    private List<ServicioContratadoRequest> ite;
}
