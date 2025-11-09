/**
 * Represents a rational number with integer and fractional parts.
 *
 */
public class RationalNumber {

	long integral;
	RationalFraction fraction;

	public RationalNumber(String s) {
		String[] parts = s.split("_");
		if (parts.length > 2)
			throw new IllegalArgumentException(
					String.format("Term %s must have at most one fractional part, e.g., 2_3/4", s));

		try {
			if (parts.length == 1) {
				if (parts[0].contains("/")) {
					this.integral = 0;
					this.fraction = new RationalFraction(parts[0]);
				} else {
					this.integral = Long.parseLong(parts[0]);
					this.fraction = new RationalFraction();
				}
			} else {
				this.integral = parts[0].isEmpty() ? 0 : Long.parseLong(parts[0]);
				this.fraction = new RationalFraction(parts[1]);
				if (this.integral < 0)
					this.fraction = this.fraction.negate();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(String.format("Term %s is not in correct format, e.g., 2_3/4", s));
		}

		normalize();
	}

	public RationalNumber(RationalFraction fraction) {
		this.integral = 0;
		this.fraction = fraction;
		normalize();
	}

	/**
	 * @param integral
	 * @param fraction
	 */
	public RationalNumber(long integral, RationalFraction fraction) {
		this.integral = integral;
		this.fraction = fraction;
		normalize();
	}

	RationalNumber add(RationalNumber ratNum) {
		return new RationalNumber(this.integral + ratNum.integral, this.fraction.add(ratNum.fraction));
	}

	RationalNumber subtract(RationalNumber ratNum) {
		return this.add(ratNum.negate());
	}

	RationalNumber multiply(RationalNumber ratNum) {
		return new RationalNumber(this.toRationalFraction().multiply(ratNum.toRationalFraction()));
	}

	RationalNumber divide(RationalNumber ratNum) {
		return new RationalNumber(this.toRationalFraction().divide(ratNum.toRationalFraction()));
	}

	@Override
	public String toString() {
		if (this.fraction == null || this.fraction.numerator == 0)
			return String.format("%d", integral);
		else if (this.integral == 0)
			return this.fraction.toString();
		else if (this.integral < 0)
			return String.format("%d_%s", this.integral, this.fraction.negate().toString());
		return String.format("%d_%s", this.integral, this.fraction.toString());
	}

	RationalFraction toRationalFraction() {
		return new RationalFraction(this.integral * this.fraction.denominator + this.fraction.numerator,
				this.fraction.denominator);
	}

	RationalNumber negate() {
		return new RationalNumber(-integral, fraction.negate());
	}

	/**
	 * Transform to mixed proper rational fraction and adjust to have integral
	 * part and fractional part numerator have the same sign.
	 */
	void normalize() {
		long num = Math.abs(this.fraction.numerator);
		if (num > this.fraction.denominator) {
			if (this.fraction.numerator < 0) {
				this.integral -= num / this.fraction.denominator;
				this.fraction = new RationalFraction(-(num % this.fraction.denominator), this.fraction.denominator);
			} else {
				this.integral += num / this.fraction.denominator;
				this.fraction = new RationalFraction(num % this.fraction.denominator, this.fraction.denominator);
			}
		}
		if (this.integral < 0 && this.fraction.numerator > 0) {
			this.integral++;
			this.fraction = new RationalFraction(this.fraction.numerator - this.fraction.denominator,
					this.fraction.denominator);
		} else if (this.integral > 0 && this.fraction.numerator < 0) {
			this.integral--;
			this.fraction = new RationalFraction(this.fraction.denominator - num, this.fraction.denominator);
		}
	}

}
