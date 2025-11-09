package scramble;

import java.util.List;

/**
 * Language specific elements.
 *
 */
public class Language {

	private String vowels;
	private String consonants;
	private List<String> allowedCombinations;
	/**
	 * @param vowels
	 * @param consonants
	 * @param allowedCombinations
	 */
	public Language(String vowels, String consonants, List<String> allowedCombinations) {
		this.vowels = vowels;
		this.consonants = consonants;
		this.allowedCombinations = allowedCombinations;
	}
	/**
	 * @return the vowels
	 */
	public String getVowels() {
		return vowels;
	}
	
	/**
	 * @return the consonants
	 */
	public String getConsonants() {
		return consonants;
	}

	/**
	 * @return the allowedCombinations
	 */
	public List<String> getAllowedCombinations() {
		return allowedCombinations;
	}
	
}
