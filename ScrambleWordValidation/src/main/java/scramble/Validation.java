package scramble;

import java.util.ArrayList;
import java.util.List;

class Validation {

	private String result;
	private List<ValidationRuleWithNegation> validationRules;

	/**
	 * Constructor for validation. Is used only in initialization.
	 * 
	 * @param result
	 * @param validationRules
	 */
	public Validation(String result, ArrayList<ValidationRuleWithNegation> validationRules) {
		this.result = result;
		this.validationRules = validationRules;
	}

	/**
	 * Validate all the validation rules; return result if all validate,
	 * otherwise return null
	 * 
	 * @param scramble
	 * @param word
	 * @param language 
	 * @return
	 */
	public String validate(String scramble, String word, Language language) {
		for (ValidationRuleWithNegation rule : validationRules) {
			if ((!rule.isNegation && !rule.rule.validate(scramble, word, language))
					|| rule.isNegation && rule.rule.validate(scramble, word, language))
				return null;
		}
		return result;
	}

}
