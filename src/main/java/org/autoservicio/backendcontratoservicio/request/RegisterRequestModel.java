package org.autoservicio.backendcontratoservicio.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestModel {
    private Integer iduser;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String ciudad;
    private String dni;
    private String rol;
}
