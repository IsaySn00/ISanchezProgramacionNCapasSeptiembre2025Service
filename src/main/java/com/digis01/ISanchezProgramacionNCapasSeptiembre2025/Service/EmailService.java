package com.digis01.ISanchezProgramacionNCapasSeptiembre2025.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String link) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Verifica tu cuenta");

        String html = """
            <html>
                <body style="font-family: Arial, sans-serif; background: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background: white; padding: 25px; border-radius: 10px;">
                        <h2 style="color: #333; text-align:center;">Verificación de Cuenta</h2>
                        <p style="font-size: 16px; color: #555; margin-bottom: 30px">
                            Hola, gracias por registrarte:
                        </p>
                        <p style="text-align: center;">
                        <a href="%s" 
                            style="background: #007bff; color: white; padding: 12px 20px; 
                            border-radius: 5px; text-decoration: none; font-size: 16px;">
                                Verificar Cuenta
                        </a>
                        </p>
                    </div>
               </body>
           </html>
        """.formatted(link, link);
        helper.setText(html, true);
        javaMailSender.send(message);
    }
}
