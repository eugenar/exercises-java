package spellchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Spell corrector based on generating all variants of a given word based on edit rules
 * up to a limit of (edit distance - Levenshtein distance). The limit is necessary to avoid 
 * running out of memory.
 * 
 * @author Eugen Ardeleanu
 *
 */
public class SpellChecker {

	static List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');
	static Set<String> dictionary = new HashSet<String>();
	static int maxVowels, maxWordLength;
	static int maxIterations = 4; // TODO configurable

	// read dictionary of words from a text file one word per line
	static {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					((SpellChecker.class.getClassLoader().getResourceAsStream("resources/wordsEn.txt")))));
			String word = reader.readLine();
			while (word != null) {
				if (word.length() > maxWordLength)
					maxWordLength = word.length();
				int count = countVowels(word);
				if (count > maxVowels)
					maxVowels = count;
				dictionary.add(word.toLowerCase());
				word = reader.readLine();
			}
		} catch (IOException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	public static Set<String> check(String word) {
		if (word == null || word.isEmpty())
			return null;

		if (checkCharCasing(word) && dictionary.contains(word.toLowerCase()))
			return null;

		Set<String> corrections = new HashSet<String>();
		Set<String> variants = applyEditRules(word.toLowerCase());
		variants.forEach(v -> {
			if (dictionary.contains(v))
				corrections.add(v);
		});
		return corrections;
	}

	private static boolean checkCharCasing(String word) {
		String lword = word.toLowerCase();
		return word.equals(lword) || word.equals(word.toUpperCase()) || word.length() == 1
				|| lword.equals((word.substring(0, 1).toLowerCase() + word.substring(1)));
	}

	/**
	 * Apply all edit rules for the max iterations or until no new variants are
	 * generated.
	 * 
	 * @param word
	 * @return all variant words obtained by applying the rules
	 */
	private static Set<String> applyEditRules(String word) {
		Set<String> variants = new HashSet<String>();
		variants.add(word);
		Set<String> newVariants = new HashSet<String>();
		for (int i = 0; i < maxIterations; i++) {
			insertVowel(variants, newVariants);
			deleteRepeatedChar(variants, newVariants);
			if (!variants.addAll(newVariants))
				break;
			newVariants.clear();
		}
		return variants;
	}

	/**
	 * Insert one vowel at all positions in a set of words.
	 * 
	 * @param word
	 */
	private static void insertVowel(Set<String> words, Set<String> variants) {
		words.forEach(word -> {
			insertVowel(word, variants);
		});
	}

	/**
	 * Delete one repeated character in a set of words.
	 * 
	 * @param word
	 * @return all variant words generated
	 */
	private static void deleteRepeatedChar(Set<String> words, Set<String> variants) {
		words.forEach(word -> {
			deleteRepeatedChar(word, variants);
		});
	}

	/**
	 * Insert one vowel at all positions in a word until reaching the max length of
	 * words or max number of vowels in the dictionary.
	 * 
	 * @param word
	 */
	private static void insertVowel(String word, Set<String> variants) {
		if (word.length() >= maxWordLength || countVowels(word) >= maxVowels)
			return;
		for (int i = 0; i <= word.length(); i++) {
			for (char c : vowels) {
				variants.add(word.substring(0, i) + c + word.substring(i));
			}
		}
	}

	/**
	 * Delete one repeated character in a word.
	 * 
	 * @param word
	 */
	private static void deleteRepeatedChar(String word, Set<String> variants) {
		for (int i = 1; i < word.length(); i++) {
			if (word.charAt(i) == word.charAt(i - 1))
				variants.add(word.substring(0, i) + word.substring(i + 1));
		}
	}

	private static int countVowels(String word) {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (vowels.contains(word.charAt(i)))
				count++;
		}
		return count;
	}
}
