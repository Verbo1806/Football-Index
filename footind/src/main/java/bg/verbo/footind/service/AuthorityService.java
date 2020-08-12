package bg.verbo.footind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.verbo.footind.db.repository.auth.AuthorityRepository;
import bg.verbo.footind.dto.auth.AuthorityDTO;
import bg.verbo.footind.service.mapper.AuthorityMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorityService {
	private AuthorityRepository authorityRepository;

	public List<AuthorityDTO> findAll() {
		return authorityRepository
				.findAll()
				.stream()
				.map(AuthorityMapper.INSTANCE::mapAuthorityEntityToDto)
				.collect(Collectors.toList());
	}

}
