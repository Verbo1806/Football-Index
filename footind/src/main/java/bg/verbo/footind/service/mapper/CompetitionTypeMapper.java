package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.CompetitionType;
import bg.verbo.footind.dto.CompetitionTypeDTO;

@Mapper
public interface CompetitionTypeMapper {
	CompetitionTypeMapper INSTANCE = Mappers.getMapper(CompetitionTypeMapper.class);

	CompetitionType mapCompetitionTypeDtoToEntity(CompetitionTypeDTO dto);

	CompetitionTypeDTO mapCompetitionTypeEntityToDto(CompetitionType entity);
}
