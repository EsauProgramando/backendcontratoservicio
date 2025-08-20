package org.autoservicio.backendcontratoservicio.repository;

import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.interfaces.IParamaeRepo;
import org.autoservicio.backendcontratoservicio.model.paramaeModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Repository
public class ParamaeRepository extends IConfigGeneric implements IParamaeRepo {
    @Override
    public paramaeModel buscar_x_ID(String codpar, String tippar) throws DataAccessException, SAXException, IOException, ParserConfigurationException {
        return this.jTemplate().queryForObject("select * from paramae where codemp=? and codpar=? and tippar=?", new BeanPropertyRowMapper<paramaeModel>(paramaeModel.class), "001", codpar, tippar);
    }
}
