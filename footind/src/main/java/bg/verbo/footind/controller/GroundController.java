package bg.verbo.footind.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.GroundDTO;
import bg.verbo.footind.dto.TeamDTO;
import bg.verbo.footind.service.GroundService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.GROUNDS_URL)
public class GroundController {
	private GroundService groundService;
	
	@GetMapping
	@TrackLatency
	public List<GroundDTO> getGrounds() {
		return groundService.findAll();
	}
	
	@GetMapping("/{groundId}")
	public ResponseEntity<GroundDTO> getGround(@PathVariable Long groundId) {
		Optional<GroundDTO> ground = groundService.findById(groundId);
	    return ground.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<GroundDTO> create(@RequestBody @Valid GroundDTO ground) {
		GroundDTO newGround = groundService.save(ground);
		return ResponseEntity.status(201).body(newGround);
	}
	
	@PutMapping("/{groundId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<GroundDTO> update(@PathVariable Long groundId, @RequestBody @Valid GroundDTO ground) {
		GroundDTO updatedGround = groundService.update(ground, groundId);
		return ResponseEntity.status(200).body(updatedGround);
	}
	
	@DeleteMapping("/{groundId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TeamDTO> delete(@PathVariable Long groundId) {
		groundService.deleteById(groundId);
	    return ResponseEntity.noContent().build();
	}
}
