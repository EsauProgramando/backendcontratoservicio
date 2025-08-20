package org.autoservicio.backendcontratoservicio.config;

import org.autoservicio.backendcontratoservicio.database.DataBaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
public class IConfigGeneric {

    @Autowired
    private DataBaseConfig db;

    public JdbcTemplate jTemplate() throws SAXException, IOException, ParserConfigurationException {
        return new JdbcTemplate(db.masterDataSource());
    }


    public HttpEntity<String> recuperaHeaders(String tokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", tokens);

        return new HttpEntity<String>(headers);
    }
}
