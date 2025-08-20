package org.autoservicio.backendcontratoservicio.excepciones;

import org.springframework.dao.DataAccessException;

public class RepositorioException extends DataAccessException {
    public RepositorioException(String msg) {
        super(msg);
    }
}
