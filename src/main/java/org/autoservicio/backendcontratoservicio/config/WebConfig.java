package org.autoservicio.backendcontratoservicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class WebConfig {

//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//
//        // 🔹 Producción: solo tus dominios
//        corsConfig.setAllowedOrigins(Arrays.asList(
//                "https://agoisp.pro",
//                "https://pago.agoisp.pro",
//                "http://localhost:4200",   // pruebas locales
//                "http://31.97.133.166"     // tu IP
//        ));
//
//        // 🔹 Alternativa desarrollo (abrir todo):
//        // corsConfig.addAllowedOriginPattern("*");
//
//        corsConfig.setAllowCredentials(true);
//        corsConfig.addAllowedHeader("*");
//        corsConfig.addAllowedMethod("*");
//        corsConfig.setMaxAge(3600L); // cache preflight 1h
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return new CorsWebFilter(source);
//    }
}
