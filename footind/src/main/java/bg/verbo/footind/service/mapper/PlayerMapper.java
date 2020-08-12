package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.Player;
import bg.verbo.footind.dto.PlayerDTO;

@Mapper
public interface PlayerMapper {
	PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

	Player mapPlayerDtoToEntity(PlayerDTO dto);

	@Mapping(target = "team.players", ignore = true)
	PlayerDTO mapPlayerEntityToDto(Player entity);
}
