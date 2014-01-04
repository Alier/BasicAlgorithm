import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphProblem {
	private HashMap<Integer, HashMap<Integer, Integer>> originalGraph; // <cur_vertex
																		// <neighbor_v,edge
																		// weight
																		// to
																		// neighbor_v>,<>>,
																		// nodes
																		// from
																		// 1-200
	private ArrayList<Integer> exploredNodes; // keep the indexes of explored
												// nodes; 1-200
	private ArrayList<Integer> unexploredNodes; // keep the indexes of
												// unexplored nodes; 1-200

	private int[] shortestPathNum; // keep the shortest path (number) for every
									// node
	private HashMap<Integer, LinkedList<Integer>> shortestPath; // keep the
																// actual
																// shortest path
	// of every node in format 1-2-3
	// (from 1->3)
	private int totalNumNodes; // total number of vertices
	private int startV; // starting vertex S;

	public GraphProblem(String fileName, int numNodes, int s_Index)
			throws IOException {
		totalNumNodes = numNodes;
		startV = s_Index;
		originalGraph = new HashMap<Integer, HashMap<Integer, Integer>>();
		exploredNodes = new ArrayList<Integer>(totalNumNodes);
		unexploredNodes = new ArrayList<Integer>(totalNumNodes);
		shortestPathNum = new int[totalNumNodes];
		shortestPath = new HashMap<Integer, LinkedList<Integer>>();
		for (int i = 0; i < totalNumNodes; i++) {
			shortestPathNum[i] = 100000; // by default the +big number
			unexploredNodes.add(i + 1); // every node is unexplored at first
		}

		readFileIntoMemory(fileName);
	}

	public void readFileIntoMemory(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int curVertex = 0;
		int neighborVertex = 0;
		int edgeWeightToNeighbor = 0;

		while (line != null) {
			String[] vertexRow = line.split("\t");
			HashMap<Integer, Integer> neighborCount = new HashMap<Integer, Integer>();
			curVertex = Integer.parseInt(vertexRow[0]);
			for (int i = 1; i < vertexRow.length; i++) {
				String[] neighborInfo = vertexRow[i].split(",");
				neighborVertex = Integer.parseInt(neighborInfo[0]);
				edgeWeightToNeighbor = Integer.parseInt(neighborInfo[1]);
				neighborCount.put(neighborVertex, edgeWeightToNeighbor);
			}
			originalGraph.put(curVertex, neighborCount);

			line = br.readLine();
		}
		br.close();
	}

	public void DijkstraAlgorithm() {
		int s = startV; // s is the starting vertex

		// mark s as explored
		exploredNodes.add(s);
		// int indexOfS = unexploredNodes.indexOf(s);
		// unexploredNodes.remove(indexOfS);

		// update the shorted path from s to s to be 0, and add s to the Path
		// from s to s
		shortestPathNum[s - 1] = 0;
		LinkedList<Integer> pathOfS = new LinkedList<Integer>();
		pathOfS.add(s);
		shortestPath.put(s, pathOfS);

		int shortestPathLen = 0;
		int curPathLen = 0;
		int curExploredNode = 0;
		int curNeighbor = 0;

		// save the shortest path found s-v
		int shortestS = 0; // from 1-200
		int shortestV = 0; // from 1-200

		while (exploredNodes.size() < totalNumNodes) { // loop till all nodes
														// visited
			shortestPathLen = 100000; // shortest path from starting node to any
										// node in {unexplored nodes}
			curPathLen = 100000;

			// find the shorted path from {explored nodes} -> {unexplored nodes}
			for (int i = 0; i < exploredNodes.size(); i++) { // for every node
																// in
																// {explored
																// nodes}
				curExploredNode = exploredNodes.get(i);
				HashMap<Integer, Integer> neighbors = originalGraph
						.get(curExploredNode);
				Iterator<Integer> it = neighbors.keySet().iterator();
				while (it.hasNext()) {
					curNeighbor = it.next();
					if (!exploredNodes.contains(curNeighbor)) {// if neighbor
																// is not in
																// {explored
																// nodes}
						curPathLen = shortestPathNum[curExploredNode - 1]
								+ neighbors.get(curNeighbor);
						if (curPathLen < shortestPathLen) {
							shortestPathLen = curPathLen;
							// save current s-v
							shortestS = curExploredNode;
							shortestV = curNeighbor;
						}
					}
				}
			}

			// System.out.println(shortestPathLen+":("+shortestS+","+shortestV+")");

			// add node v into explored and update v's shortestPathNum and
			// shortestPath
			exploredNodes.add(shortestV);

			shortestPathNum[shortestV - 1] = shortestPathLen;
			LinkedList<Integer> pathForS = shortestPath.get(shortestS);
			LinkedList<Integer> pathForV = new LinkedList<Integer>(pathForS);
			pathForV.add(shortestV);
			shortestPath.put(shortestV, pathForV);

			// System.out.println();
			// printShortestPath();
			// System.out.println();
			// printShortestPathNums();

		}
	}

	public void printOriginalGraph() {
		int vertex = 0;
		int neighbor = 0;
		int edge = 0;

		Iterator<Integer> it = originalGraph.keySet().iterator();
		while (it.hasNext()) {
			vertex = it.next();
			System.out.print(vertex + ":");
			HashMap<Integer, Integer> neighbors = originalGraph.get(vertex);
			Iterator<Integer> innerIt = neighbors.keySet().iterator();
			while (innerIt.hasNext()) {
				neighbor = innerIt.next();
				edge = neighbors.get(neighbor);
				System.out.print("(" + neighbor + "," + edge + ") ");
			}
			// System.out.println();
		}
	}

	public void printShortestPathNums() {
		for (int i = 0; i < totalNumNodes; i++) {
			int j = i + 1;
			if (j == 7 || j == 37 || j == 59 || j == 82 || j == 99 || j == 115
					|| j == 133 || j == 165 || j == 188 || j == 197)
				System.out.println(j + ":" + shortestPathNum[i]);
		}
	}

	public void printShortestPath() {
		int vertex = 0;
		int nextV = 0;

		Iterator<Integer> it = shortestPath.keySet().iterator();
		while (it.hasNext()) {
			vertex = it.next();
			System.out.print(vertex + ":");
			LinkedList<Integer> pathV = shortestPath.get(vertex);
			for (int i = 0; i < pathV.size(); i++) {
				nextV = pathV.get(i);
				System.out.print(nextV + ",");
			}
			System.out.println();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		GraphProblem obj = new GraphProblem("dijkstraData.txt", 200, 1);
		// GraphProblem obj = new GraphProblem("testSCC",5, 1);
		obj.DijkstraAlgorithm();
		obj.printShortestPathNums();
		System.out.println();
		obj.printShortestPath();
		// obj.printOriginalGraph();
		// System.out.println("Finished\n");
	}

}
