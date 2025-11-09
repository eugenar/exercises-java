package rover;

/**
 * Class describing rover's state and functionality:
 * 
 * position, orientation, limits, types of movement
 * 
 * The command string is parsed and the corresponding moves are performed.
 *
 */
public class Rover {
	private int horizontalLimit;
	private int verticalLimit;
	private Cardinal orientation;
	private int horizontal;
	private int vertical;
	private String command;

	/**
	 * Creates rover object.
	 * 
	 * @param horizontalLimit
	 * @param verticalLimit
	 * @param orientation
	 * @param horizontal
	 * @param vertical
	 * @param command
	 */
	public Rover(int horizontalLimit, int verticalLimit, Cardinal orientation, int horizontal, int vertical,
			String command) throws InvalidPositionException {
		this.horizontalLimit = horizontalLimit;
		this.verticalLimit = verticalLimit;
		this.orientation = orientation;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.command = command;
	}

	public void execute() throws InvalidPositionException {
		for (int i = 0; i < command.length(); i++) {
			switch (Instruction.getInstruction(command.charAt(i))) {
			case LEFT:
				rotateLeft();
				break;
			case RIGHT:
				rotateRight();
				break;
			case MOVE:
				move();
				break;
			default: // ignore
				break;
			}
		}
	}

	private void move() throws InvalidPositionException {
		switch (orientation) {
		case NORTH:
			vertical++;
			break;
		case SOUTH:
			vertical--;
			break;
		case EAST:
			horizontal++;
			break;
		case WEST:
			horizontal--;
			break;
		default: // ignore
		}
		if (!isValidPosition())
			throw new InvalidPositionException(String.format("%s %s", horizontal, vertical));
	}

	private void rotateLeft() {
		switch (orientation) {
		case NORTH:
			orientation = Cardinal.WEST;
			break;
		case SOUTH:
			orientation = Cardinal.EAST;
			break;
		case EAST:
			orientation = Cardinal.NORTH;
			break;
		case WEST:
			orientation = Cardinal.SOUTH;
		default: // ignore
		}
	}

	private void rotateRight() {
		switch (orientation) {
		case NORTH:
			orientation = Cardinal.EAST;
			break;
		case SOUTH:
			orientation = Cardinal.WEST;
			break;
		case EAST:
			orientation = Cardinal.SOUTH;
			break;
		case WEST:
			orientation = Cardinal.NORTH;
		default: // ignore
		}
	}

	public String toString() {
		return String.format("%s %s %s", horizontal, vertical, orientation);
	}

	public String getPosition() {
		return toString();
	}

	private boolean isValidPosition() {
		return horizontal >= 0 && vertical >= 0 && horizontal <= horizontalLimit && vertical <= verticalLimit;
	}

	enum Cardinal {
		NORTH("N"), SOUTH("S"), EAST("E"), WEST("W");

		private String value;

		Cardinal(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}

		public static Cardinal getCardinal(String value) {
			for (Cardinal c : Cardinal.values()) {
				if (c.value.equals(value))
					return c;
			}
			return null;
		}
	}

	enum Instruction {
		LEFT('L'), RIGHT('R'), MOVE('M');

		private char value;

		Instruction(char value) {
			this.value = value;
		}

		public static Instruction getInstruction(char value) {
			for (Instruction i : Instruction.values()) {
				if (i.value == value)
					return i;
			}
			return null;
		}
	}

}
