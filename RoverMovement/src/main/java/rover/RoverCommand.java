package rover;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates all rover objects.
 *
 */
public class RoverCommand {

	private String input;
	private int horizontalLimit;
	private int verticalLimit;
	private List<Rover> rovers;

	/**
	 * Creates all Rover objects based on the input.
	 * 
	 * @param input
	 * @throws InvalidPositionException
	 */
	public RoverCommand(String input) throws InvalidPositionException {
		this.input = input;
		initialize();
	}

	/**
	 * Execute the instructions for each Rover sequentially.
	 */
	public void execute() {
		for (Rover r : rovers)
			try {
				r.execute();
			} catch (InvalidPositionException e) {
				// log
			}
	}

	/**
	 * Get the positions of all Rover objects as a String separated by newline.
	 * 
	 * @return
	 */
	public String getPositions() {
		StringBuffer sb = new StringBuffer();
		for (Rover r : rovers)
			sb.append(r.getPosition()).append(System.getProperty("line.separator"));
		return sb.length() > 0 ? sb.substring(0, sb.length() - System.getProperty("line.separator").length()) : null;
	}

	/**
	 * Initialize Rover objects based on input. Invalid input lines are ignored.
	 * 
	 * @throws InvalidPositionException
	 */
	private void initialize() throws InvalidPositionException {
		if (input == null || input.isEmpty())
			throw new IllegalArgumentException("No input.");
		String lines[] = input.split(System.getProperty("line.separator"));
		if (lines.length < 3)
			throw new IllegalArgumentException("Malformed input.");
		String[] limits = lines[0].trim().split(" ");
		if (limits.length < 2)
			throw new IllegalArgumentException("No position limits.");
		verticalLimit = Integer.parseInt(limits[0]);
		horizontalLimit = Integer.parseInt(limits[1]);
		if (!isValidLimitPosition())
			throw new InvalidPositionException(String.format("%s %s", horizontalLimit, verticalLimit));
		rovers = new ArrayList<Rover>();

		// expect pairs of lines for each rover
		for (int i = 1; i < lines.length && i + 1 < lines.length; i = i + 2) {
			String[] positions = lines[i].trim().split(" ");
			if (positions.length < 3)
				continue;
			int horizontal = Integer.parseInt(positions[0]);
			int vertical = Integer.parseInt(positions[1]);
			if (!isValidPosition(horizontal, vertical))
				continue;
			Rover.Cardinal orientation = Rover.Cardinal.getCardinal(positions[2]);
			if (orientation == null)
				continue;

			rovers.add(new Rover(horizontalLimit, verticalLimit, orientation, horizontal, vertical, lines[i + 1]));
		}
	}

	private boolean isValidLimitPosition() {
		return horizontalLimit >= 0 && verticalLimit >= 0;
	}

	private boolean isValidPosition(int horizontal, int vertical) {
		return horizontal >= 0 && vertical >= 0 && horizontal < horizontalLimit && vertical < verticalLimit;
	}

}
