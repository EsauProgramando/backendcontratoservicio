package org.autoservicio.backendcontratoservicio.interfaces;

import org.autoservicio.backendcontratoservicio.model.DocumentoSecuencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDocumentoSercuencia extends JpaRepository<DocumentoSecuencia, Long> {
    Optional<DocumentoSecuencia> findTopByTipoComprobanteOrderBySerieActualDesc(String tipoComprobante);
}
