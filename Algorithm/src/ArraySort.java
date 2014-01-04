import java.io.IOException;

public class ArraySort {
	private int[] array;
	private int array_len;
	public static int count = 0;
	public static int count2 = 0;

	public ArraySort(String filename) throws IOException {
		super();
		array = new int[10000];
		array_len = ArrayFormation.readFileToArray(array, 10000, filename);
	}

	/*
	 * currently just choose first element in the partition as pivot
	 */
	static public int choosePivot(int[] array, int lowIndex, int highIndex) {
		// choose first one as Pivot, do nothing
		// choose last one as Pivot, swap the last one and first one;
		//int tmp = array[lowIndex];
		// // array[lowIndex]=array[highIndex];
		// // array[highIndex]=tmp;
		//
		// // choose the median of first, last and middle(left middle if the
		// array
		// // is even length)
		// int midIndex = (highIndex + lowIndex) / 2;
		// if (array[lowIndex] < array[midIndex]
		// && array[midIndex] < array[highIndex]
		// || array[highIndex] < array[midIndex]
		// && array[midIndex] < array[lowIndex]) {
		// // midIndex is median
		// array[lowIndex] = array[midIndex];
		// array[midIndex] = tmp;
		// } else if (array[midIndex] < array[highIndex]
		// && array[highIndex] < array[lowIndex]
		// || array[lowIndex] < array[highIndex]
		// && array[highIndex] < array[midIndex]) {
		// // highIndex is median
		// array[lowIndex] = array[highIndex];
		// array[highIndex] = tmp;
		// }// lowIndex is median, don't do anything
		//
		return array[lowIndex];
	}

	public static void quickSort(int[] array, int lowIndex, int highIndex) {
		// this.printArray();
		// System.out.println("low:high="+lowIndex+":"+highIndex);
		if (lowIndex >= highIndex)
			return;

		count2 += (highIndex - lowIndex);

		int i = lowIndex + 1;
		int pivot = choosePivot(array, lowIndex, highIndex);

		int tmp = 0;

		for (int j = lowIndex + 1; j <= highIndex; j++) {
			count++;
			// compare array[j] and pivot, see if needs swap
			if (array[j] < pivot) {
				// if there is nothing <p yet, then just move i forward;
				if (i != j) {
					// swap array[j] and array[i] unless there is nothing
					// smaller than pivot yet
					tmp = array[j];
					array[j] = array[i];
					array[i] = tmp;
				}
				i++;
			}
		}

		// swap pivot and array[i-1]
		tmp = array[i - 1];
		array[i - 1] = pivot;
		array[lowIndex] = tmp;

		// element i-1 is the pivot, doesn't need to sort.
		quickSort(array, lowIndex, i - 2);
		quickSort(array, i, highIndex);
	}

	public static void quickSort2(int[] array, int[] array2, int lowIndex,
			int highIndex) {
		// this.printArray();
		// System.out.println("low:high="+lowIndex+":"+highIndex);
		if (lowIndex >= highIndex)
			return;

		count2 += (highIndex - lowIndex);

		int i = lowIndex + 1;
		int pivot = choosePivot(array, lowIndex, highIndex);
		int pivot2 = choosePivot(array2, lowIndex, highIndex);

		int tmp = 0;
		int tmp2 = 0;

		for (int j = lowIndex + 1; j <= highIndex; j++) {
			count++;
			// compare array[j] and pivot, see if needs swap
			if (array[j] < pivot) {
				// if there is nothing <p yet, then just move i forward;
				if (i != j) {
					// swap array[j] and array[i] unless there is nothing
					// smaller than pivot yet
					tmp = array[j];
					array[j] = array[i];
					array[i] = tmp;

					tmp2 = array2[j];
					array2[j] = array2[i];
					array2[i] = tmp2;
				}
				i++;
			}
		}

		// swap pivot and array[i-1]
		tmp = array[i - 1];
		array[i - 1] = pivot;
		array[lowIndex] = tmp;

		// swap pivot and array[i-1]
		tmp2 = array2[i - 1];
		array2[i - 1] = pivot2;
		array2[lowIndex] = tmp2;

		// element i-1 is the pivot, doesn't need to sort.
		quickSort2(array, array2, lowIndex, i - 2);
		quickSort2(array, array2, i, highIndex);
	}

	public static void printArray(int array_len,int[] array) {
		for (int i = 0; i < array_len; i++) {
			if (i % 100 == 0)
				System.out.println();

			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ArraySort newObj = new ArraySort("QuickSort.txt");
		// newObj.printArray();
		System.out.println("array_len =" + newObj.array_len);
		// newObj.quickSort(newObj.array, 0, newObj.array_len - 1);
		System.out.println("--------sorted: ---------");
		//newObj.printArray();
		System.out.println("count of iteration: " + ArraySort.count);
		System.out.println("count2 of iteration: " + ArraySort.count2);
	}

}
