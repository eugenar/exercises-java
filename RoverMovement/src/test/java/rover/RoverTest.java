package rover;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import rover.Rover.Cardinal;

/**
 * Tests for Rover class
 *
 */
public class RoverTest {

	@Test(expected = InvalidPositionException.class)
	public void RoverInvalidPositionTest() throws InvalidPositionException {
		Rover r = new Rover(5, 5, Cardinal.NORTH, 1, 2, "MMMM");
		r.execute();
	}

	@Test
	public void RoverMoveTest() throws InvalidPositionException {
		Rover r = new Rover(5, 5, Cardinal.NORTH, 1, 2, "M");
		r.execute();
		assertEquals("1 3 N", r.getPosition());
	}

	@Test
	public void RoverRotateLeftTest() throws InvalidPositionException {
		Rover r = new Rover(5, 5, Cardinal.NORTH, 1, 2, "L");
		r.execute();
		assertEquals("1 2 W", r.getPosition());
	}

	@Test
	public void RoverRotateRightTest() throws InvalidPositionException {
		Rover r = new Rover(5, 5, Cardinal.NORTH, 1, 2, "R");
		r.execute();
		assertEquals("1 2 E", r.getPosition());
	}

	@Test
	public void RoverEmptyCommandTest() throws InvalidPositionException {
		Rover r = new Rover(5, 5, Cardinal.NORTH, 1, 2, "");
		r.execute();
		assertEquals("1 2 N", r.getPosition());
	}
	
}
