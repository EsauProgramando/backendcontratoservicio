package org.autoservicio.backendcontratoservicio.service.ordentrabajo;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.EjecucionordenModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.HistorialejecucionModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.repository.ordentrabajo.EjecucionordenRepository;
import org.autoservicio.backendcontratoservicio.repository.ordentrabajo.OrdentrabajoRepository;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;
import org.autoservicio.backendcontratoservicio.service.GoogleDriveService;
import org.autoservicio.backendcontratoservicio.service.SParamae;
import org.autoservicio.backendcontratoservicio.util.base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.List;

@Service
public class EjecucionordenService {
    @Autowired
    private EjecucionordenRepository repo;

    @Autowired
    private SParamae service_paramae;

    @Autowired
    private GoogleDriveService googleDriveService;
//    public Mono<responseModel> registrarejecucionorden(Integer op, EjecucionordenModel obj) {
//        return Mono.fromCallable(() -> this.repo.registrarejecucionorden(op,obj));
//    }
public Mono<responseModel> registrarejecucionorden(Integer op, EjecucionordenModel obj) {
    return service_paramae.buscar_x_ID("DRV", "EJECOT")
            .flatMap(result -> Mono.fromCallable(() -> {

                // Si hay historial y alguno trae archivo
                if (obj.getHistorial() != null && !obj.getHistorial().isEmpty()) {
                    for (HistorialejecucionModel hist : obj.getHistorial()) {
                        if (hist.getArchivobase64() != null && !hist.getArchivobase64().isBlank()) {

                            String base64Clean = hist.getArchivobase64().contains(",")
                                    ? hist.getArchivobase64().split(",")[1]
                                    : hist.getArchivobase64();

                            // Nombre del archivo
                            String fileName = "EJECOT-" + System.currentTimeMillis();

                            // Convertir base64 a archivo temporal
                            File tempFile = base64Util.convertBase64ToFile(
                                    base64Clean,
                                    fileName,
                                    hist.getExtensiondoc()
                            );

                            // Subir a Drive (carpeta obtenida de la tabla paramétrica)
                            String driveLink = googleDriveService.uploadFile(tempFile, result.getValorstring());

                            // Extraer solo el ID del archivo
                            String fileId = driveLink.split("/d/")[1].split("/")[0];

                            // Guardamos solo el ID en lugar del base64
                            hist.setPath_imagen(fileId);
                        }
                    }
                }

                // Guardar en BD
                return this.repo.registrarejecucionorden(op, obj);
            }));
}

    public Mono<EjecucionordenModel> obtenerejecucionorden(String idejecucionorden) {
        return Mono.fromCallable(() -> this.repo.obtenerejecucionorden(idejecucionorden))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(result ->
                        Mono.zip(
                                Mono.fromCallable(() -> this.repo.obtenerejecucionmateriales(idejecucionorden))
                                        .subscribeOn(Schedulers.boundedElastic()),
                                Mono.fromCallable(() -> this.repo.obtenerejecucion_historial(idejecucionorden))
                                        .subscribeOn(Schedulers.boundedElastic())
                        ).map(tuple -> {
                            result.setMateriales(tuple.getT1());
                            result.setHistorial(tuple.getT2());
                            return result;
                        })
                );
    }


    public Mono<List<EjecucionordenModel>> listaejecucionordens(ListaOrdenRequest request) {
        return Mono.fromCallable(() -> this.repo.listaejecucions(request));
    }
    public Mono<List<HistorialejecucionModel>> obtenerejecucionorden_historial(String idejecucionorden) {
        return Mono.fromCallable(() -> this.repo.obtenerejecucion_historial(idejecucionorden));
    }
}
