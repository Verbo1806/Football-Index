package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.Confederation;
import bg.verbo.footind.dto.ConfederationDTO;

@Mapper
public interface ConfederationMapper {
	ConfederationMapper INSTANCE = Mappers.getMapper(ConfederationMapper.class);

	Confederation mapConfederationDtoToEntity(ConfederationDTO dto);

	ConfederationDTO mapConfederationEntityToDto(Confederation entity);
}
