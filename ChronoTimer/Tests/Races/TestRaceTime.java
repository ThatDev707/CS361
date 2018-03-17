package Races;

import com.haxorz.ChronoTimer.Races.Athlete;
import com.haxorz.ChronoTimer.Races.RaceTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class TestRaceTime {
	RaceTime rt;

	//TODO test sysClock chanag3ed in middle of race

	@Before
	public void before() {
		rt = new RaceTime(new Athlete(1));
	}

	@Test
	public void testDNF() {
		rt.setDNF(true);
		Assert.assertTrue(rt.isDNF());
	}

	@Test
	public void testSetTime() {
		rt.setStartTime(LocalTime.of(10, 0));
	}

	public void testSetTime1() {
		rt.setStartTime(LocalTime.of(10, 0));
		rt.setEndTime(LocalTime.of(10,1));
	}
	public void testString() {
		rt.setStartTime(LocalTime.of(10, 0));
		rt.setEndTime(LocalTime.of(10,1));
		Assert.assertEquals(rt.toStringMinutes(), "01:00.00");
	}
}