package org.autoservicio.backendcontratoservicio.model.ordentrabajo;

import java.util.List;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.Builder
public class EjecucionordenModel {
    private String estado,idtecnico,idordentrabajo,observaciones;
    private Double latitud,longitud,potenciaopt,rssi,snr,descmbps,subbps,ping;
    private Boolean pinggateway,navegacionweb,speedtest,iptv,voip;
    private String img_evidencia;
    private List<MaterialesejecModel> materiales;
    private List<HistorialejecucionModel> historial;


}
