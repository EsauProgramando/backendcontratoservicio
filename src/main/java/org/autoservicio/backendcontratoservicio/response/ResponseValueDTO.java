package org.autoservicio.backendcontratoservicio.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseValueDTO<T> {
    private int idResultado;
    private String resultado;
    private T value;
    private List<T> values;
}
