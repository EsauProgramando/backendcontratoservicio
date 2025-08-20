package org.autoservicio.backendcontratoservicio.config;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class genericModel<T> {
    boolean success;
    String mensaje;
    T data;
}
