package interviewPrep;

public class binaryTree {
	treeNode root;
	int nodeCount;

	public binaryTree() {
		root = null;
		nodeCount = 0;
	}

	public binaryTree(int rootValue) {
		root = new treeNode(rootValue);
		nodeCount = 1;
	}

	public treeNode getRoot() {
		return root;
	}

	//get right sibling from the same parent
	public treeNode getDirectRightSibling(treeNode curNode) {
		if(curNode.parent!=null && curNode.parent.leftLeaf == curNode)
			return curNode.parent.rightLeaf;
			
		return null;
	}
	
	//get left sibling from the same parent
	public treeNode getDirectLeftSibling(treeNode curNode) {
		if(curNode.parent!=null && curNode.parent.rightLeaf == curNode)
			return curNode.parent.leftLeaf;
			
		return null;
	}
	
	//get next Sibling on the same level, no matter direct or indirect
	//!!!!!!!!!!!INCOMPLETE!!!!!!!!!!!!!!!!!!!!!
	public treeNode getNextSibling(treeNode curNode) {
		treeNode directRightSibling = getDirectRightSibling(curNode);
		if(directRightSibling != null)
			return directRightSibling;
		
		return null;
	}
	
	//!!!!!!!!!!!INCOMPLETE!!!!!!!!!!!!!!!!!!!!!
	public treeNode getMostLeftSibling(treeNode curNode) {
		return null;
	}
	
	public binaryTree(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			// add into tree
			if (i == 0) {// first node
				root = new treeNode(intArray[i]);
			} else {
				treeNode newNode = new treeNode(intArray[i]);
				addNodeBalanced(root, newNode);
			}
			nodeCount++;
		}
	}

	// returns true if successful, return false otherwise
	//!!!!!!!!!!!INCOMPLETE!!!!!!!!!!!!!!!!!!!!!
	public boolean addNodeBalanced(treeNode root, treeNode leaf) {
		boolean result = false;
		
		if(root == null) {
			return false;
		}
		
		//if cur node has empty child
		if (root.leftLeaf == null) {
			root.leftLeaf = leaf;
			leaf.parent = root;
			return true;
		}

		if (root.rightLeaf == null) {
			root.rightLeaf = leaf;
			leaf.parent = root;
			return true;
		}

		//!!!!!!!!!!!INCORRECT!!!!!!!!!!!!!!!!!!!!!

		/*if(root.parent!=null) {//if it's not tree root
			if(root == root.parent.leftLeaf){ //left child
				treeNode rightSibling = this.getRightSibling(root);
				return addNodeBalanced(rightSibling,leaf);
			} else { //right child, reaching here meaning the right child has full leaves too
				treeNode leftSibling = this.getLeftSibling(root);
				return addNodeBalanced(leftSibling.leftLeaf, leaf);
			}
		} else { // if it's root
			result = addNodeBalanced(root.leftLeaf, leaf);
			
			if(result)
				return result;
			return addNodeBalanced(root.rightLeaf, leaf);
		}*/
	}

	/*
	 * orderIndex = 1 : In-order orderIndex = 2 : Pre-order orderIndex = 3 :
	 * Post-order
	 */
	public void printTree(treeNode rootNode, int orderIndex) {
		if (rootNode != null) {
			//System.out.println("Printing tree with root:" + rootNode.value);
			switch (orderIndex) {
			case 1: { // In-order
				printTree(rootNode.leftLeaf, orderIndex);
				System.out.print(' ');
				System.out.print(rootNode.value);
				System.out.print(' ');
				printTree(rootNode.rightLeaf, orderIndex);
				break;
			}
			case 2: { // Pre-order
				System.out.print(' ');
				System.out.print(rootNode.value);
				System.out.print(' ');
				printTree(rootNode.leftLeaf, orderIndex);
				printTree(rootNode.rightLeaf, orderIndex);
				break;
			}
			case 3: { // Post-order
				printTree(rootNode.leftLeaf, orderIndex);
				printTree(rootNode.rightLeaf, orderIndex);
				System.out.print(' ');
				System.out.print(rootNode.value);
				System.out.print(' ');
				break;
			}

			default:
				System.out.println("orderIndex " + orderIndex
						+ " not recognized!\n");
				break;
			}
		}
	}
}
