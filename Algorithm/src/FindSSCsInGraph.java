import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class FindSSCsInGraph {

	/**
	 * @param args
	 */
	private HashMap<Integer, LinkedList<Integer>> originalGraph; // keep
																	// original
																	// Graph,
																	// each
																	// vertex
	// corresponds to an index and
	// the content of that index is
	// a BitSet of node size N. If
	// there is edge exist between
	// vertices s and v, then the
	// bit of v would be true on
	// originalGraph[s]
	private HashMap<Integer, LinkedList<Integer>> reversedGraph;
	private int totalNumNodes; // number of total nodes
	private BitSet nodesStatus; // keep 1/0 for nodes explored/not
	private int[] nodesFinishtime; // keep finishing time for all
									// nodes in first
	private int[] nodesSortedbyFt; // sorted nodes by finishing time increasing
									// order
	private int[] nodesStack; // simulate stack for nodes when doing dfs
	private int[] sortedSCCsSize; // size of each SCCs;

	// time reversed graph dfs
	private HashMap<Integer, LinkedList<Integer>> sccsOfLeader; // leader is the
																// key, nodes
																// belongs to
																// the key in
																// the linked
																// list;
	private int curFinishTime; // global, current f(n) for first DFS loop
	private int curLeader; // global, current Leader for 2nd DFS loop
	private int topOfStack; // the pointer to the top of the stack in array
							// nodesStack;

	public FindSSCsInGraph(int numNodes, String filename) throws IOException {
		originalGraph = new HashMap<Integer, LinkedList<Integer>>();
		reversedGraph = new HashMap<Integer, LinkedList<Integer>>();
		nodesStatus = new BitSet(numNodes);
		nodesFinishtime = new int[numNodes];
		nodesSortedbyFt = new int[numNodes];
		nodesStack = new int[numNodes];
		sortedSCCsSize = new int[numNodes];
		sccsOfLeader = new HashMap<Integer, LinkedList<Integer>>();
		totalNumNodes = numNodes;

		curFinishTime = 1;
		curLeader = -1;
		topOfStack = -1;

		for (int i = 0; i < numNodes; i++) {
			originalGraph.put(i, new LinkedList<Integer>());
			reversedGraph.put(i, new LinkedList<Integer>());
			nodesFinishtime[i] = 0;
			nodesSortedbyFt[i] = i; // initially set to be node's natural order
			nodesStack[i] = -1;
		}

		readFileIntoMemory(filename, 100);
	}

	// push vertex into stack
	public void pushToStack(int nodeIndex) {
		topOfStack++;
		nodesStack[topOfStack] = nodeIndex;
	}

	// pop the top out of stack
	public void popFromStack() {
		nodesStack[topOfStack] = -1;
		topOfStack--;
	}

	public void readFileIntoMemory(String fileName, int lineNum)
			throws IOException {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int headVertex = 0;
		int tailVertex = 0;
		// int lineNo =0;

		while (line != null) {
			// lineNo++;
			String[] vertexRow = line.split(" ");
			headVertex = Integer.parseInt(vertexRow[0]);
			tailVertex = Integer.parseInt(vertexRow[1]);

			// set the bit in originalGraph for index headVertex
			// initialize originalGraph and nodesFinishtime;
			LinkedList<Integer> tmp = originalGraph.get(headVertex - 1);
			tmp.add(tailVertex - 1);

			// set the bit in reversedGraph for index tailVertex
			LinkedList<Integer> tmp2 = reversedGraph.get(tailVertex - 1);
			tmp2.add(headVertex - 1);

			line = br.readLine();
		}
		br.close();
	}

	public void firstDFSwithReversedG() {
		for (int fromNode = 0; fromNode < totalNumNodes; fromNode++) {
			// if not explored then go ahead, otherwise break
			if (!nodesStatus.get(fromNode)) {
				DFSforReversedG(fromNode);
			}
		}
	}

	public void DFSforReversedG(int nodeIndex) {
		// push to stack, and mark explored;
		pushToStack(nodeIndex);
		nodesStatus.set(nodeIndex);
		// printNodesStack();

		// find next node this node has an directed edge to,that's not explored
		// yet and do DFS on it, startBit to mark the index from which we
		// should search for next neighbor node in the bitSet
		int toNode = -1;
		LinkedList<Integer> neighbors = reversedGraph.get(nodeIndex);
		for (int i = 0; i < neighbors.size(); i++) {
			toNode = neighbors.get(i);
			if (!nodesStatus.get(toNode)) { // if neighbor has not been explored
				DFSforReversedG(toNode);
			}
		}

		// searched till last node;
		popFromStack();
		nodesFinishtime[nodeIndex] = curFinishTime;
		curFinishTime++;
	}

	public void DFSforG(int nodeIndex) {
		// push to stack, and mark explored;
		pushToStack(nodeIndex);
		nodesStatus.set(nodeIndex);

		// find next node this node has an directed edge to,that's not explored
		// yet and do DFS on it, startBit to mark the index from which we
		// should search for next neighbor node in the bitSet
		int toNode = -1;
		LinkedList<Integer> neighbors = originalGraph.get(nodeIndex);
		for (int i = 0; i < neighbors.size(); i++) {
			toNode = neighbors.get(i);
			if (!nodesStatus.get(toNode)) { // if neighbor has not been explored
				DFSforG(toNode);
			}

		}

		popFromStack();
		sccsOfLeader.get(curLeader).add(nodeIndex);

	}

	// before doing this , make sure to reset the nodesStatus array and
	// nodesStack
	public void secondDFSwithG() {// nodesSortedbyFt is in f(n) increasing
									// order, so get from the last
		for (int fromNode = totalNumNodes - 1; fromNode > 0; fromNode--) {
			curLeader = nodesSortedbyFt[fromNode];
			// if not explored then go ahead, otherwise break
			if (!nodesStatus.get(curLeader)) {
				// curLeader = nodesSortedbyFt[fromNode]; // is 1 less than real
				// node No.
				// create the list with current node as leader
				sccsOfLeader.put(curLeader, new LinkedList<Integer>());
				DFSforG(curLeader);
			}
		}
	}

	// basically when sorting the nodesFinishtime, sorting the nodesSortedbyFt
	// the same way, because they're corresponding to each other;
	public void sortNodesByFinishingTime() {
		ArraySort.quickSort2(nodesFinishtime, nodesSortedbyFt, 0,
				totalNumNodes - 1);

		// for(int i=0;i<totalNumNodes-1;i++){
		// for(int j=i+1; j<totalNumNodes;j++){
		// if(nodesFinishtime[i]<nodesFinishtime[j]){
		// int tmp = nodesFinishtime[j];
		// nodesFinishtime[j]=nodesFinishtime[i];
		// nodesFinishtime[i]=tmp;
		//
		// int tmp2 = nodesSortedbyFt[j];
		// nodesSortedbyFt[j]=nodesSortedbyFt[i];
		// nodesSortedbyFt[i]=tmp2;
		// }
		// }
		// }
		//
		// ArraySort.quickSort2(nodesFinishtime, nodesSortedbyFt, 0,
		// totalNumNodes - 1);
		// ArraySort.printArray(totalNumNodes, nodesFinishtime);
		// ArraySort.printArray(totalNumNodes, nodesSortedbyFt);
	}

	public void printSccs() {
		Iterator<Integer> it = sccsOfLeader.keySet().iterator();
		int j = 0;
		while (it.hasNext() && j < 10) {
			int leaderVertex = it.next();
			System.out.print((leaderVertex + 1) + ":");
			for (int i = 0; i < sccsOfLeader.get(leaderVertex).size(); i++) {
				System.out.print((sccsOfLeader.get(leaderVertex).get(i) + 1)
						+ ",");
			}
			System.out.println();
			j++;
		}
	}

	public void printSizeOfSccs(int length, int dec_flag) {
		for (int i = 0; i < 100; i++) {
			if (dec_flag>0)
				System.out.print(sortedSCCsSize[length-1-i] + " ");
			else
				System.out.print(sortedSCCsSize[i] + " ");
		}
	}

	public void sortSccsBySize() {
		Iterator<Integer> it = sccsOfLeader.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			int leaderVertex = it.next();
			int sizeOfSccs = sccsOfLeader.get(leaderVertex).size();
			sortedSCCsSize[i] = sizeOfSccs;
			i++;
		}

		// sorting the array
		int length = i;
		System.out.println("length=" + length);
		printSizeOfSccs(length, 0);

		ArraySort.quickSort(sortedSCCsSize, 0, length - 1);
		printSizeOfSccs(length, 1);

		// for(int j=0;i<length-1;j++){
		// for(int k=j+1;k<length;k++){
		// if(sortedSCCsSize[j]<sortedSCCsSize[k]){
		// int tmp = sortedSCCsSize[k];
		// sortedSCCsSize[k] =sortedSCCsSize[j];
		// sortedSCCsSize[j] =tmp;
		// }
		// }
		// }
	}

	public void printNodesStack() {
		for (int i = 0; i < topOfStack; i++) {
			System.out.print((nodesStack[i] + 1) + " ");
			if (i % 100 == 0)
				System.out.println();
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FindSSCsInGraph obj = new FindSSCsInGraph(875714, "../SCC.txt");
		// FindSSCsInGraph obj = new FindSSCsInGraph(6, "testSCC");
		// FindSSCsInGraph obj2 = new FindSSCsInGraph(5, "RevtestSCC");

		// System.out.println(obj.originalGraph.keySet().size());
		// Iterator<Integer> it = obj.originalGraph.keySet().iterator();
		// int i=0;
		// while (it.hasNext() && i<=100) {
		// int curVertex = it.next();
		// int neighborSize = obj.originalGraph.get(curVertex).size();
		// System.out.println(curVertex+":"+neighborSize+" ");
		// i++;
		// }
		obj.firstDFSwithReversedG();
		// ArraySort.printArray(10, obj.nodesFinishtime);
		// System.out.println("----------------------------");
		// ArraySort.printArray(6, obj.nodesSortedbyFt);

		// System.out.println("----------------------------");

		obj.sortNodesByFinishingTime();
		// ArraySort.printArray(10, obj.nodesFinishtime);
		// System.out.println("----------------------------");
		// ArraySort.printArray(10, obj.nodesSortedbyFt);

		obj.nodesStatus.clear();
		obj.secondDFSwithG();
		//
		obj.sortSccsBySize();
		// obj.printSizeOfSccs();

		// System.out.println("------------------");
		// obj2.firstDFSwithReversedG();
		// //ArraySort.printArray(15, obj.nodesFinishtime);
		// //ArraySort.printArray(6, obj.nodesSortedbyFt);
		//
		// obj2.sortNodesByFinishingTime();
		// obj2.nodesStatus.clear();
		// obj2.secondDFSwithG();
		//
		// obj2.printSccs();
	}
}