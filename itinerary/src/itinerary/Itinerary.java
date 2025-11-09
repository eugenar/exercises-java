package itinerary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Itinerary {

	public static void main(String[] args) {
		String[][] segments = new String[][] { { "d", "e" }, { "b", "c" }, { "a", "b" }, { "e", "f" }, { "c", "d" } };
		List<String[]> itinerary = trip(segments);
		for (String[] seg : itinerary)
			System.out.println(String.format("(%s, %s)", seg[0], seg[1]));
	}

	static List<String[]> trip(String[][] segments) {
		Map<String, String> sd = new HashMap<>();
		Map<String, String> ds = new HashMap<>();

		for (String[] seg : segments) {
			if (sd.containsKey(seg[0]))
				throw new IllegalArgumentException();
			sd.put(seg[0], seg[1]);

			if (ds.containsKey(seg[1]))
				throw new IllegalArgumentException();
			ds.put(seg[1], seg[0]);
		}

		String[] seg = segments[0];
		List<String[]> itinerary = new ArrayList<String[]>();
		do {
			itinerary.add(0, seg);
			String s = ds.get(seg[0]);
			seg = new String[] { s, seg[0] };
		} while (ds.containsKey(seg[0]));
		itinerary.add(0, seg);

		seg = segments[0];
		while (sd.containsKey(seg[1])) {
			String d = sd.get(seg[1]);
			seg = new String[] { seg[1], d };
			itinerary.add(seg);
		}
		return itinerary;
	}

}
