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

    public void sendEmail(String to, String link, String subjectMail) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        helper.setTo(to);

        String html = "";

        if (subjectMail == "add") {
            helper.setSubject("Verifica tu cuenta");

            html = """
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
        } else if (subjectMail == "password") {
            helper.setSubject("Recuperación de Contraseña");

            html = """
            <html>
                <body style="font-family: Arial, sans-serif; background: #f4f4f4; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background: white; padding: 25px; border-radius: 10px;">
                        <h2 style="color: #333; text-align:center;">Recuperación de contraseña</h2>
                        <p style="font-size: 16px; color: #555; margin-bottom: 30px">
                            Hola, da click en el boton para poder recuperar tu contraseña:
                        </p>
                        <p style="text-align: center;">
                        <a href="%s" 
                            style="background: #007bff; color: white; padding: 12px 20px; 
                            border-radius: 5px; text-decoration: none; font-size: 16px;">
                                Recuperar
                        </a>
                        </p>
                    </div>
               </body>
           </html>
        """.formatted(link, link);
        }

        helper.setText(html, true);
        javaMailSender.send(message);
    }

    public void sendNotification(String to, String subject) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        
        String html = "";

        html = """
            <html lang="es">
            <head>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f7; color: #51545e; margin: 0; padding: 0; width: 100% !important; }
                    .container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; margin-top: 20px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
                    .header { background-color: #2d3748; padding: 25px; text-align: center; color: #ffffff; }
                    .content { padding: 40px; line-height: 1.6; }
                    .footer { background-color: #f8fafc; padding: 20px; text-align: center; font-size: 12px; color: #a8aaaf; }
                    .button { display: inline-block; background-color: #3182ce; color: #ffffff !important; padding: 12px 25px; border-radius: 5px; text-decoration: none; font-weight: bold; margin-top: 20px; }
                    .alert { background-color: #fff5f5; border-left: 4px solid #f56565; padding: 15px; margin-top: 20px; font-size: 14px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1 style="margin: 0; font-size: 20px;">Seguridad de la Cuenta</h1>
                    </div>
            
                    <div class="content">
                        <h2 style="color: #2d3748;">¡Hola!</h2>
                        <p>Te informamos que la contraseña de tu cuenta ha sido <strong>modificada exitosamente</strong> el día de hoy.</p>
                        
                        <p>Si fuiste tú, puedes ignorar este mensaje de forma segura. No es necesario realizar ninguna acción adicional.</p>
            
                      
                    </div>
            
                    <div class="footer">
                        <p>&copy; 2025</p>
                        <p>Este es un correo automático, por favor no respondas a este mensaje.</p>
                    </div>
                </div>
            </body>
            </html>
        """;
        
        helper.setText(html, true);
        javaMailSender.send(message);
    }

    public void sendCodigoCambioPassword(String to, String codigo) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                "UTF-8"
        );

        helper.setTo(to);
        helper.setSubject("Código de verificación para cambio de contraseña");

        String html = """
        <html lang="es">
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    padding: 20px;
                }
                .container {
                    max-width: 600px;
                    margin: auto;
                    background: #ffffff;
                    padding: 30px;
                    border-radius: 10px;
                    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                    text-align: center;
                }
                .code {
                    font-size: 32px;
                    letter-spacing: 6px;
                    font-weight: bold;
                    color: #2d3748;
                    background: #edf2f7;
                    padding: 15px;
                    border-radius: 8px;
                    display: inline-block;
                    margin: 20px 0;
                }
                .warning {
                    color: #e53e3e;
                    font-size: 14px;
                    margin-top: 20px;
                }
                .footer {
                    font-size: 12px;
                    color: #a0aec0;
                    margin-top: 30px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Cambio de contraseña</h2>
                <p>Has solicitado cambiar tu contraseña.</p>
                <p>Ingresa el siguiente código en la aplicación para continuar:</p>

                <div class="code">%s</div>

                <div class="footer">
                    <p>&copy; 2025</p>
                    <p>Este es un correo automático, no respondas a este mensaje.</p>
                </div>
            </div>
        </body>
        </html>
    """.formatted(codigo);

        helper.setText(html, true);
        javaMailSender.send(message);
    }

}
