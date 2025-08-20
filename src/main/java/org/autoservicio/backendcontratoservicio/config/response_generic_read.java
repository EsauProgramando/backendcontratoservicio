package org.autoservicio.backendcontratoservicio.config;

import java.util.List;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
public class response_generic_read<T> {
    Integer totalrecords;
    List<T> listar;
}
