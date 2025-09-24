package org.autoservicio.backendcontratoservicio.service.gestioncobranza;

import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Clientes_morosidad_extModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.Detalle_facturas_moraModel;
import org.autoservicio.backendcontratoservicio.repository.gestioncobranza.SeguimientoMorosidadRepository;
import org.autoservicio.backendcontratoservicio.response.Clientes_morosidad_ext;
import org.autoservicio.backendcontratoservicio.response.Detalle_facturas_mora;
import org.autoservicio.backendcontratoservicio.response.Sp_kpis_mora_total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SeguimientoMorosidadServicio {
    @Autowired
    private SeguimientoMorosidadRepository repo;

    public Mono<List<Clientes_morosidad_ext>> Clientes_morosidad_extModel(Clientes_morosidad_extModel enviodatos) {
        return Mono.fromCallable(() -> this.repo.Clientes_morosidad_extModel(enviodatos));
    }

    public Mono<Sp_kpis_mora_total> kpis_mora_total(Integer solo_con_saldo) {
        return Mono.fromCallable(() -> this.repo.kpis_mora_total(solo_con_saldo));
    }

    public Mono<List<Detalle_facturas_mora>> detalle_facturas_mora(Detalle_facturas_moraModel enviodatos) {
        return Mono.fromCallable(() -> this.repo.detalle_facturas_mora(enviodatos));
    }
}
