package bg.verbo.footind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.verbo.footind.db.entity.Team;
import bg.verbo.footind.db.repository.TeamRepository;
import bg.verbo.footind.dto.TeamDTO;
import bg.verbo.footind.service.mapper.TeamMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeamService {
	private TeamRepository teamRepository;
	
	//@Cacheable(CacheConfig.ALL_TEAMS)
	public List<TeamDTO> findAll() {
		return teamRepository
				.findAll()
				.stream()
				.map(TeamMapper.INSTANCE::mapTeamEntityToDto)
				.collect(Collectors.toList());
	}

	public Optional<TeamDTO> findById(Long teamId) {
		return teamRepository
				.findById(teamId)
				.map(TeamMapper.INSTANCE::mapTeamEntityToDto);
	}
	
	public List<TeamDTO> findByExample(Optional<String> name, Optional<String> leagueName) {
		return teamRepository
				.findByNameAndLeagueName(name.orElse(null), leagueName.orElse(null))
				.stream()
				.map(TeamMapper.INSTANCE::mapTeamEntityToDto)
				.collect(Collectors.toList());
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_TEAMS)
	public TeamDTO save(TeamDTO team) {
		Team newTeam = teamRepository
							.save(TeamMapper.INSTANCE.mapTeamDtoToEntity(team));
		
		return TeamMapper.INSTANCE.mapTeamEntityToDto(newTeam);
	}
	
	@Transactional
	//@CachePut(CacheConfig.ALL_TEAMS)
	public TeamDTO update(TeamDTO team, Long teamId) {
		Team updatedTeam = teamRepository
								.findById(teamId)
								.orElseThrow(() -> new EntityNotFoundException("Team does not exist!"));

		team.setId(teamId);
		updatedTeam = teamRepository.save(TeamMapper.INSTANCE.mapTeamDtoToEntity(team));
		return TeamMapper.INSTANCE.mapTeamEntityToDto(updatedTeam);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_TEAMS)
	public void deleteById(Long teamId) {
		teamRepository.deleteById(teamId);
	}

}
