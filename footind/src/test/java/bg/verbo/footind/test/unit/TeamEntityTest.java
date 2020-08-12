package bg.verbo.footind.test.unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.verbo.footind.db.entity.Team;

@ExtendWith(MockitoExtension.class)
public class TeamEntityTest {

	@Test
	public void testEquals() {
	    Team x = Team.builder().id((long) 1).build();
	    Team y = Team.builder().id((long) 1).build();
	    Assert.assertEquals(x.equals(y), y.equals(x));
	    Assert.assertEquals(x.hashCode(), y.hashCode());
	}

}
