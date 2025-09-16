package org.autoservicio.backendcontratoservicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "configuracion", uniqueConstraints = @UniqueConstraint(columnNames = {"grupo", "clave"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grupo;
    private String clave;

    @Lob
    private String valor;

    private String tipoDato;
    private String descripcion;

    private java.time.LocalDateTime fechaCreacion;
    private java.time.LocalDateTime fechaActualizacion;
}
