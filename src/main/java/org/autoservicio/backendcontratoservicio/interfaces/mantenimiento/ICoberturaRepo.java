package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.CoberturaModel;
import java.util.List;

public interface ICoberturaRepo {

    List<CoberturaModel> listaCoberturas();
}
