public class TTTGame {

	public static void main(String[] args) {
		Board board = new Board(3, new char[] { 'X', '0' });
		board.print();

		move(board, 0, 1);
		move(board, 0, 2);
		move(board, 1, 1);
		move(board, 1, 2);
		move(board, 2, 1);
	}

	static Board.Status move(Board board, int x, int y) {
		Board.Status status = Board.Status.NONE;
		try {
			status = board.add(x, y);
		} catch (Board.IllegalMoveException | Board.GameEndException e) {
			e.printStackTrace();
		}
		board.print();
		System.out.println(board.getStatus());
		return status;
	}

}
