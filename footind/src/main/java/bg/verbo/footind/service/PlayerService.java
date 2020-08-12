package bg.verbo.footind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.verbo.footind.db.entity.Player;
import bg.verbo.footind.db.repository.PlayerRepository;
import bg.verbo.footind.dto.PlayerDTO;
import bg.verbo.footind.service.mapper.PlayerMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlayerService {
	private PlayerRepository playerRepository;
	
	//@Cacheable(CacheConfig.ALL_PLAYERS)
	public List<PlayerDTO> findAll() {
		return playerRepository
				.findAll()
				.stream()
				.map(PlayerMapper.INSTANCE::mapPlayerEntityToDto)
				.collect(Collectors.toList());
	}

	public Optional<PlayerDTO> findById(Long playerId) {
		return playerRepository
				.findById(playerId)
				.map(PlayerMapper.INSTANCE::mapPlayerEntityToDto);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_PLAYERS)
	public PlayerDTO save(PlayerDTO player) {
		Player newPlayer = playerRepository
							.save(PlayerMapper.INSTANCE.mapPlayerDtoToEntity(player));
		
		return PlayerMapper.INSTANCE.mapPlayerEntityToDto(newPlayer);
	}
	
	@Transactional
	//@CachePut(CacheConfig.ALL_PLAYERS)
	public PlayerDTO update(PlayerDTO player, Long playerId) {
		Player updatedPlayer = playerRepository
								.findById(playerId)
								.orElseThrow(() -> new EntityNotFoundException("Player does not exist!"));

		player.setId(playerId);
		updatedPlayer = playerRepository.save(PlayerMapper.INSTANCE.mapPlayerDtoToEntity(player));
		return PlayerMapper.INSTANCE.mapPlayerEntityToDto(updatedPlayer);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_PLAYERS)
	public void deleteById(Long playerId) {
		playerRepository.deleteById(playerId);
	}

}
