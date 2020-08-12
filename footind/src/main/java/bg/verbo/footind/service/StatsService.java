package bg.verbo.footind.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import bg.verbo.footind.db.entity.RequestLog;
import bg.verbo.footind.db.repository.RequestLogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatsService {
	private RequestLogRepository requestLogRepository;

	public void createNewRequestLog(String url, String username) {
		requestLogRepository.save(
				RequestLog.builder()
				.requestTimestamp(new Date())
				.url(url)
				.username(username)
				.build());
	}
}
