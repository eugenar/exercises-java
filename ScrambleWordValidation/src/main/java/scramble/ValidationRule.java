/**
 * Base class for all validation rules
 */
package scramble;

public abstract class ValidationRule {

	public abstract boolean validate(String scramble, String word, Language language);

}
