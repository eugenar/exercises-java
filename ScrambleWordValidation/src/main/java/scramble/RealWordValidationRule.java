package scramble;

/**
 * Validation rule to check if the scramble looks like a real word according to
 * the rules:
 * 
 * 1. letters alternate between vowels and consonants (with ‘Y’ being a vowel
 * for this purpose)
 * 
 * 2. following combinations of vowels and consonants are allowed:
 * 
 * AI AY EA EE EO IO OA OO OY YA
 * 
 * YO YU BL BR CH CK CL CR DR FL
 * 
 * FR GH GL GR KL KR KW PF PL PR
 * 
 * SC SCH SCR SH SHR SK SL SM SN SP
 * 
 * SQ ST SW TH THR TR TW WH WR
 * 
 * 3. all double consonants are allowed
 * 
 * 4. no other combinations are allowed. For instance, SWR doesn’t look real
 * even though both SW and WR are independently looking real
 *
 */
public class RealWordValidationRule extends ValidationRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see scramble.ValidationRule#validate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(String scramble, String word, Language language) {
		boolean isPreviousAllowed = false;
		for (int i = 0, j = 1; j < word.length(); i++, j++) {
			char c = scramble.charAt(i);
			char c2 = scramble.charAt(j);
			if (isAlternating(c, c2, language))
				continue;
			if (isAllowedCombination(c, c2, language)) {
				if (isPreviousAllowed)
					return false;
				else
					isPreviousAllowed = true;
			} else
				return false;
		}
		return true;
	}

	private boolean isVowel(char c, Language language) {
		return language.getVowels().contains(Character.toString(c));
	}

	private boolean isConsonant(char c, Language language) {
		return language.getConsonants().contains(Character.toString(c));
	}

	private boolean isAlternating(char c, char c2, Language language) {
		return (isVowel(c, language) && isConsonant(c2, language))
				|| (isConsonant(c, language) && isVowel(c2, language));
	}

	private boolean isAllowedCombination(char c, char c2, Language language) {
		return isListedCombination(c, c2, language) || (c == c2 && isConsonant(c, language));
	}

	private boolean isListedCombination(char c, char c2, Language language) {
		return language.getAllowedCombinations().contains(String.valueOf(c).concat(String.valueOf(c2)));
	}
}
