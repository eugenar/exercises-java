package intervalOverlap;

import java.util.ArrayList;
import java.util.Arrays;

public class MaxInterval {

	public static void main(String[] args) {
		ArrayList<Integer> s = new ArrayList<Integer>(Arrays.asList(1, 3, 0, 5, 8, 5));
		ArrayList<Integer> f = new ArrayList<Integer>(Arrays.asList(2, 4, 6, 7, 9, 9));
		ArrayList<Integer> res = maxIntervals(s, f);
		System.out.println(res);
		
		s = new ArrayList<Integer>(Arrays.asList(75250, 50074, 43659, 8931, 11273, 27545, 50879, 77924));
		f = new ArrayList<Integer>(Arrays.asList(112960, 114515, 81825, 93424, 54316, 35533, 73383, 160252));
		res = maxIntervals(s, f);
		System.out.println(res);
	}

	static ArrayList<Integer> maxIntervals(ArrayList<Integer> s, ArrayList<Integer> f) {

		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<Integer> cur = new ArrayList<Integer>();

		maxIntervals(s, f, res, cur, 0);

		return res;
	}

	static void maxIntervals(ArrayList<Integer> s, ArrayList<Integer> f, ArrayList<Integer> res, ArrayList<Integer> cur,
			int index) {
		if (s.size() == index)
			return;
		if (isFit(s, f, cur, index)) {
			cur.add(index);
			if (cur.size() > res.size()) {
				res.clear();
				res.addAll(cur);
			}
			maxIntervals(s, f, res, cur, index + 1);
			cur.remove(cur.size() - 1);
		}
		maxIntervals(s, f, res, cur, index + 1);
	}

	static boolean isFit(ArrayList<Integer> s, ArrayList<Integer> f, ArrayList<Integer> cur, int index) {
		for (int i : cur) {
			if (isOverlap(s.get(i), f.get(i), s.get(index), f.get(index)))
				return false;
		}
		return true;
	}

	static boolean isOverlap(int s, int f, int s2, int f2) {
		return !(s2 >= f || f2 <= s);
	}
}
