/**
 * Represents an arithmetic expression with 2 rational numbers as operands and 1
 * operation.
 *
 */
public class ArithmeticExpression {

	public enum Operation {
		ADD, SUBTRACT, MULTIPLY, DIVIDE, SIMPLIFY
	}

	Operation operation;
	RationalNumber leftOperand;
	RationalNumber rightOperand;

	public ArithmeticExpression(String s) {
		String[] expr = s.split("\\s+");
		if (expr.length > 3)
			throw new IllegalArgumentException(String
					.format("Expression %s must consist of 3 white space separated terms on one line, e.g., 2 + 3", s));

		if (expr.length == 1) {
			this.leftOperand = new RationalNumber(expr[0]);
			this.operation = Operation.SIMPLIFY;
		}
		else {
			switch (expr[1]) {
			case "+":
				this.operation = Operation.ADD;
				break;
			case "-":
				this.operation = Operation.SUBTRACT;
				break;
			case "*":
				this.operation = Operation.MULTIPLY;
				break;
			case "/":
				this.operation = Operation.DIVIDE;
				break;
			default:
				throw new IllegalArgumentException(String.format("Unsupported operation type %s", operation));

			}
			this.leftOperand = new RationalNumber(expr[0]);
			this.rightOperand = new RationalNumber(expr[2]);
		}
	}

	RationalNumber calculate() {
		switch (operation) {
		case ADD:
			return leftOperand.add(rightOperand);
		case SUBTRACT:
			return leftOperand.subtract(rightOperand);
		case MULTIPLY:
			return leftOperand.multiply(rightOperand);
		case DIVIDE:
			return leftOperand.divide(rightOperand);
		case SIMPLIFY:
			return this.leftOperand;
		default:
			throw new IllegalArgumentException(String.format("Unsupported operation type %s", operation));
		}
	}

}
