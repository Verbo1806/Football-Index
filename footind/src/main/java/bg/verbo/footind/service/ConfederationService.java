package bg.verbo.footind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.verbo.footind.db.entity.Confederation;
import bg.verbo.footind.db.repository.ConfederationRepository;
import bg.verbo.footind.dto.ConfederationDTO;
import bg.verbo.footind.service.mapper.ConfederationMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfederationService {
	private ConfederationRepository confederationRepository;
	
	//@Cacheable(CacheConfig.ALL_CONFEDERATIONS)
	public List<ConfederationDTO> findAll() {
		return confederationRepository
				.findAll()
				.stream()
				.map(ConfederationMapper.INSTANCE::mapConfederationEntityToDto)
				.collect(Collectors.toList());
	}

	public Optional<ConfederationDTO> findById(Long confederationId) {
		return confederationRepository
				.findById(confederationId)
				.map(ConfederationMapper.INSTANCE::mapConfederationEntityToDto);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_CONFEDERATIONS)
	public ConfederationDTO save(ConfederationDTO confederation) {
		Confederation newConfederation = confederationRepository
							.save(ConfederationMapper.INSTANCE.mapConfederationDtoToEntity(confederation));
		
		return ConfederationMapper.INSTANCE.mapConfederationEntityToDto(newConfederation);
	}
	
	@Transactional
	//@CachePut(CacheConfig.ALL_CONFEDERATIONS)
	public ConfederationDTO update(ConfederationDTO confederation, Long confederationId) {
		Confederation updatedConfederation = confederationRepository
								.findById(confederationId)
								.orElseThrow(() -> new EntityNotFoundException("Confederation does not exist!"));

		confederation.setId(confederationId);
		updatedConfederation = confederationRepository.save(ConfederationMapper.INSTANCE.mapConfederationDtoToEntity(confederation));
		return ConfederationMapper.INSTANCE.mapConfederationEntityToDto(updatedConfederation);
	}

	@Transactional
	//@CachePut(CacheConfig.ALL_CONFEDERATIONS)
	public void deleteById(Long confederationId) {
		confederationRepository.deleteById(confederationId);
	}

}
