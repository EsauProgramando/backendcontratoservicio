package org.autoservicio.backendcontratoservicio.database;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.SAXException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Slf4j
@Configuration
public class DataBaseConfig {
 public DataSource masterDataSource() throws SAXException, IOException, ParserConfigurationException {
        String cadenaConexion = "jdbc:mysql://31.97.133.166:3306/telecomunicaciones";
        log.info("Conectando a: " + cadenaConexion);

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(cadenaConexion);
        ds.setUsername("magistrack");
        ds.setPassword("M@gistr4ck-BC");

        return ds;
    }
/*
 public DataSource masterDataSource() {
        String cadenaConexion = "jdbc:mysql://localhost:3306/telecomunicaciones";
        System.out.println("Conectando a: " + cadenaConexion);

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(cadenaConexion);
        ds.setUsername("root"); // usuario local
        ds.setPassword(""); // contraseña local

        return ds;
    }*/
}

