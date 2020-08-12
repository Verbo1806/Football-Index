package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.League;
import bg.verbo.footind.dto.LeagueDTO;

@Mapper
public interface LeagueMapper {
	LeagueMapper INSTANCE = Mappers.getMapper(LeagueMapper.class);

	League mapLeagueDtoToEntity(LeagueDTO dto);

	LeagueDTO mapLeagueEntityToDto(League entity);
}
