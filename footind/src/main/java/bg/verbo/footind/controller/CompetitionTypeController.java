package bg.verbo.footind.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.CompetitionTypeDTO;
import bg.verbo.footind.service.CompetitionTypeService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.COMPETITION_TYPES_URL)
public class CompetitionTypeController {
	private CompetitionTypeService competitionTypeService;
	
	@GetMapping
	@TrackLatency
	public List<CompetitionTypeDTO> getCompetitions() {
		return competitionTypeService.findAll();
	}
	
}
