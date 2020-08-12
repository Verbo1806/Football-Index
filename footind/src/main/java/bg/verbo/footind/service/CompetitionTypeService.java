package bg.verbo.footind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.verbo.footind.db.repository.CompetitionTypeRepository;
import bg.verbo.footind.dto.CompetitionTypeDTO;
import bg.verbo.footind.service.mapper.CompetitionTypeMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompetitionTypeService {
	private CompetitionTypeRepository competitionTypeRepository;

	public List<CompetitionTypeDTO> findAll() {
		return competitionTypeRepository
				.findAll()
				.stream()
				.map(CompetitionTypeMapper.INSTANCE::mapCompetitionTypeEntityToDto)
				.collect(Collectors.toList());
	}

}
