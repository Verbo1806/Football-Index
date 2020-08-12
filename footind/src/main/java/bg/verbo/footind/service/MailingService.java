package bg.verbo.footind.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailingService.class);

	private JavaMailSender emailSender;

	public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom("sashoverbovskiy@gmail.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        
        emailSender.send(message);
        
        LOGGER.info("An email has been sent to {}", to);
    }
}
