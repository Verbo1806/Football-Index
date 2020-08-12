package bg.verbo.footind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.verbo.footind.db.repository.StadiumCategoryRepository;
import bg.verbo.footind.dto.StadiumCategoryDTO;
import bg.verbo.footind.service.mapper.StadiumCategoryMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StadiumCategoryService {
	private StadiumCategoryRepository stadiumCategoryRepository;

	public List<StadiumCategoryDTO> findAll() {
		return stadiumCategoryRepository
				.findAll()
				.stream()
				.map(StadiumCategoryMapper.INSTANCE::mapStadiumCategoryEntityToDto)
				.collect(Collectors.toList());
	}
	
	public List<StadiumCategoryDTO> findByExample(Optional<String> code, Optional<String> name) {
		return stadiumCategoryRepository
				.findByCodeAndName(code.orElse(null), name.orElse(null))
				.stream()
				.map(StadiumCategoryMapper.INSTANCE::mapStadiumCategoryEntityToDto)
				.collect(Collectors.toList());
	}

}
