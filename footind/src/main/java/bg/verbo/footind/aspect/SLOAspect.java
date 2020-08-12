package bg.verbo.footind.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import bg.verbo.footind.config.SLOConfig;
import bg.verbo.footind.event.MailEventPublisher;
import lombok.AllArgsConstructor;

@Aspect
@Component
@AllArgsConstructor
public class SLOAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(SLOAspect.class);
  
  private final MailEventPublisher mailEventPublisher;
  
	@Around("@annotation(bg.verbo.footind.annotation.TrackLatency)")
	public Object tackLatency(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
    
		Object ret = pjp.proceed();
    
		stopWatch.stop();

		long executionTime = stopWatch.getLastTaskTimeMillis();
		if (executionTime > SLOConfig.DEFAULT_LATENCY) {
			LOGGER.error("Method {} executed for {}ms which is more than the limit of {}ms.",
         	pjp.getSignature(), executionTime, SLOConfig.DEFAULT_LATENCY);
      
			mailEventPublisher.publish("sashoverbovskiy@gmail.com",
					"Method execution is too slow!",
					String.format("Method %s executed for %sms which is more than the limit of %sms.", pjp.getSignature(), executionTime, SLOConfig.DEFAULT_LATENCY));
		}

		return ret;
	}

}
