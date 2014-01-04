import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class HashOperation {
	private int[] originalArray; // save original array from file, have
									// duplication
	private int arrayLen;
	private HashMap<Integer, LinkedList<Integer>> mappedArray; // de-duplicated
																// version of
																// originalArray

	/**
	 * @param args
	 * @throws IOException
	 */

	// would de-duplicate in the process
	public void InsertToHashMap() {
		int curKey;
		for (int i = 0; i < arrayLen; i++) {
			curKey = originalArray[i];
			// if originalArray[i] is duplicate, then the LinkedList for its
			// hash key should already exist;
			if (mappedArray.containsKey(curKey)) {
				LinkedList<Integer> existElements = mappedArray.get(curKey);
				// check if it's really duplicate or it's just in same bucket
				// after hash function
				if (!existElements.contains(curKey)) {// if it's not really
														// duplicate
					existElements.add(curKey);
				} else {// if it's duplicate, reset this value in originalArray
						// to be 0; so it would not be used later
					originalArray[i] = 0;
				}
			} else { // else, create new linkedlist, put current value in and
						// put in hashmap;
				LinkedList<Integer> elements = new LinkedList<Integer>();
				elements.add(curKey);
				mappedArray.put(curKey, elements);
			}
		}
	}

	public boolean IsExistInHashMap(int yValue) {
		if (mappedArray.containsKey(yValue)) {
			LinkedList<Integer> elements = mappedArray.get(yValue);
			return elements.contains(yValue);
		} else
			return false;
	}

	public int TwoSumProblem() {
		int x, y = 0;
		int count = 0;
		LinkedList<Integer> sums = new LinkedList<Integer>();
		// sum between [2500,4000]
		for (int sum = 2500; sum <= 4000; sum++) {
			for (int i = 0; i < arrayLen; i++) {
				x = originalArray[i];
				y = sum - x;
				if (x == 0 || x == y)
					continue;
				if (IsExistInHashMap(y)) {
					sums.add(sum);
					count++;
					//System.out.println(x + "," + y + "=" + sum);
					break;
				}
			}
		}

		// Iterator<Integer> i = sums.iterator();
		// while (i.hasNext()) {
		// System.out.print(i.next() + " ");
		// }
		// System.out.println();
		return count;
	}

	public HashOperation(int arrayLen, String filename) throws IOException {
		this.arrayLen = arrayLen;
		originalArray = new int[arrayLen];
		this.arrayLen = ArrayFormation.readFileToArray(originalArray, arrayLen,
				filename);
		mappedArray = new HashMap<Integer, LinkedList<Integer>>(arrayLen);
		InsertToHashMap();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HashOperation obj = new HashOperation(500000, "HashInt.txt");
		System.out.println(obj.TwoSumProblem());
	}

}
