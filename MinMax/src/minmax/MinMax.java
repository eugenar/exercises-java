package minmax;

public class MinMax {

	public static void main(String[] args) {
		int[] a = new int[] { 2, 8, 4, 1, 3, 8 };
		int[] res = minMaxArray(a);
		if (res != null)
			System.out.println(String.format("%d - %d", res[0], res[1]));

		a = new int[] { 2, 8, 4, 1, 3, 8, 1, 3, 8, 9, 2 };
		res = minMaxArray(a);
		if (res != null)
			System.out.println(String.format("%d - %d", res[0], res[1]));
	}

	static int[] minMaxArray(int[] a) {
		if (a == null || a.length == 1)
			return null;

		int min = 0, max = 0, minCur = 0, maxCur = 0, dif = 0;

		while (minCur < a.length - 1) {
			for (int l = minCur; l < a.length - 1; l++) {
				if (a[l] < a[l + 1]) {
					minCur = l;
					break;
				}
			}

			for (int r = minCur + 1; r < a.length; r++) {
				if (r == a.length - 1 || a[r] > a[r + 1]) {
					maxCur = r;
					break;
				}
			}

			int difCur = a[maxCur] - a[minCur];
			if (difCur > dif) {
				dif = difCur;
				min = minCur;
				max = maxCur;
			}
			minCur = maxCur;
		}
		return new int[] { min, max };
	}

	static int[] maxAreaArray(int[] a) {
		if (a == null || a.length == 1)
			return null;

		int l = 0, r = 0, areaMax = 0;

		while (l < a.length) {
			for (int i = l + 1; i < a.length; i++) {
				if (a[i] >= a[l]) {
					int area = a[l] * (i - l);
					if (area > areaMax) {
						areaMax = area;
						l = i;
					}

				}
			}
		}
		return new int[] { l, r };
	}

}
