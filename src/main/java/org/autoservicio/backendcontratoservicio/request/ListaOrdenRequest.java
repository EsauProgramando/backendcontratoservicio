package org.autoservicio.backendcontratoservicio.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaOrdenRequest {
    private String idtecnico,estado,fechainicial,fechafinal;
}
