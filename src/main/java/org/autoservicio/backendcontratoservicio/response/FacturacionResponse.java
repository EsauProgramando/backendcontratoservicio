package org.autoservicio.backendcontratoservicio.response;

import lombok.Data;

@Data
public class FacturacionResponse {
    private FacturacionData data;
    private boolean success;
    private String message;

    @Data
    public static class FacturacionData {
        private String typeResultadoDeclaracion;
        private int responseCode;
        private String message;
        private String codigoHash;
        private String cadenaQr;
        private String base64Xml;
        private String base64XmlCdr;
        private String ticket;
        private String observacion;
    }
}
