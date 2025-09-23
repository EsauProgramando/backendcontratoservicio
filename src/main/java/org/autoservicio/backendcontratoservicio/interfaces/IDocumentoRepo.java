package org.autoservicio.backendcontratoservicio.interfaces;

import org.autoservicio.backendcontratoservicio.model.documentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDocumentoRepo extends JpaRepository<documentoModel, String> {
    Optional<documentoModel> findByCodigoFactura(String codigo_factura);
    List<documentoModel> findByEstado(String estado);
}

