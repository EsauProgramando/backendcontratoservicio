package org.autoservicio.backendcontratoservicio.interfaces;

import org.autoservicio.backendcontratoservicio.model.paramaeModel;
import org.springframework.dao.DataAccessException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface IParamaeRepo {
    paramaeModel buscar_x_ID(String codpar, String tippar) throws DataAccessException, SAXException, IOException, ParserConfigurationException;
}
