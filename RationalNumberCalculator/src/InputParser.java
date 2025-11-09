import java.util.Scanner;

/**
 * Parses arithmetic expressions from stdin. Expression terms are white space
 * separate. Ends parsing on an empty new line.
 *
 */
public class InputParser {

	private Scanner scan = new Scanner(System.in);

	public void parse() {
		String line = null;
		while (!(line = scan.nextLine()).isEmpty()) {
			try {
				RationalNumber result = new ArithmeticExpression(line).calculate();
				System.out.println(result.toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("Exiting application due to entering empty line.");
	}
}
