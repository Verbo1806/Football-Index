package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.Team;
import bg.verbo.footind.dto.TeamDTO;

@Mapper
public interface TeamMapper {
	TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

	Team mapTeamDtoToEntity(TeamDTO dto);

	@Mapping(target = "players", ignore = true)
	TeamDTO mapTeamEntityToDto(Team entity);
}
