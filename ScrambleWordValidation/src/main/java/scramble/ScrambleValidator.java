/**
 * Validates scrambled words according to validation rules
 */
package scramble;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.apache.commons.configuration2.tree.ImmutableNode;

public class ScrambleValidator {

	private static final char LIST_DELIMITER = ',';
	private static final String COMBINATIONS = "combinations";
	private static final String VOWELS = "vowels";
	private static final String CONSONANTS = "consonants";
	private static final String NEGATION = "negation";
	private static final String NAME = "name";
	private static final String RESULT_NODE = "result";
	private static final String VALIDATION_CLASS = "scramble.Validation";
	private static final String RULES_NODE = "rules.rule";
	private static final String VALIDATION_NODE = "validations.validation";
	private static final String CONFIG_FILE = "src/main/config/configuration.xml";
	private static final String LANGUAGE_NODE = "languages.language";

	private static Map<String, LanguageValidator> languages;

	static {
		languages = new HashMap<>();
		XMLConfiguration config = new XMLConfiguration();
		config.setListDelimiterHandler(new DefaultListDelimiterHandler(LIST_DELIMITER));
		FileHandler handler = new FileHandler(config);
		try {
			handler.load(CONFIG_FILE);
			List<HierarchicalConfiguration<ImmutableNode>> languageNodes = config.configurationsAt(LANGUAGE_NODE);
			for (HierarchicalConfiguration<ImmutableNode> ln : languageNodes) {
				String lang = ln.getString(NAME);
				String vowels = ln.getString(VOWELS);
				String consonants = ln.getString(CONSONANTS);
				List<String> combinations = ln.getList(COMBINATIONS).stream().map(Object::toString)
						.collect(Collectors.toList());
				Language language = new Language(vowels, consonants, combinations);
				List<HierarchicalConfiguration<ImmutableNode>> validationNodes = ln.configurationsAt(VALIDATION_NODE);
				List<Validation> validations = new ArrayList<>(validationNodes.size());
				for (HierarchicalConfiguration<ImmutableNode> vn : validationNodes) {
					List<HierarchicalConfiguration<ImmutableNode>> ruleNodes = vn.configurationsAt(RULES_NODE);
					List<ValidationRuleWithNegation> rules = new ArrayList<>(ruleNodes.size());
					for (HierarchicalConfiguration<ImmutableNode> rn : ruleNodes) {
						ValidationRule r = (ValidationRule) Class.forName(rn.getString(NAME)).getConstructor()
								.newInstance();
						boolean isNegation = rn.containsKey(NEGATION) ? rn.getBoolean(NEGATION) : false;
						rules.add(new ValidationRuleWithNegation(isNegation, r));
					}
					Validation v = (Validation) Class.forName(VALIDATION_CLASS)
							.getConstructor(String.class, ArrayList.class)
							.newInstance(vn.getString(RESULT_NODE), rules);
					validations.add(v);
				}
				languages.put(lang, new LanguageValidator(validations, language));
			}
		} catch (ConfigurationException cex) {
			// TODO log
		} catch (InstantiationException e) {
			// TODO log
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO log
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO log
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO log
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO log
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO log
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	public static LanguageValidator get(String language) {
		return languages.get(language);
	}

}
