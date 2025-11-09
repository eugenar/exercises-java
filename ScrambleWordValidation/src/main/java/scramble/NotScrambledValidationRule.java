package scramble;

/**
 * Validation rule to check if a word is scrambled.
 *
 */
public class NotScrambledValidationRule extends ValidationRule {

	@Override
	public boolean validate(String scramble, String word, Language language) {
		if (scramble == null || scramble.length() == 0 || word == null || word.length() == 0
				|| scramble.length() != word.length())
			return true;
		return word.equals(scramble);
	}

}
