package org.autoservicio.backendcontratoservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendAutoservicioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendAutoservicioApplication.class, args);
    }

}
