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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.annotation.TrackLatency;
import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.TeamDTO;
import bg.verbo.footind.service.TeamService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.TEAMS_URL)
public class TeamController {
	private TeamService teamService;
	
	@GetMapping
	@TrackLatency
	public List<TeamDTO> getTeams() {
		return teamService.findAll();
	}
	
	@GetMapping("/{teamId}")
	public ResponseEntity<TeamDTO> getTeam(@PathVariable Long teamId) {
		Optional<TeamDTO> team = teamService.findById(teamId);
	    return team.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/search")
	public List<TeamDTO> getByExample(@RequestParam Optional<String> name, @RequestParam Optional<String> leagueName) {
		return teamService.findByExample(name, leagueName);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TeamDTO> create(@RequestBody @Valid TeamDTO team) {
		TeamDTO newTeam = teamService.save(team);
		return ResponseEntity.status(201).body(newTeam);
	}
	
	@PutMapping("/{teamId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TeamDTO> update(@PathVariable Long teamId, @RequestBody @Valid TeamDTO team) {
		TeamDTO updatedTeam = teamService.update(team, teamId);
		return ResponseEntity.status(200).body(updatedTeam);
	}
	
	@DeleteMapping("/{teamId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TeamDTO> delete(@PathVariable Long teamId) {
		teamService.deleteById(teamId);
	    return ResponseEntity.noContent().build();
	}
}
