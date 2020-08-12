package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.Ground;
import bg.verbo.footind.dto.GroundDTO;

@Mapper
public interface GroundMapper {
	GroundMapper INSTANCE = Mappers.getMapper(GroundMapper.class);

	Ground mapGroundDtoToEntity(GroundDTO dto);

	GroundDTO mapGroundEntityToDto(Ground entity);
}
