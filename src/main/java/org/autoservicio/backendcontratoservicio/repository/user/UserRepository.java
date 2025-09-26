package org.autoservicio.backendcontratoservicio.repository.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.user.IUserRepo;
import org.autoservicio.backendcontratoservicio.model.user.UserModel;
import org.autoservicio.backendcontratoservicio.request.RegisterRequestModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class UserRepository extends IConfigGeneric implements IUserRepo {
    @Override
    public Mono<UserModel> obtenerusuario_username(String username) {
        return Mono.fromCallable(() -> {
            try {
                String query = "SELECT * FROM user WHERE username = ?";
                return this.jTemplate().queryForObject(query,
                        new BeanPropertyRowMapper<UserModel>(UserModel.class),
                        username
                );
            } catch (Exception ex) {
                throw new RepositorioException("error al obtener usuario: " + ex.getMessage());
            }
        });
    }

    @Override
    public Mono<UserModel> save(UserModel user) {
        return Mono.fromCallable(() -> {
            try {
                // Aquí implementas la lógica para guardar el usuario
                String query = "INSERT INTO user (username, password, nombres, apellidos, ciudad, dni, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
                this.jTemplate().update(query,
                        user.getUsername(),
                        user.getPassword(),
                        user.getNombres(),
                        user.getApellidos(),
                        user.getCiudad(),
                        user.getDni(),
                        user.getRol());
                return user;
            } catch (Exception ex) {
                throw new RepositorioException("error al guardar usuario: " + ex.getMessage());
            }
        });
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return Mono.fromCallable(() -> {
            try {
                String query = "SELECT COUNT(*) FROM user WHERE username = ?";
                Integer count = this.jTemplate().queryForObject(query, Integer.class, username);
                return count != null && count > 0;
            } catch (Exception ex) {
                throw new RepositorioException("error al verificar usuario: " + ex.getMessage());
            }
        });
    }


}

