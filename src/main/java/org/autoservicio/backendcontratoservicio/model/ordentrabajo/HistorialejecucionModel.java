package org.autoservicio.backendcontratoservicio.model.ordentrabajo;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class HistorialejecucionModel {
    private String archivobase64,extensiondoc,descripcion,fecha,icon,color,path_imagen;
}
