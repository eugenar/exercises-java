package scramble;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator for a specific language
 *
 */
public class LanguageValidator {

	private List<Validation> validations;
	private Language language;

	/**
	 * @param validations
	 * @param language
	 */
	public LanguageValidator(List<Validation> validations, Language language) {
		this.validations = validations;
		this.language = language;
	}

	/**
	 * Validate all validations in the order they appear in the configuration
	 * file and return the first result matching.
	 * 
	 * @param scramble
	 * @param word
	 * @return validation result
	 */
	public String validate(String scramble, String word) {
		for (Validation v : validations) {
			String result = v.validate(scramble.toUpperCase(), word.toUpperCase(), language);
			if (result != null)
				return result;
		}
		return null;
	}

	/**
	 * Validate a list of (scramble word) pairs.
	 * 
	 * @param scrambleWordPairs
	 * @return validation result
	 */
	public List<String> validate(List<String> scrambleWordPairs) {
		if (scrambleWordPairs == null)
			return null;
		List<String> result = new ArrayList<>(scrambleWordPairs.size());
		for (String s : scrambleWordPairs) {
			String[] sa = s.trim().split(" ");
			if (sa.length > 1) {
				String scramble = sa[0];
				String word = sa[sa.length - 1];
				String res = validate(scramble, word);
				result.add(String.format("%s is %s scramble of %s", scramble, res, word));
			}
		}
		return result;
	}

}
