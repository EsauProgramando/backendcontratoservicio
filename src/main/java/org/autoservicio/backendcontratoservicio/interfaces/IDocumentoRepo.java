package org.autoservicio.backendcontratoservicio.interfaces;

import org.autoservicio.backendcontratoservicio.model.documentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDocumentoRepo extends JpaRepository<documentoModel, Integer> {
    Optional<documentoModel> findByIdContrato(Integer idContrato);
    List<documentoModel> findByEstado(String estado);
}

