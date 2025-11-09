package scramble;

/**
 * Validation rule for first character and two consecutive character matching.
 *
 */
public class FirstOrConsecutivePositionValidationRule extends ValidationRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see scramble.ValidationRule#validate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(String scramble, String word, Language language) {
		if (scramble.charAt(0) == word.charAt(0))
			return true;
		for (int i = 0, j = 1; j < word.length(); i++, j++) {
			if (scramble.charAt(i) == word.charAt(i) && scramble.charAt(j) == word.charAt(j))
				return true;
		}
		return false;
	}

}
