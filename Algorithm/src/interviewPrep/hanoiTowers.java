package interviewPrep;

import java.util.Stack;

public class hanoiTowers {
	Stack<Integer> stack1; // original stack
	Stack<Integer> stack2;
	Stack<Integer> stack3; // destination stack

	// supposing there are 6 plates, then we push 6, 5, 4, 3, 2, 1 onto stack1
	// by sequence
	public hanoiTowers(int count) {
		stack1 = new Stack<Integer>();
		stack2 = new Stack<Integer>();
		stack3 = new Stack<Integer>();

		for (int i = count; i > 0; i--) {
			stack1.push(new Integer(i));
		}
	}

	// manually put stack 1 , 2 , 3 in a circle for sequence purpose
	public int getThirdStackNo(int stackNo1, int stackNo2) {
		if ((stackNo1 == 1 && stackNo2 == 2)
				|| (stackNo1 == 2 && stackNo2 == 1))
			return 3;
		if ((stackNo1 == 1 && stackNo2 == 3)
				|| (stackNo1 == 3 && stackNo2 == 1))
			return 2;
		if ((stackNo1 == 2 && stackNo2 == 3)
				|| (stackNo1 == 3 && stackNo2 == 2))
			return 1;

		return 0;
	}

	public Stack<Integer> getStack(int stackNo) {
		switch (stackNo) {
		case 1:
			return stack1;
		case 2:
			return stack2;
		case 3:
			return stack3;
		default:
			return null;
		}
	}

	public void printStack(Stack<Integer> stack) {
		Stack<Integer> tmpStack = new Stack<Integer>();
		
		//pop all and print
		while (!stack.empty()) {
			int cur = stack.pop();
			System.out.println(cur);
			tmpStack.push(cur);
		}
		
		//put it back
		while(!tmpStack.empty()){
			stack.push(tmpStack.pop()); 
		}
	}

	// fromStackToStack(1,5,1,2) meaning: move plates 1-5 from stack 1 to stack
	// 2
	public void fromStackToStack(int beginN, int endN, int srcStackNo,
			int dstStackNo) {
		int midStackNo = getThirdStackNo(srcStackNo, dstStackNo);
		
		//get all stacks for pop/push operations later
		Stack<Integer> srcStack = getStack(srcStackNo);
		Stack<Integer> dstStack = getStack(dstStackNo);
		
		if(beginN == endN){ //move 1 plate
			int plate = srcStack.pop();
			dstStack.push(plate);
			System.out.println("plate "+beginN+": "+srcStackNo+"->"+dstStackNo);
			return;
		}
		
		fromStackToStack(beginN, endN - 1, srcStackNo, midStackNo);
		fromStackToStack(endN, endN, srcStackNo, dstStackNo);
		fromStackToStack(beginN, endN - 1, midStackNo, dstStackNo);
	}
}
