package org.autoservicio.backendcontratoservicio.interfaces.ordetrabajo;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.EjecucionordenModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.HistorialejecucionModel;
import org.autoservicio.backendcontratoservicio.model.ordentrabajo.OrdentrabajoModel;
import org.autoservicio.backendcontratoservicio.request.ListaOrdenRequest;

import java.util.List;

public interface IEjecucionordenRepo {
    responseModel registrarejecucionorden(Integer op, EjecucionordenModel obj);
    EjecucionordenModel obtenerejecucionorden(String idejecucionorden);
    List<EjecucionordenModel> listaejecucions(ListaOrdenRequest request);
    List<HistorialejecucionModel> obtenerejecucion_historial(String idejecucionorden);
}
