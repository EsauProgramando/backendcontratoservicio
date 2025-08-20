package org.autoservicio.backendcontratoservicio.service;

import org.autoservicio.backendcontratoservicio.interfaces.IParamaeRepo;
import org.autoservicio.backendcontratoservicio.model.paramaeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SParamae {

    @Autowired
    private IParamaeRepo repo;

    public Mono<paramaeModel> buscar_x_ID(String codpar, String tippar) {
        return Mono.fromCallable(() -> repo.buscar_x_ID(codpar, tippar));
    }

}
