import java.io.IOException;
import java.util.PriorityQueue;

public class HeapOperation {
	private int[] originalArray;
	private int arrayLen;
	private int[] medianArray;

	private PriorityQueue<Integer> lowHalf; // root should be the biggest of
											// lower half;
	private PriorityQueue<Integer> highHalf; // root should be the smallest of
												// higher half;

	public HeapOperation(int arrayLen, String filename) throws IOException {
		this.arrayLen = arrayLen;
		originalArray = new int[arrayLen];
		medianArray = new int[arrayLen];
		this.arrayLen = ArrayFormation.readFileToArray(originalArray, arrayLen,
				filename);

		lowHalf = new PriorityQueue<Integer>(this.arrayLen / 2 + 1,
				new reverseComparator());
		highHalf = new PriorityQueue<Integer>(this.arrayLen / 2 + 1);
	}

	/*
	 * insert every element from array to these two heaps, keep them
	 * balanced,size difference<2;
	 * 
	 * for each insertion, calculate the current median, and sum up m1->mN (N is
	 * the total number of elements inserted)
	 */
	public void MedianMaintanance() {
		int maxLowHalf = 0; // then this heap is null
		int minHighHalf = 10001; // when this heap is null
		int curElement = 0;

		for (int i = 0; i < this.arrayLen; i++) {
			// insert i;
			curElement = originalArray[i];
			if (lowHalf.peek() != null)
				maxLowHalf = lowHalf.peek();
			if (highHalf.peek() != null)
				minHighHalf = highHalf.peek();

			if (curElement < maxLowHalf) {// insert to lower half
				lowHalf.add(curElement);
			} else if (curElement > minHighHalf) {
				highHalf.add(curElement);
			} else {// maxLowHalf<= curElement <=minHighHalf, put in highHalf,
					// will re-balance later
				highHalf.add(curElement);
			}

			// re-balance the two heaps if needed;
			if (highHalf.size() - lowHalf.size() > 1) {
				lowHalf.add(highHalf.poll());
			}else if(lowHalf.size()-highHalf.size() > 0){ //always make lowHalf the smaller set
				highHalf.add(lowHalf.poll());
			}

			// get the current median;
			//because the two heaps are balanced, so their size difference should <1;
			if (lowHalf.size() < highHalf.size()) {
				medianArray[i] = highHalf.peek();
			} else {
				medianArray[i] = lowHalf.peek();
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HeapOperation obj = new HeapOperation(10000, "Median.txt");
		obj.MedianMaintanance();

		int sum = 0;
		for (int i = 0; i < 10000; i++) {
			System.out.println((i + 1) + ":" + obj.medianArray[i]);
			sum += obj.medianArray[i];
		}

		System.out.println("sum=" + sum);
		System.out.println("sum%10000 =" + sum % 10000);
	}

}
