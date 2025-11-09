package strstr;

public class Strstr {

	public static void main(String[] args) {
//		System.out.println(strstr("abc", "ab"));
//		System.out.println(strstr("aabc", "ab"));
//		System.out.println(strstr("abc", "ac"));
//		System.out.println(strstr("abc", "abc"));
//		System.out.println(strstr("aca", "ab"));
		
		System.out.println(subseq("abc", "ab"));
		System.out.println(subseq("aabc", "ab"));
		System.out.println(subseq("abc", "ac"));
		System.out.println(subseq("abc", "abc"));
		System.out.println(subseq("aca", "ab"));
		System.out.println(subseq("aca", "aa"));
		System.out.println(subseq("abc", "ba"));
	}
	
	public static int strstr(String s, String s2) {
		if (s == null || s2 == null || s.length() == 0 || s2.length()==0)
			return -1;
		
		for (int i=0; i<s.length(); i++) {
			for (int j=0, k=i;j<s2.length() && k<s.length();j++, k++) {
				if (s.charAt(k) != s2.charAt(j)) break;
				if (j==s2.length()-1) return i;
			}
		}
		return -1;
	}
	
	public static boolean subseq(String s, String s2) {
		if (s == null || s2 == null || s.length() == 0 || s2.length()==0)
			return false;
		
		for (int i=0, j=0; i<s2.length() && j<s.length();) {
			if (s.charAt(j)==s2.charAt(i)) {
				i++;
				j++;
			} else {
				j++;
			}
			if (i==s2.length()) return true;
		}
		return false;
	}

}
