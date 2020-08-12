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

import bg.verbo.footind.db.entity.Player;
import bg.verbo.footind.db.repository.PlayerRepository;
import bg.verbo.footind.dto.PlayerDTO;
import bg.verbo.footind.service.PlayerService;
import bg.verbo.footind.service.mapper.PlayerMapper;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

	private PlayerService service;
	private Player player;
	private ArrayList<Player> allPlayers;

	@Mock
	PlayerRepository mockPlayerRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new PlayerService(mockPlayerRepository);
        
        player = new Player();
        player.setId((long) 160);
        player.setFullName("New player");
	    player.setShirtNumber((short) 19);
	    
	    allPlayers = new ArrayList<>();
	    allPlayers.add(player);
    }
	
	@Test
	public void testFindAll() {
	    when(mockPlayerRepository.findAll()).
	        thenReturn(allPlayers);

	    List<PlayerDTO> playerDTOs = service.findAll();

	    Assertions.assertEquals(1, playerDTOs.size());
	    PlayerDTO actualDTO = playerDTOs.get(0);

	    Assertions.assertEquals(player.getFullName(), actualDTO.getFullName());
	}
	
	@Test
	public void testCreate() {
		Player newPlayer = new Player();
		newPlayer.setFullName("2");
		newPlayer.setShirtNumber((short) 3);
	    
		when(mockPlayerRepository.save(newPlayer))
        	.thenReturn(newPlayer);
		
		PlayerDTO testPlayer = service.save(PlayerMapper.INSTANCE.mapPlayerEntityToDto(newPlayer));
		allPlayers.add(newPlayer);
		Assertions.assertEquals(2, allPlayers.size());
		
		Assertions.assertEquals(testPlayer.getFullName(), newPlayer.getFullName());
	}
	
	@Test
	public void testUpdate() {
		when(mockPlayerRepository.save(player))
        	.thenReturn(player);
		
		player.setShirtNumber((short) 9);
		PlayerDTO updatedPlayer = service.save(PlayerMapper.INSTANCE.mapPlayerEntityToDto(player));

		Assertions.assertEquals(updatedPlayer.getFullName(), player.getFullName());
	}

}
