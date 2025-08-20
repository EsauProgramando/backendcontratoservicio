package org.autoservicio.backendcontratoservicio.model.mantenimientos;

    @lombok.AllArgsConstructor
    @lombok.NoArgsConstructor
    @lombok.Getter
    @lombok.Setter
    @lombok.Builder
    public  class Tipo_servicioModel {
        private Integer id_tipo;
        private String descripcion;
        private Integer estareg;
    }

