package bg.verbo.footind.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.StadiumCategoryDTO;
import bg.verbo.footind.service.StadiumCategoryService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.STADIUM_CATEGORIES_URL)
public class StadiumCategoryController {
	private StadiumCategoryService stadiumCategoryService;
	
	@GetMapping
	@TrackLatency
	public List<StadiumCategoryDTO> getCategories() {
		return stadiumCategoryService.findAll();
	}
	
	@GetMapping("/search")
	public List<StadiumCategoryDTO> getByExample(@RequestParam Optional<String> code, @RequestParam Optional<String> name) {
		return stadiumCategoryService.findByExample(code, name);
	}
	
}
