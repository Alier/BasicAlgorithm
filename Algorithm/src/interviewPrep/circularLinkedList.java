package interviewPrep;

public class circularLinkedList {
	circularLinkedListNode head;
	int nodeCount;

	// Initialize from char array
	public circularLinkedList(char[] charArray) {
		char firstChar = charArray[0];
		head = new circularLinkedListNode(firstChar);
		nodeCount = 1;
		circularLinkedListNode tail = head;

		for (int i = 1; i < charArray.length; i++) {
			char curChar = charArray[i];
			// if char exists, add loop;
			circularLinkedListNode existNode = itemExistInList(curChar);

			if (existNode != null) {
				tail.next = existNode;
				break;
			} else { // add to end of list
				circularLinkedListNode node = new circularLinkedListNode(
						curChar);
				tail.next = node;
				node.next = null;
				tail = node;
				nodeCount++;
			}
		}
	}

	public circularLinkedListNode itemExistInList(char item) {
		circularLinkedListNode p = head;
		while (p.next != null) {
			if (p.item == item)
				return p;
			else p = p.next;
		}
		return null;
	}

	public char findCircleStart() {
		circularLinkedListNode p1 = head;
		circularLinkedListNode p2 = head;

		// find meeting point
		for (int i = 0; i < nodeCount; i++) {
			p1 = p1.next;
			p2 = p2.next.next;

			if (p1.item == p2.item)
				break;
		}

		p1 = head;
		while (p1.item != p2.item) {
			p1 = p1.next;
			p2 = p2.next;
		}
		
		return p1.item;
	}
}
