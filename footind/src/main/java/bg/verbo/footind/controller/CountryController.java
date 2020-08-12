package bg.verbo.footind.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.CountryDTO;
import bg.verbo.footind.service.CountryService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.COUNTRIES_URL)
public class CountryController {
	private CountryService countryService;
	
	@GetMapping
	@TrackLatency
	public List<CountryDTO> getCountries() {
		return countryService.findAll();
	}
	
}
