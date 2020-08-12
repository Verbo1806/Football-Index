package bg.verbo.footind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.verbo.footind.db.repository.CountryRepository;
import bg.verbo.footind.dto.CountryDTO;
import bg.verbo.footind.service.mapper.CountryMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryService {
	private CountryRepository countryRepository;

	public List<CountryDTO> findAll() {
		return countryRepository
				.findAll()
				.stream()
				.map(CountryMapper.INSTANCE::mapCountryEntityToDto)
				.collect(Collectors.toList());
	}

}
