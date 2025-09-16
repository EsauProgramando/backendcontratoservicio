package org.autoservicio.backendcontratoservicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documento_secuencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoSecuencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_comprobante", length = 2, nullable = false)
    private String tipoComprobante;

    @Column(name = "serie_actual", length = 4, nullable = false)
    private String serieActual;

    @Column(name = "correlativo_actual", nullable = false)
    private Integer correlativoActual;
}
