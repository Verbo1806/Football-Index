package bg.verbo.footind.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class MailEvent extends ApplicationEvent {
	private static final long serialVersionUID = 2928255845413356941L;
	
	private final String to;
	private final String subject;
	private final String text;

	public MailEvent(Object source, String to, String subject, String text) {
		super(source);
	    this.to = to;
	    this.subject = subject;
	    this.text = text;
	}
}
