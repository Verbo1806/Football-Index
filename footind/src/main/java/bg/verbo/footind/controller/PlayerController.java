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
import bg.verbo.footind.dto.PlayerDTO;
import bg.verbo.footind.dto.TeamDTO;
import bg.verbo.footind.service.PlayerService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.PLAYERS_URL)
public class PlayerController {
	private PlayerService playerService;
	
	@GetMapping
	@TrackLatency
	public List<PlayerDTO> getPlayers() {
		return playerService.findAll();
	}
	
	@GetMapping("/{playerId}")
	public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long playerId) {
		Optional<PlayerDTO> player = playerService.findById(playerId);
	    return player.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlayerDTO> create(@RequestBody @Valid PlayerDTO player) {
		PlayerDTO newPlayer = playerService.save(player);
		return ResponseEntity.status(201).body(newPlayer);
	}
	
	@PutMapping("/{playerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PlayerDTO> update(@PathVariable Long playerId, @RequestBody @Valid PlayerDTO player) {
		PlayerDTO updatedPlayer = playerService.update(player, playerId);
		return ResponseEntity.status(200).body(updatedPlayer);
	}
	
	@DeleteMapping("/{playerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TeamDTO> delete(@PathVariable Long playerId) {
		playerService.deleteById(playerId);
	    return ResponseEntity.noContent().build();
	}
}
