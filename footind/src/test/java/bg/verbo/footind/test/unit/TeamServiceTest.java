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

import bg.verbo.footind.db.entity.Team;
import bg.verbo.footind.db.repository.TeamRepository;
import bg.verbo.footind.dto.TeamDTO;
import bg.verbo.footind.service.TeamService;
import bg.verbo.footind.service.mapper.TeamMapper;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

	private TeamService service;
	private Team team;
	private ArrayList<Team> allTeams;

	@Mock
	TeamRepository mockTeamRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new TeamService(mockTeamRepository);
        
        team = new Team();
        team.setId((long) 160);
        team.setName("New team");
	    team.setAbbreaviature("NEW");
	    team.setFounded("2020");
	    
	    allTeams = new ArrayList<>();
	    allTeams.add(team);
    }
	
	@Test
	public void testFindAll() {
	    when(mockTeamRepository.findAll()).
	        thenReturn(allTeams);

	    List<TeamDTO> teamDTOs = service.findAll();

	    Assertions.assertEquals(1, teamDTOs.size());
	    TeamDTO actualDTO = teamDTOs.get(0);

	    Assertions.assertEquals(team.getName(), actualDTO.getName());
	    Assertions.assertEquals(team.getAbbreaviature(), actualDTO.getAbbreaviature());
	}
	
	@Test
	public void testCreate() {
		Team newTeam = new Team();
		newTeam.setName("2");
		newTeam.setAbbreaviature("2");
		newTeam.setFounded("2020");
	    
		when(mockTeamRepository.save(newTeam))
        	.thenReturn(newTeam);
		
		TeamDTO testTeam = service.save(TeamMapper.INSTANCE.mapTeamEntityToDto(newTeam));
		allTeams.add(newTeam);
		Assertions.assertEquals(2, allTeams.size());
		
		Assertions.assertEquals(testTeam.getName(), newTeam.getName());
	    Assertions.assertEquals(testTeam.getAbbreaviature(), newTeam.getAbbreaviature());
	}
	
	@Test
	public void testUpdate() {
		when(mockTeamRepository.save(team))
        	.thenReturn(team);
		
		team.setAbbreaviature("NTM");
		TeamDTO updatedTeam = service.save(TeamMapper.INSTANCE.mapTeamEntityToDto(team));

		Assertions.assertEquals(updatedTeam.getName(), team.getName());
	    Assertions.assertEquals(updatedTeam.getAbbreaviature(), team.getAbbreaviature());
	}

}
