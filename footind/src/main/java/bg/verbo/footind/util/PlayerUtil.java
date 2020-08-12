package bg.verbo.footind.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import bg.verbo.footind.data.PlayerNames;
import bg.verbo.footind.dto.PlayerDTO;

public class PlayerUtil {
	private static Random rand = new Random();
	
	private PlayerUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static PlayerDTO generatePlayer() {
		PlayerDTO newPlayer = new PlayerDTO();
		newPlayer.setShirtNumber((short) rand.nextInt(99));
		newPlayer.setBornAt(generateBirthDate());
		newPlayer.setFullName(generatePlayerName());
		
		return newPlayer;
	}

	private static String generatePlayerName() {
		return PlayerNames.FIRST_NAME[rand.nextInt(PlayerNames.FIRST_NAME.length)] + " " +
				PlayerNames.LAST_NAME[rand.nextInt(PlayerNames.LAST_NAME.length)];
	}

	private static Date generateBirthDate() {
		GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1980, 2003);
        gc.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        
        return gc.getTime();
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
