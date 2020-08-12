package bg.verbo.footind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.verbo.footind.db.repository.SurfaceTypeRepository;
import bg.verbo.footind.dto.SurfaceTypeDTO;
import bg.verbo.footind.service.mapper.SurfaceTypeMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurfaceTypeService {
	private SurfaceTypeRepository surfaceTypeRepository;

	public List<SurfaceTypeDTO> findAll() {
		return surfaceTypeRepository
				.findAll()
				.stream()
				.map(SurfaceTypeMapper.INSTANCE::mapSurfaceTypeEntityToDto)
				.collect(Collectors.toList());
	}

}
