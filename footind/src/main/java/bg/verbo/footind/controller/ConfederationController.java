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
import bg.verbo.footind.dto.ConfederationDTO;
import bg.verbo.footind.service.ConfederationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.CONFEDERATIONS_URL)
public class ConfederationController {
	private ConfederationService confederationService;
	
	@GetMapping
	@TrackLatency
	public List<ConfederationDTO> getConfederations() {
		return confederationService.findAll();
	}
	
	@GetMapping("/{confederationId}")
	public ResponseEntity<ConfederationDTO> getConfederation(@PathVariable Long confederationId) {
		Optional<ConfederationDTO> confederation = confederationService.findById(confederationId);
	    return confederation.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ConfederationDTO> create(@RequestBody @Valid ConfederationDTO confederation) {
		ConfederationDTO newConfederation = confederationService.save(confederation);
		return ResponseEntity.status(201).body(newConfederation);
	}
	
	@PutMapping("/{confederationId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ConfederationDTO> update(@PathVariable Long confederationId, @RequestBody @Valid ConfederationDTO confederation) {
		ConfederationDTO updatedConfederation = confederationService.update(confederation, confederationId);
		return ResponseEntity.status(200).body(updatedConfederation);
	}
	
	@DeleteMapping("/{confederationId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ConfederationDTO> delete(@PathVariable Long confederationId) {
		confederationService.deleteById(confederationId);
	    return ResponseEntity.noContent().build();
	}
}
