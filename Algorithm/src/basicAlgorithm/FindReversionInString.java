import java.io.IOException;

public class FindReversionInString {
	private long count;
	private int[] array;
	private int[] sortedArray;
	private int array_len;

	public FindReversionInString(String filename) throws IOException {
		super();
		array = new int[100000];
		sortedArray = new int[100000];
		array_len = ArrayFormation.readFileToArray(array,100000,filename);
		this.count = 0;
	}

	public long getCount() {
		return this.count;
	}

	public void sortAndCountOpti(int []array, int lowIndex, int highIndex){
		if(lowIndex==highIndex)
			return;
		
		int midIndex = (lowIndex+highIndex)/2;
		
		this.sortAndCountOpti(array, lowIndex, midIndex);
		this.sortAndCountOpti(array, midIndex+1, highIndex);
		
		int i = lowIndex;
		int j = midIndex+1;
		int n = i;
		while (i <=midIndex && j <=highIndex) {
			if (array[i] <= array[j]) {
				sortedArray[n] = array[i];
				n++;
				i++;
			} else {
				sortedArray[n] = array[j];
				n++;
				j++;
				this.count += midIndex - i + 1;
			}
		}

		// j reaches highIndex first
		for (; i<=midIndex; i++, n++) {
			sortedArray[n] = array[i];
		}

		for (; j <=highIndex; j++, n++) {
			sortedArray[n] = array[j];
		}

		//copy sortedArray back to array for section[lowIndex, highIndex]
		for(int k=lowIndex;k<=highIndex;k++){
			array[k]=sortedArray[k];
		}
	}
	
	public void sortAndCount(int[] array, int arrayLen) {
		if (arrayLen <= 1) {
			return;
		}

		int midIndex = (arrayLen - 1) / 2;

		/**
		 * left array: [0, (arrayLen-1)/2] right array: [arrayLen/2+1,
		 * arrayLen-1];
		 */
		int leftArrayLen = (arrayLen - 1) / 2 + 1;
		int rightArrayLen = arrayLen - leftArrayLen;
		
		int[] leftArray = new int[leftArrayLen];
		int[] rightArray = new int[rightArrayLen];
		
		for (int i = 0; i < leftArrayLen; i++) {
			leftArray[i] = array[i];
		}

		for (int i = 0; i < rightArrayLen; i++) {
			rightArray[i] = array[i + leftArrayLen];
		}

		this.sortAndCount(leftArray, leftArrayLen);
		this.sortAndCount(rightArray, rightArrayLen);

		int i = 0;
		int j = 0;
		int n = 0;
		while (i < leftArrayLen && j < rightArrayLen) {
			if (leftArray[i] <= rightArray[j]) {
				array[n] = leftArray[i];
				n++;
				i++;
			} else {
				array[n] = rightArray[j];
				n++;
				j++;
				this.count += midIndex - i + 1;
			}
		}

		// j reaches highIndex first
		for (; i < leftArrayLen; i++, n++) {
			array[n] = leftArray[i];
		}

		for (; j < rightArrayLen; j++, n++) {
			array[n] = rightArray[j];
		}

	}

	public void brutalForceCount(int lowIndex, int highIndex) {
		//System.out.println("Brutal force:");
		for (int i = lowIndex; i <= highIndex; i++) {
			for (int j = i + 1; j <= highIndex; j++) {
				if (this.array[j] < this.array[i]){
					//System.out.println(this.array[i]+":"+this.array[j]);
					count++;
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FindReversionInString newObj = new FindReversionInString(
				"IntegerArray.txt");
		FindReversionInString newObj3 = new FindReversionInString(
				"IntegerArray.txt");
		FindReversionInString newObj2 = new FindReversionInString(
				"IntegerArray.txt");
		
		long time1=System.currentTimeMillis();
		newObj.sortAndCount(newObj.array, newObj.array_len);
		long time2=System.currentTimeMillis();
		newObj2.brutalForceCount(0, 0 + newObj2.array_len - 1);
		long time3=System.currentTimeMillis();
		newObj3.sortAndCountOpti(newObj3.array, 0, newObj3.array_len-1);
		long time4=System.currentTimeMillis();
		
		System.out.println("array_len=" + newObj.array_len);
		System.out.println("Sort_count:" + newObj.getCount() +" time spent:"+(time2-time1));
		System.out.println("Brutal_count:" + newObj2.getCount()+" time spent:"+(time3-time2));
		System.out.println("Sort_count_opt:" + newObj3.getCount()+" time spent:"+(time4-time3));
	}
}
