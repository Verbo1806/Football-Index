package bg.verbo.footind.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bg.verbo.footind.dto.PlayerDTO;
import bg.verbo.footind.service.PlayerService;
import bg.verbo.footind.util.PlayerUtil;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduledTasks {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
	
	private PlayerService playerService;
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void generatePlayer() {
		PlayerDTO newPlayer = PlayerUtil.generatePlayer();
		playerService.save(newPlayer);
		LOGGER.info("A new player with name {} has been added!", newPlayer.getFullName());
	}
}