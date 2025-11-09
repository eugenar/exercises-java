package stringChain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chain {

	public static void main(String[] args) {
		String[][] names = new String[][] { { "d", "e" }, { "b", "c" }, { "a", "b" }, { "e", "f" }, { "c", "d" } };
		int l = maxChain(names);
		System.out.println(String.format("%d", l));

		names = new String[][] { { "d", "e" }, { "c", "a" }, { "e", "f" }, { "c", "d" } };
		l = maxChain(names);
		System.out.println(String.format("%d", l));

		names = new String[][] { { "d", "e" }, { "c", "a" }, { "a", "b" }, { "c", "d" } };
		l = maxChain(names);
		System.out.println(String.format("%d", l));

		names = new String[][] { { "c", "a" }, { "a", "b" }, { "c", "d" } };
		l = maxChain(names);
		System.out.println(String.format("%d", l));

		names = new String[][] { { "c", "a" }, { "c", "d" } };
		l = maxChain(names);
		System.out.println(String.format("%d", l));
	}

	static Map<String, List<String>> arrayToMap(String[][] names) {
		if (names == null)
			throw new IllegalArgumentException();
		Map<String, List<String>> map = new HashMap<>();
		for (String[] name : names) {
			if (map.containsKey(name[0]))
				map.get(name[0]).add(name[1]);
			else
				map.put(name[0], new ArrayList<>(List.of(name[1])));
		}
		return map;
	}

	static int maxChain(String[][] names) {
		Map<String, List<String>> map = arrayToMap(names);
		int l, max = 0;
		for (String name : map.keySet()) {
			l = maxChain(name, map);
			if (l > max)
				max = l;
		}
		return max;
	}

	static int maxChain(String name, Map<String, List<String>> map) {
		if (!map.containsKey(name))
			return 0;
		int l, max = 0;
		for (String n : map.get(name)) {
			l = 1 + maxChain(n, map);
			if (l > max)
				max = l;
		}
		return max;
	}

}
