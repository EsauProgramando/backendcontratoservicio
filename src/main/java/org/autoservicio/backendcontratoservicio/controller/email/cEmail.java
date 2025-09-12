package org.autoservicio.backendcontratoservicio.controller.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.model.email.EmailRequest;
import org.autoservicio.backendcontratoservicio.service.email.EmailSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class cEmail {
    @Autowired
    private EmailSimpleService emailService;

    @PostMapping("/mensajesimple")
    public ResponseEntity<responseModel> enviarCorreo(@RequestBody EmailRequest emailRequest) {
        try {
            // Llamamos al servicio para enviar el correo
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());

            // Creamos la respuesta exitosa con el mensaje "Correo enviado con éxito!"
            responseModel response = responseModel.builder()
                    .response("EXITO")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (MessagingException e) {
            // Si ocurre un error, devolvemos una respuesta con código 500 y el mensaje de error
            responseModel response = responseModel.builder()
                    .response("Error al enviar correo: " + e.getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
