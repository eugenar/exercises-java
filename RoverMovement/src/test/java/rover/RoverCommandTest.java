package rover;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for RoverCommand
 *
 */
public class RoverCommandTest {

	@Test
	public void RoverCommand2Test() throws InvalidPositionException {
		String input = "5 5" + System.getProperty("line.separator") + "1 2 N" + System.getProperty("line.separator")
				+ "LMLMLMLMM" + System.getProperty("line.separator") + "3 3 E" + System.getProperty("line.separator")
				+ "MMRMMRMRRM";

		RoverCommand rc = new RoverCommand(input);
		rc.execute();
		String output = rc.getPositions();
		String expected = "1 3 N" + System.getProperty("line.separator") + "5 1 E";
		assertEquals(expected, output);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void RoverCommandInvalidInputTest() throws InvalidPositionException{
		String input = "5 5" + System.getProperty("line.separator") + "1 2 N";
		RoverCommand rc = new RoverCommand(input);
		rc.execute();
	}

	@Test(expected=InvalidPositionException.class)
	public void RoverCommandInvalidPositionTest() throws InvalidPositionException{
		String input = "-1 5" + System.getProperty("line.separator") + "1 2 N"+ System.getProperty("line.separator")
		+ "MMMM";
		RoverCommand rc = new RoverCommand(input);
		rc.execute();
	}
	
}
