package org.autoservicio.backendcontratoservicio.service.mantenimientos;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.BitacoraServicio;
import org.autoservicio.backendcontratoservicio.repository.mantenimientos.BitacuraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BitacuraService {
    @Autowired
    private BitacuraRepository repo;
    public Mono<responseModel> registrarbitacura(Integer op, BitacoraServicio obj) {
        return Mono.fromCallable(() -> this.repo.registrarbitacura(op,obj));
    }
    public Mono<List<BitacoraServicio>> obtnerbitacoraid_cliente(Integer id_cliente) {
        return Mono.fromCallable(() -> this.repo.obtnerbitacoraid_cliente(id_cliente));
    }
}
