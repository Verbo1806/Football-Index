package bg.verbo.footind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.verbo.footind.db.entity.League;
import bg.verbo.footind.db.repository.LeagueRepository;
import bg.verbo.footind.dto.LeagueDTO;
import bg.verbo.footind.service.mapper.LeagueMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LeagueService {
	private LeagueRepository leagueRepository;
	
	//@Cacheable(CacheConfig.ALL_LEAGUES)
	public List<LeagueDTO> findAll() {
		return leagueRepository
				.findAll()
				.stream()
				.map(LeagueMapper.INSTANCE::mapLeagueEntityToDto)
				.collect(Collectors.toList());
	}

	public Optional<LeagueDTO> findById(Long leagueId) {
		return leagueRepository
				.findById(leagueId)
				.map(LeagueMapper.INSTANCE::mapLeagueEntityToDto);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_LEAGUES)
	public LeagueDTO save(LeagueDTO league) {
		League newLeague = leagueRepository
							.save(LeagueMapper.INSTANCE.mapLeagueDtoToEntity(league));
		
		return LeagueMapper.INSTANCE.mapLeagueEntityToDto(newLeague);
	}
	
	@Transactional
	//@CachePut(CacheConfig.ALL_LEAGUES)
	public LeagueDTO update(LeagueDTO league, Long leagueId) {
		League updatedLeague = leagueRepository
								.findById(leagueId)
								.orElseThrow(() -> new EntityNotFoundException("League does not exist!"));

		league.setId(leagueId);
		updatedLeague = leagueRepository.save(LeagueMapper.INSTANCE.mapLeagueDtoToEntity(league));
		return LeagueMapper.INSTANCE.mapLeagueEntityToDto(updatedLeague);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_LEAGUES)
	public void deleteById(Long leagueId) {
		leagueRepository.deleteById(leagueId);
	}

}
