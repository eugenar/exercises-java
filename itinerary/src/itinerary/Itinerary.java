package itinerary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Itinerary {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		String[][] segments;
		try {
			System.out.print("Enter number of segments: ");
			int n = Integer.parseInt(in.nextLine().trim());
			if (n <= 0) {
				throw new IllegalArgumentException("Number of segments must be positive");
			}
			segments = new String[n][2];
			for (int i = 0; i < n; i++) {
				System.out.print(String.format("Enter segment %d (from to): ", i + 1));
				String line = in.nextLine().trim();
				if (line.isEmpty()) {
					throw new IllegalArgumentException("Empty segment input");
				}
				String[] parts = line.split("\\s+");
				if (parts.length != 2) {
					throw new IllegalArgumentException("Each segment must contain exactly two tokens");
				}
				segments[i][0] = parts[0];
				segments[i][1] = parts[1];
			}
		} catch (Exception e) {
			System.out.println("Invalid or no console input detected. Falling back to sample segments.");
			segments = new String[][] { { "d", "e" }, { "b", "c" }, { "a", "b" }, { "e", "f" }, { "c", "d" } };
		} finally {
			// do not close System.in scanner if you plan to reuse System.in elsewhere;
			// closing here is fine for small CLI programs that will exit.
			 in.close();
		}

		try {
			List<String[]> itinerary = trip(segments);
			for (String[] seg : itinerary) {
				System.out.println(String.format("(%s, %s)", seg[0], seg[1]));
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Error building itinerary: " + e.getMessage());
		}
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
