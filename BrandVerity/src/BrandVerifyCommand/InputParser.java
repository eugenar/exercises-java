/**
 * 
 */
package BrandVerifyCommand;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Parse the input file and load in memory maps with terms and frequencies
 * @author Eugen Ardeleanu
 */
class InputParser {

	/**
	 * Input file path
	 */
	String filePath;
	
	/**
	 * Map of terms and their file occurrence indexes sorted by key reverse, e.g., {"you"=[30, 73, 80, 83, 125, 129, 152, 220]}
	 * with indexes sorted increasing; used for FREQUENCY command
	 */
	Map<String, List<Integer>> term2index = new HashMap<>();
	
	/**
	 * Map of frequencies and terms, e.g., {8=["for", "points", "you"]} sorted by frequency decreasing;
	 * used for TOP and IN_ORDER commands
	 */
	Map<Integer, SortedSet<String>> frequency2term = new TreeMap<>(Collections.reverseOrder());

	/**
	 * @param filePath
	 */
	public InputParser(String filePath) {
		this.filePath = filePath;
		loadTermMap();
		loadFrequencyMap();
	}

	private void loadFrequencyMap() {
		for (Map.Entry<String, List<Integer>> entry : term2index.entrySet()) {
			String term = entry.getKey();
			int frequency = entry.getValue().size();
			if (frequency2term.containsKey(frequency))
				frequency2term.get(frequency).add(term);
			else {
				SortedSet<String> set = new TreeSet<>();
				set.add(term);
				frequency2term.put(frequency, set);
			}
		}
	}

	private void loadTermMap() {
		try (Scanner scan = new Scanner(new FileInputStream(filePath))) {
			int index = 0;
			while (scan.hasNext()) {
			    String s = scan.next();
			    if (term2index.containsKey(s)) {
			    	term2index.get(s).add(index);
			    } else {
			    	term2index.put(s, new ArrayList<Integer>(Arrays.asList(index)));
			    }
			    
			    index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1); // terminal error
		}
	}
}
