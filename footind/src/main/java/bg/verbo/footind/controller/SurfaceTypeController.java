package bg.verbo.footind.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.SurfaceTypeDTO;
import bg.verbo.footind.service.SurfaceTypeService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.SURFACE_TYPES_URL)
public class SurfaceTypeController {
	private SurfaceTypeService surfaceTypeService;
	
	@GetMapping
	@TrackLatency
	public List<SurfaceTypeDTO> getSurfaces() {
		return surfaceTypeService.findAll();
	}
	
}
