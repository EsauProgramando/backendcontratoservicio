package org.autoservicio.backendcontratoservicio.service.auth;

import org.autoservicio.backendcontratoservicio.interfaces.user.IUserRepo;
import org.autoservicio.backendcontratoservicio.model.user.UserModel;
import org.autoservicio.backendcontratoservicio.request.RegisterRequestModel;
import org.autoservicio.backendcontratoservicio.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {


    private final IUserRepo userRepository;  // Cambia a IUserRepo
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(IUserRepo userRepository,  // Cambia a IUserRepo
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Mono<String> login(String username, String password) {
        return userRepository.obtenerusuario_username(username)
                .filter(reslogin -> passwordEncoder.matches(password, reslogin.getPassword()))
                .map(user -> jwtUtil.generateToken(user))
                .switchIfEmpty(Mono.error(new RuntimeException("Credenciales inválidas")));
    }

    public Mono<UserModel> register(RegisterRequestModel request) {
        return userRepository.existsByUsername(request.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Usuario ya existe"));
                    }

                    UserModel user = UserModel.builder()
                            .username(request.getUsername())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .nombres(request.getNombres())
                            .apellidos(request.getApellidos())
                            .ciudad(request.getCiudad())
                            .dni(request.getDni())
                            .rol(request.getRol() != null ? request.getRol() : "USER")
                            .build();

                    return userRepository.save(user);
                });
    }
}