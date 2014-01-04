package interviewPrep;

public class testMain {
	public static void main(String[] args) {

		// For testing stringMutations
		//char[] charArray = args[0].toCharArray();
		// stringMutation obj = new stringMutation(charArray, charArray.length);
		// obj.doMutation();

		// print
		// for (String a : obj.getMutations()) {
		// System.out.println(a);
		//circularLinkedList list = new circularLinkedList(charArray);
		//System.out.println(list.findCircleStart());
		
		/* for HanoiTowers */
		/*int towerHeight = 5;
		hanoiTowers newTower = new hanoiTowers(towerHeight);
		System.out.println("Original Stack:");
		newTower.printStack(newTower.stack1);
		newTower.fromStackToStack(1, 5, 1, 3);
		
		System.out.println("New Stack:");
		newTower.printStack(newTower.stack3);*/
		
		/*int[] intArray = new int[]{1,2,3,4,5,6};
		stackOps newObj = new stackOps(intArray);
		newObj.printStack(newObj.forSort);
		System.out.println("After sorting: ");
		newObj.printStack(newObj.sortStack(newObj.forSort));*/
		
		//for binaryTree
		int[] intArray = new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
		binaryTree newTree = new binaryTree(intArray);
		System.out.println("======In-order=======");
		newTree.printTree(newTree.getRoot(),1);
		System.out.println("\n======Pre-order=======");
		newTree.printTree(newTree.getRoot(),2);
		System.out.println("\n======Post-order=======");
		newTree.printTree(newTree.getRoot(),3);
	}
}
