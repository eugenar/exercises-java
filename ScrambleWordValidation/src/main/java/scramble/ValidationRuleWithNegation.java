package scramble;

/**
 * Wrapper around ValidationRule including logical 'negation' operator.
 *
 */
class ValidationRuleWithNegation {
	boolean isNegation;
	ValidationRule rule;

	/**
	 * @param isNegation
	 * @param rule
	 */
	ValidationRuleWithNegation(boolean isNegation, ValidationRule rule) {
		this.isNegation = isNegation;
		this.rule = rule;
	}
}
