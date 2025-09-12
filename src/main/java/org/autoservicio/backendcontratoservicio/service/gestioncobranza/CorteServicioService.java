package org.autoservicio.backendcontratoservicio.service.gestioncobranza;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CorteServicioModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesenvioListadofiltro;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.CortesfiltroEnvio;
import org.autoservicio.backendcontratoservicio.repository.gestioncobranza.CorteServicioRepository;
import org.autoservicio.backendcontratoservicio.response.ClienteCortesPentientesRequest;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CorteServicioService {
    @Autowired
    private CorteServicioRepository repo;

    public Mono<responseModel> registrarcorteservicio(Integer op, CorteServicioModel obj) {
        return Mono.fromCallable(() -> this.repo.registrarcorteservicio(op,obj));
    }
    public Mono<List<ClienteCortesPentientesRequest>> obtener_cortes_pendientes_filtro(CortesfiltroEnvio enviodatos) {
        return Mono.fromCallable(() -> this.repo.obtener_cortes_pendientes_filtro(enviodatos));
    }
    public Mono<List<CortesServicioRequest>> obtener_cortes_listados(CortesenvioListadofiltro enviodatos) {
        return Mono.fromCallable(() -> this.repo.obtener_cortes_listados(enviodatos));
    }

    public Mono<CortesServicioRequest> obtener_cortes_id_corte(Integer id_corte) {
        return Mono.fromCallable(() -> this.repo.obtener_cortes_id_corte(id_corte));
    }
}
