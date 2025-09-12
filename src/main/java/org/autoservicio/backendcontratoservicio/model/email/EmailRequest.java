package org.autoservicio.backendcontratoservicio.model.email;
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class EmailRequest {
    private String to;
    private String subject;
    private String body;

}
