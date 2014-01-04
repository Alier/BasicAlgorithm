package interviewPrep;

import java.util.Stack;

public class stackOps {
	Stack<Integer> forSort;

	public stackOps(int[] intArray) {
		forSort = new Stack<Integer>();
		for (int i = 0; i < intArray.length; i++) {
			forSort.push(new Integer(intArray[i]));
		}
	}

	// in ascending order
	public Stack<Integer> sortStack(Stack<Integer> origStack) {
		Stack<Integer> resultStack = new Stack<Integer>();
		int popCountOrig = 0;
		int pushCountOrig = 0;
		int n = origStack.size();

		while (!origStack.empty()) {
			int tmp = origStack.pop();
			popCountOrig++;

			while (!resultStack.empty() && resultStack.peek() > tmp) {
				origStack.push(resultStack.pop());
				pushCountOrig++;
			}
			resultStack.push(new Integer(tmp));
		}

		// System.out.println("OrigStack: pop "+popCountOrig+"/push "+pushCountOrig);
		// System.out.println("NewStack: pop "+popCountNew+"/push "+pushCountNew);
		System.out.println("Complexity from " + n + " to " + n * n);
		System.out.println("total count " + (popCountOrig + pushCountOrig));

		return resultStack;
	}

	public void printStack(Stack<Integer> stack) {
		Stack<Integer> tmpStack = new Stack<Integer>();

		// pop all and print
		while (!stack.empty()) {
			int cur = stack.pop();
			System.out.println(cur);
			tmpStack.push(cur);
		}

		// put it back
		while (!tmpStack.empty()) {
			stack.push(tmpStack.pop());
		}
	}
}
