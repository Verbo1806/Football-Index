package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.StadiumCategory;
import bg.verbo.footind.dto.StadiumCategoryDTO;

@Mapper
public interface StadiumCategoryMapper {
	StadiumCategoryMapper INSTANCE = Mappers.getMapper(StadiumCategoryMapper.class);

	StadiumCategory mapStadiumCategoryDtoToEntity(StadiumCategoryDTO dto);

	StadiumCategoryDTO mapStadiumCategoryEntityToDto(StadiumCategory entity);
}
