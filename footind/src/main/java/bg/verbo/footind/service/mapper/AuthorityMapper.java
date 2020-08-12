package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.auth.Authority;
import bg.verbo.footind.dto.auth.AuthorityDTO;

@Mapper
public interface AuthorityMapper {
	AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

	@Mapping(target="name", source="dto.authority")
	Authority mapAuthorityDtoToEntity(AuthorityDTO dto);

	@Mapping(target="authority", source="entity.name")
	AuthorityDTO mapAuthorityEntityToDto(Authority entity);
}
