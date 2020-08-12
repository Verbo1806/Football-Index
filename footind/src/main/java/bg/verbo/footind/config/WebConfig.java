package bg.verbo.footind.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import bg.verbo.footind.interceptor.StatsInterceptor;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private StatsInterceptor statsInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(statsInterceptor);
	}
}
