package bg.verbo.footind.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MailEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publish(String to, String subject, String text) {
		MailEvent mailEvent = new MailEvent(this, to, subject, text);
		this.applicationEventPublisher.publishEvent(mailEvent);
	}
}
