package bg.verbo.footind.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import bg.verbo.footind.service.MailingService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MailListener {
	private MailingService mailingService;

	@Async
	@EventListener(MailEvent.class)
	public void onEvent(MailEvent mailEvent) {
		mailingService.sendMail(mailEvent.getTo(), mailEvent.getSubject(), mailEvent.getText());
	}
}
