package interviewPrep;

public class binaryTree {
	public treeNode root;
	public int nodeCount;
	public treeNode lastInsertNode; // pointing to last node inserted into
									// balanced tree

	public binaryTree() {
		root = null;
		nodeCount = 0;
	}

	public binaryTree(int rootValue) {
		root = new treeNode(rootValue);
		lastInsertNode = root;
		nodeCount = 1;
	}

	public treeNode getRoot() {
		return root;
	}
	
	public treeNode getLeftSibling(treeNode curNode) {
		if(curNode.parent != null && curNode.parent.rightLeaf == curNode) {
			return curNode.parent.leftLeaf;
		}
		
		return null;
	}
	
	//get the left most leaf on the lowest level
	public treeNode getLeftMostNode(treeNode curRoot) 
	{
		if(curRoot.leftLeaf == null)
			return curRoot;
		
		return this.getLeftMostNode(curRoot.leftLeaf);
	}
	
	//get the next node on the same level on the right
	public treeNode getNextRightSibling(treeNode curNode) {
		treeNode parent = curNode.parent;
		
		if(parent == null) //curNode is root , no right sibling
			return null;
		
		if(parent.leftLeaf == curNode)
			return parent.rightLeaf;
		else {
			treeNode parentRightSibling = getNextRightSibling(parent);
			if(parentRightSibling != null && parentRightSibling.leftLeaf != null) {
				return parentRightSibling.leftLeaf;
			}
		}
			 
		return null;
	}
	
	//return true if cur Node is the right most node on its level, else return false
	public boolean isRightMost(treeNode curNode) {
		treeNode parent = curNode.parent;
		
		if(parent == null) //root
			return true;
		
		if(parent.rightLeaf == curNode) //is parent's right leaf, see if parent's is right most
			return isRightMost(parent);
		
		return false;
	}
	
	public boolean isLeftMost(treeNode curNode) {
		treeNode parent = curNode.parent;
		
		if(parent == null) //root
			return true;
		
		if(parent.leftLeaf == curNode) //is parent's right leaf, see if parent's is right most
			return isLeftMost(parent);
		
		return false;
	}
	
	public binaryTree(int[] intArray) {
		for (int i = 0; i < intArray.length; i++) {
			// add into tree
			if (i == 0) {// first node
				root = new treeNode(intArray[i]);
				lastInsertNode = root;
				nodeCount = 1;
			} else {
				addNodeBalanced(intArray[i]);
			}
			nodeCount++;
		}
	}

	// returns true if successful, return false otherwise
	public void addNodeBalanced(int newLeafValue) {
		treeNode newNode = new treeNode(newLeafValue);
		
		// root node is last insert, add to left child
		if(this.lastInsertNode == this.root) {
			this.lastInsertNode.leftLeaf = newNode;
			newNode.parent = this.lastInsertNode;
			this.lastInsertNode = newNode;
			return;
		} 
		
		treeNode lastParent = this.lastInsertNode.parent;
		//if last insert node is left child, right child must be empty 
		if(lastParent.leftLeaf == this.lastInsertNode) {
			//right sibling must be empty
			lastParent.rightLeaf = newNode;
			newNode.parent = lastParent;
			this.lastInsertNode = newNode;
			return;
		}
		
		//if last insert node is right child
		if(lastParent.rightLeaf == this.lastInsertNode) {
			//if lastParent is the left most on its level, find right sibling and add underneath	
			treeNode parentRightSibling = this.getNextRightSibling(lastParent);
			if( parentRightSibling != null ){
				parentRightSibling.leftLeaf = newNode;
				newNode.parent = parentRightSibling;
				this.lastInsertNode = newNode;
				return;
			} else {// lastParent is the right most
				treeNode leftMostSibling = this.getLeftMostNode(this.root);
				//if(leftMostSibling != null) {
					leftMostSibling.leftLeaf = newNode;
					newNode.parent = leftMostSibling;
					this.lastInsertNode = newNode;
					return;
				//}
			}
		}
		
	}

	/*
	 * orderIndex = 1 : In-order orderIndex = 2 : Pre-order orderIndex = 3 :
	 * Post-order
	 */
	public void printTree(treeNode rootNode, int orderIndex) {
		if (rootNode != null) {
			// System.out.println("Printing tree with root:" + rootNode.value);
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
