package org.autoservicio.backendcontratoservicio.model;

import java.time.LocalDate;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class paramaeModel {
    private String codemp;
    private String codpar;
    private String tippar;
    private String despar;
    private String codaux;
    private String desaux;
    private String tipvalor;
    private Integer valornum;
    private Double valordec;
    private Double valorporc;
    private LocalDate valorfecha;
    private LocalDate valorfechahora;
    private String valorstring;
    private Integer valorlogico;
    private String nrocuenta;
    private Integer estareg;
}
