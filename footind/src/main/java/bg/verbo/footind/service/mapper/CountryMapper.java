package bg.verbo.footind.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import bg.verbo.footind.db.entity.Country;
import bg.verbo.footind.dto.CountryDTO;

@Mapper
public interface CountryMapper {
	CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

	Country mapCountryDtoToEntity(CountryDTO dto);

	CountryDTO mapCountryEntityToDto(Country entity);
}
