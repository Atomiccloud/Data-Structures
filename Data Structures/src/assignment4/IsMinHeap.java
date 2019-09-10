package assignment4;

public class IsMinHeap {
	public static int[] arr = {1, 2, 3, 4, 5 };

	public static void main(String[] args) {
		System.out.println(isMinHeap(0));

	}

	public static boolean isMinHeap(int k) {
		if (k > (arr.length-2)/2) {
			return true;
		}
		if (arr[k] <= arr[k * 2 + 1] && isMinHeap(k * 2 + 1)) {
			if (2*k+1 == arr.length || arr[k] <= arr[k * 2 + 2] && isMinHeap(k * 2 + 2)) {
				return true;
			}
		}
		return false;
	}
}
