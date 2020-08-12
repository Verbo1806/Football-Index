package bg.verbo.footind.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import bg.verbo.footind.service.StatsService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StatsInterceptor implements HandlerInterceptor {
	private final StatsService statsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		statsService.createNewRequestLog(request.getRequestURI(), request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Unauthorized");
		return true;
	}
}
