package bg.verbo.footind.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bg.verbo.footind.db.entity.auth.User;
import bg.verbo.footind.db.repository.auth.UserRepository;
import bg.verbo.footind.dto.auth.AuthorityDTO;
import bg.verbo.footind.dto.auth.UserPrincipal;
import bg.verbo.footind.service.mapper.AuthorityMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
	private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<AuthorityDTO> authorities = user.getAuthorities()
        		.stream()
        		.map(AuthorityMapper.INSTANCE::mapAuthorityEntityToDto)
        		.collect(Collectors.toList());
        return new UserPrincipal(user.getUsername(), user.getPassword(), authorities);
    }
	
	public List<UserDetails> findAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDetails> mappedUsers = new ArrayList<>();
		
		users.stream()
			 .forEach(u -> mappedUsers.add(new UserPrincipal(u.getUsername(), null, 
				u.getAuthorities().stream()
				.map(AuthorityMapper.INSTANCE::mapAuthorityEntityToDto)
        		.collect(Collectors.toList()))));
		
		return mappedUsers;
	}
}
