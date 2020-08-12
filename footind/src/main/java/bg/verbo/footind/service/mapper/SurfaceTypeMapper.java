package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.SurfaceType;
import bg.verbo.footind.dto.SurfaceTypeDTO;

@Mapper
public interface SurfaceTypeMapper {
	SurfaceTypeMapper INSTANCE = Mappers.getMapper(SurfaceTypeMapper.class);

	SurfaceType mapSurfaceTypeDtoToEntity(SurfaceTypeDTO dto);

	SurfaceTypeDTO mapSurfaceTypeEntityToDto(SurfaceType entity);
}
