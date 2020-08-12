package bg.verbo.footind.web.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import bg.verbo.footind.web.error.RestTemplateResponseErrorHandler;

@Configuration
public class RestConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(30000))
				.setReadTimeout(Duration.ofMillis(30000))
				.errorHandler(new RestTemplateResponseErrorHandler())
				.build();
	}
}
