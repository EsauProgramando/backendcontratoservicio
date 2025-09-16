package org.autoservicio.backendcontratoservicio.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoRequestDTO {

    private Integer idContrato;
    private String tipoComprobante; // Ej: '01', '03'
    private String tipoDocumentoCliente; // Ej: '1', '6'
    private String numeroDocumentoCliente;
    private String nombreCliente;
    private String razonSocialCliente;
    private String direccionCliente;
}
