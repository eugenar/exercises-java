/**
 * Represents a rational fraction comprised of numerator and denominator.
 *
 */
public class RationalFraction {

	long numerator;
	long denominator;

	public RationalFraction(String s) {
		String[] parts = s.split("/");
		if (parts.length != 2)
			throw new IllegalArgumentException(
					String.format("Term fraction %s must have numerator and denominator, e.g., 3/4", s));

		try {
			this.numerator = Long.parseLong(parts[0]);
			this.denominator = Long.parseLong(parts[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					String.format("Term fraction %s must have numerator and denominator, e.g., 3/4", s));
		}
		if (denominator == 0)
			throw new IllegalArgumentException(String.format("Fraction %s denominator is 0", s));
		reduce();
	}

	public RationalFraction() {
		this.numerator = 0;
		this.denominator = 1;
	}

	/**
	 * Create rational fraction in simplest form: reduced with positive
	 * denominator
	 * 
	 * @param numerator
	 * @param denominator
	 */
	public RationalFraction(long numerator, long denominator) {
		if (denominator == 0)
			throw new IllegalArgumentException("Fraction denominator is 0");
		this.numerator = numerator;
		this.denominator = denominator;
		reduce();
	}

	@Override
	public String toString() {
		return String.format("%d/%d", this.numerator, this.denominator);
	}

	void reduce() {
		if (this.denominator < 0) {
			this.numerator = -this.numerator;
			this.denominator = -this.denominator;
		}
		long d = gcd(Math.abs(numerator), denominator);
		this.numerator /= d;
		this.denominator /= d;
	}

	RationalFraction add(RationalFraction frac) {
		long d = gcd(this.denominator, frac.denominator);
		return new RationalFraction(((frac.denominator / d) * this.numerator + (this.denominator / d) * frac.numerator),
				lcm(this.denominator, frac.denominator));
	}

	RationalFraction multiply(RationalFraction frac) {
		return new RationalFraction(this.numerator * frac.numerator, this.denominator * frac.denominator);
	}

	RationalFraction divide(RationalFraction frac) {
		if (frac.numerator == 0)
			throw new IllegalArgumentException("Division through 0");
		return new RationalFraction(this.numerator * frac.denominator, this.denominator * frac.numerator);
	}

	RationalFraction negate() {
		return new RationalFraction(-numerator, denominator);
	}

	static long gcd(long a, long b) {
		while (b > 0) {
			long c = a % b;
			a = b;
			b = c;
		}
		return a;
	}

	static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
	}
}
