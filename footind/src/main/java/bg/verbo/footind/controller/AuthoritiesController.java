package bg.verbo.footind.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.auth.AuthorityDTO;
import bg.verbo.footind.service.AuthorityService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.AUTHORITIES_URL)
public class AuthoritiesController {
	private AuthorityService authorityService;
	
	@GetMapping
	@TrackLatency
	public List<AuthorityDTO> getAuthorities() {
		return authorityService.findAll();
	}
	
}
