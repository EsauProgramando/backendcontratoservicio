package org.autoservicio.backendcontratoservicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "documento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class documentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    @Column(name = "codigo_factura", nullable = false, length = 20)
    private String codigoFactura;

    @Column(name = "tipo_comprobante", length = 2)
    private String tipoComprobante; // '01', '03', etc.

    @Column(name = "tipo_documento_cliente", length = 2)
    private String tipoDocumentoCliente; // '1', '6', etc.

    @Column(name = "numero_documento_cliente", nullable = false, length = 15)
    private String numeroDocumentoCliente;

    @Column(name = "nombre_cliente", length = 200)
    private String nombreCliente; // solo boleta

    @Column(name = "razon_social_cliente", length = 200)
    private String razonSocialCliente; // solo factura

    @Column(name = "direccion_cliente", length = 300)
    private String direccionCliente;

    @Column(name = "serie", length = 10)
    private String serie;

    @Column(name = "correlativo", length = 10)
    private String correlativo;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "codigo_hash", length = 200)
    private String codigoHash;

    @Column(name = "cadena_qr", length = 200)
    private String cadenaQr;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "xml_base64", columnDefinition = "MEDIUMTEXT")
    private String xmlBase64;

    @Column(name = "xml_cdr_base64", columnDefinition = "MEDIUMTEXT")
    private String xmlCdrBase64;

    @Column(name = "url_pdf", length = 255)
    private String urlPdf;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
