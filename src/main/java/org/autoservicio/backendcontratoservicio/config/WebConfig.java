package org.autoservicio.backendcontratoservicio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a todas las rutas que comienzan con /api
                .allowedOrigins(
                        "https://agoisp.pro",
                        "https://pago.agoisp.pro",
                        "http://localhost:4200",
                        "http://31.97.133.166"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permite los métodos que necesitas
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(true); // Permite el envío de cookies
    }

}
