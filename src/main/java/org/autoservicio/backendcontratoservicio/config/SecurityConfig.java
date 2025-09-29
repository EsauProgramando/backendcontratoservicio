package org.autoservicio.backendcontratoservicio.config;

import org.autoservicio.backendcontratoservicio.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración de seguridad (WebFlux)
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // ⚠️ Muy importante: permitir preflight (OPTIONS)
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Endpoints públicos
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/swagger-resources/**",
                                "/v2/api-docs/**"

                        ).permitAll()
                        .pathMatchers("/api/auth/**","/api/facturacion/buscar_facturas_enlinea",
                                "/api/facturacion/actualizar_factura",
                                "/api/pagos/**",
                                "/api/paramae/**",
                                "/api/izipay/**").permitAll()

                        // Endpoints protegidos
                        .pathMatchers("/api/**", "/contrato_servicio/api/**").authenticated()
                        // Endpoints protegidos
                        .pathMatchers("/**","/contrato_servicio/**").authenticated()

                        // Cualquier otro endpoint
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    /**
     * Configuración de CORS (WebFlux)
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Producción: restringir a tus dominios
        corsConfig.setAllowedOrigins(Arrays.asList(
                "https://agoisp.pro",
                "https://pago.agoisp.pro",
                "http://localhost:4200",
                "http://31.97.133.166"
        ));

        // Alternativa desarrollo: abrir todo
        // corsConfig.addAllowedOriginPattern("*");

        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
