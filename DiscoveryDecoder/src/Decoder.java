
public class Decoder {

	public static void main(String[] args) {
		System.out.println(decode("3[a]2[bc]dz")); // aaabcbcdz
		System.out.println(decode("3[b]2[a2[c]]")); // bbbaccacc
		System.out.println(decode("3[a2[c]]")); // accaccacc
		System.out.println(decode("a0[xyz]c")); // ac
		System.out.println(decode("abc")); // abc
	}

	public static String decode(String input) {
		if (input == null || !input.contains("["))
			return input;

		String s = null;
		outer: for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ']') { // first closed bracket
				for (int j = i - 1; j > 0; j--) {
					if (input.charAt(j) == '[') { // corresponding open bracket
						int n = 0;
						int k = 0;
						for (k = j - 1; k >= 0; k--) { // compute the number
							char c = input.charAt(k);
							if (Character.isDigit(c)) {
								n = 10 * n + Character.digit(c, 10);
							} else
								break;
						}
						StringBuilder sb = new StringBuilder();
						for (int l = 0; l < n; l++) { // expand string
							sb.append(input.substring(j + 1, i));
						}
						String b = k >= 0 ? input.substring(0, k + 1) : ""; // beginning substring
						String e = i < input.length() - 1 ? input.substring(i + 1) : ""; // ending substring
						s = decode(b + sb + e); // recurse
						break outer; // break out of nested loops
					}
				}
			}
		}
		return s;
	}

}

/**
 * 
 * Write a runlength decoder no special characters, except for digits,
 * alpha-numerals, [ and ]
 * 
 * String decode(String input)
 * 
 * 3[a]2[bc]dz --> 3aaa2[bc]dz --> aaabcbcdz
 * 
 * 3[a2[c]] --> 3[acc] --> accaccacc
 * 
 * 3[b]2[a2[c]] --> bbb2[a2[c]] --> bbb2[acc] --> bbbaccacc
 * 
 * abc --> abc
 * 
 * a0[xyz]c --> ac
 * 
 **/

// prashanth_kokati@discovery.com
