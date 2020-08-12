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
import bg.verbo.footind.dto.LeagueDTO;
import bg.verbo.footind.dto.TeamDTO;
import bg.verbo.footind.service.LeagueService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.LEAGUES_URL)
public class LeagueController {
	private LeagueService leagueService;
	
	@GetMapping
	@TrackLatency
	public List<LeagueDTO> getLeagues() {
		return leagueService.findAll();
	}
	
	@GetMapping("/{leagueId}")
	public ResponseEntity<LeagueDTO> getLeague(@PathVariable Long leagueId) {
		Optional<LeagueDTO> league = leagueService.findById(leagueId);
	    return league.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LeagueDTO> create(@RequestBody @Valid LeagueDTO league) {
		LeagueDTO newLeague = leagueService.save(league);
		return ResponseEntity.status(201).body(newLeague);
	}
	
	@PutMapping("/{leagueId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LeagueDTO> update(@PathVariable Long leagueId, @RequestBody @Valid LeagueDTO league) {
		LeagueDTO updatedLeague = leagueService.update(league, leagueId);
		return ResponseEntity.status(200).body(updatedLeague);
	}
	
	@DeleteMapping("/{leagueId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TeamDTO> delete(@PathVariable Long leagueId) {
		leagueService.deleteById(leagueId);
	    return ResponseEntity.noContent().build();
	}
}
