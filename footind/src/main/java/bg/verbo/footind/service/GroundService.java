package bg.verbo.footind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.verbo.footind.db.entity.Ground;
import bg.verbo.footind.db.repository.GroundRepository;
import bg.verbo.footind.dto.GroundDTO;
import bg.verbo.footind.service.mapper.GroundMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroundService {
	private GroundRepository groundRepository;
	
	//@Cacheable(CacheConfig.ALL_GROUNDS)
	public List<GroundDTO> findAll() {
		return groundRepository
				.findAll()
				.stream()
				.map(GroundMapper.INSTANCE::mapGroundEntityToDto)
				.collect(Collectors.toList());
	}

	public Optional<GroundDTO> findById(Long groundId) {
		return groundRepository
				.findById(groundId)
				.map(GroundMapper.INSTANCE::mapGroundEntityToDto);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_GROUNDS)
	public GroundDTO save(GroundDTO ground) {
		Ground newGround = groundRepository
							.save(GroundMapper.INSTANCE.mapGroundDtoToEntity(ground));
		
		return GroundMapper.INSTANCE.mapGroundEntityToDto(newGround);
	}
	
	@Transactional
	//@CachePut(CacheConfig.ALL_GROUNDS)
	public GroundDTO update(GroundDTO ground, Long groundId) {
		Ground updatedGround = groundRepository
								.findById(groundId)
								.orElseThrow(() -> new EntityNotFoundException("Ground does not exist!"));

		ground.setId(groundId);
		updatedGround = groundRepository.save(GroundMapper.INSTANCE.mapGroundDtoToEntity(ground));
		return GroundMapper.INSTANCE.mapGroundEntityToDto(updatedGround);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_GROUNDS)
	public void deleteById(Long groundId) {
		groundRepository.deleteById(groundId);
	}

}
