
public class Board {

	enum Status {
		NONE, WIN, DRAW
	};

	int dim;
	char[] chars;
	char[][] board;
	int[][] rowCnt;
	int[][] colCnt;
	int diagCnt;
	int diagCnt2;
	int nextMoveChar = 0;
	Status status = Status.NONE;

	/**
	 * @param board
	 */
	public Board(int dim, char[] chars) {
		char[][] b = new char[dim][dim];
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length; j++) {
				b[i][j] = Character.MIN_VALUE;
			}
		}
		board = b;

		rowCnt = new int[2][dim];
		colCnt = new int[2][dim];
		this.chars = chars;
		this.dim = dim;
	}

	Status add(int x, int y) throws IllegalMoveException, Board.GameEndException {
		if (status == Status.DRAW || status == Status.WIN)
			throw new GameEndException();

		char c = chars[nextMoveChar];

		if (!isLegal(x, y))
			throw new IllegalMoveException();

		board[x][y] = c;

		rowCnt[nextMoveChar][x]++;
		colCnt[nextMoveChar][y]++;
		if (x == y)
			diagCnt++;
		if (x == dim - 1 - y)
			diagCnt2++;

		if (rowCnt[nextMoveChar][x] == dim || colCnt[nextMoveChar][y] == dim || diagCnt == dim || diagCnt2 == dim) {
			status = Status.WIN;
			return Status.WIN;
		}
		nextMoveChar = (nextMoveChar + 1) % 2;

		if (isFull()) {
			status = Status.DRAW;
			return Status.DRAW;
		}
		status = Status.NONE;
		return Status.NONE;
	}

	boolean isLegal(int x, int y) {
		return board[x][y] == Character.MIN_VALUE;
	}

	boolean isFull() {
		for (int i = 0; i < board.length; i++) {
			if (rowCnt[0][i] + rowCnt[1][i] == dim)
				return true;
		}
		return false;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (char[] row : board) {
			for (char c : row) {
				if (c == Character.MIN_VALUE)
					sb.append('-');
				else
					sb = sb.append(c);
				sb.append('|');
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	void print() {
		System.out.println(this.toString());
	}

	class IllegalMoveException extends Exception {

		private static final long serialVersionUID = 1L;

	}

	class GameEndException extends Exception {

		private static final long serialVersionUID = 2L;

	}

}
