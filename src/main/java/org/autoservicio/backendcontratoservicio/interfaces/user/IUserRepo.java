package org.autoservicio.backendcontratoservicio.interfaces.user;

import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.user.UserModel;
import org.autoservicio.backendcontratoservicio.request.RegisterRequestModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserRepo {


    Mono<UserModel> obtenerusuario_username(String username);
    Mono<UserModel> save(UserModel user);
    Mono<Boolean> existsByUsername(String username);
}
