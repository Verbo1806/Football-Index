package bg.verbo.footind.test.unit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.verbo.footind.db.entity.League;
import bg.verbo.footind.db.repository.LeagueRepository;
import bg.verbo.footind.dto.LeagueDTO;
import bg.verbo.footind.service.LeagueService;
import bg.verbo.footind.service.mapper.LeagueMapper;

@ExtendWith(MockitoExtension.class)
public class LeagueServiceTest {

	private LeagueService service;
	private League league;
	private ArrayList<League> allLeagues;

	@Mock
	LeagueRepository mockLeagueRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new LeagueService(mockLeagueRepository);
        
        league = new League();
        league.setId((long) 160);
        league.setName("New league");
	    league.setFounded("2020");
	    
	    allLeagues = new ArrayList<>();
	    allLeagues.add(league);
    }
	
	@Test
	public void testFindAll() {
	    when(mockLeagueRepository.findAll()).
	        thenReturn(allLeagues);

	    List<LeagueDTO> leagueDTOs = service.findAll();

	    Assertions.assertEquals(1, leagueDTOs.size());
	    LeagueDTO actualDTO = leagueDTOs.get(0);

	    Assertions.assertEquals(league.getName(), actualDTO.getName());
	    Assertions.assertEquals(league.getFounded(), actualDTO.getFounded());
	}
	
	@Test
	public void testCreate() {
		League newLeague = new League();
		newLeague.setName("2");
		newLeague.setFounded("2020");
	    
		when(mockLeagueRepository.save(newLeague))
        	.thenReturn(newLeague);
		
		LeagueDTO testLeague = service.save(LeagueMapper.INSTANCE.mapLeagueEntityToDto(newLeague));
		allLeagues.add(newLeague);
		Assertions.assertEquals(2, allLeagues.size());
		
		Assertions.assertEquals(testLeague.getName(), newLeague.getName());
	    Assertions.assertEquals(testLeague.getFounded(), newLeague.getFounded());
	}
	
	@Test
	public void testUpdate() {
		when(mockLeagueRepository.save(league))
        	.thenReturn(league);
		
		league.setFounded("2019");
		LeagueDTO updatedLeague = service.save(LeagueMapper.INSTANCE.mapLeagueEntityToDto(league));

		Assertions.assertEquals(updatedLeague.getName(), league.getName());
	    Assertions.assertEquals(updatedLeague.getFounded(), league.getFounded());
	}

}
