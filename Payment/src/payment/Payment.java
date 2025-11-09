package payment;

import java.util.HashMap;
import java.util.Map;

public class Payment {

	public static void main(String[] args) {
		Map<String, Integer> limits = new HashMap<String, Integer>();
		limits.put("a", 10);
		limits.put("b", 10);
		limits.put("c", 10);
		limits.put("d", 10);
		int amount = 40;

		Map<String, Integer> result = distribute(amount, limits);
		print(result);

		limits = new HashMap<String, Integer>();
		limits.put("a", 10);
		limits.put("b", 10);
		limits.put("c", 10);
		limits.put("d", 10);
		amount = 42;

		result = distribute(amount, limits);
		print(result);

		limits = new HashMap<String, Integer>();
		limits.put("a", 10);
		limits.put("b", 10);
		limits.put("c", 10);
		limits.put("d", 10);
		amount = 46;

		result = distribute(amount, limits);
		print(result);

		limits = new HashMap<String, Integer>();
		limits.put("a", 10);
		limits.put("b", 10);
		limits.put("c", 10);
		limits.put("d", 10);
		amount = 32;

		result = distribute(amount, limits);
		print(result);

		limits = new HashMap<String, Integer>();
		limits.put("a", 6);
		limits.put("b", 12);
		limits.put("c", 10);
		limits.put("d", 10);
		amount = 32;

		result = distribute(amount, limits);
		print(result);
	}

	static Map<String, Integer> distribute(int amount, Map<String, Integer> limits) {

		// initialize
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (var e : limits.entrySet()) {
			result.put(e.getKey(), 0);
		}

		int maxDist = amount / limits.size();
		int minDist = minDistribute(limits, result);
		int min = Math.min(minDist, maxDist);
		boolean modified = true;
		while (min > 0 && modified) {
			modified = false;
			for (var e : result.entrySet()) {
				if (e.getValue() < limits.get(e.getKey())) {
					e.setValue(e.getValue() + min);
					amount -= min;
					modified = true;
				}
			}
			maxDist = amount / limits.size();
			minDist = minDistribute(limits, result);
			min = Math.min(minDist, maxDist);
		}

		if (minDist > 0) {
			// distribute remainder
			int rem = amount % limits.size();
			if (rem > 0) {
				for (var e : result.entrySet()) {
					if (e.getValue() < limits.get(e.getKey())) {
						e.setValue(e.getValue() + 1);
						if (--rem == 0)
							break;
					}
				}
			}
		}
		return result;
	}

	static int minDistribute(Map<String, Integer> limits, Map<String, Integer> result) {
		int min = Integer.MAX_VALUE;

		for (var e : limits.entrySet()) {
			int diff = e.getValue() - result.get(e.getKey());
			if (diff > 0 && diff <= min) {
				min = diff;
			}
		}
		return min;
	}

	static void print(Map<String, Integer> result) {
		for (var e : result.entrySet()) {
			System.out.print(String.format("%s:%d ", e.getKey(), e.getValue()));
		}
		System.out.println();
	}

}
