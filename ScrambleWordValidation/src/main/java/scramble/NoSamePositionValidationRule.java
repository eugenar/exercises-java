package scramble;

/**
 * Validation rule to check for any matching character at the same position.
 *
 */
public class NoSamePositionValidationRule extends ValidationRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see scramble.ValidationRule#validate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(String scramble, String word, Language language) {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == scramble.charAt(i))
				return false;
		}
		return true;
	}

}
