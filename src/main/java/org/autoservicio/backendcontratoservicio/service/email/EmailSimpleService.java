package org.autoservicio.backendcontratoservicio.service.email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailSimpleService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);  // true para permitir contenido HTML

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);  // Cuerpo del correo, en formato HTML

        mailSender.send(message);
    }
}
