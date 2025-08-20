package org.autoservicio.backendcontratoservicio.model.gestionclientes;

import java.util.List;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class ContratosModel {
    private Integer id_contrato,id_cliente;
    private String fecha_contrato,observaciones,
            url_soporte_contrato,url_documento,
            url_croquis;
    private Integer estareg;
    private String 	fechareg,creador,fechabaja,observacion_baja;
    private List<Servicios_contratadosModel> detalle;
}
