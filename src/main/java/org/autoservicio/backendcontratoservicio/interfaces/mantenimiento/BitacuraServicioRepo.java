package org.autoservicio.backendcontratoservicio.interfaces.mantenimiento;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.mantenimientos.BitacoraServicio;

import java.util.List;

public interface BitacuraServicioRepo {
    responseModel registrarbitacura(Integer op, BitacoraServicio obj);
    List<BitacoraServicio> obtnerbitacoraid_cliente(Integer id_cliente);

}
