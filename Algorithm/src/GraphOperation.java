import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphOperation {
	HashMap<Integer, HashMap<Integer, Integer>> originalGraph; // original Graph
	HashMap<Integer, HashMap<Integer, Integer>> graphMatrix; // save the graph
																// in rows:
																// vertexs:
																// <neighborX,
																// edge to
																// neighborX>..
	HashMap<Integer, LinkedList<Integer>> mergedNodes; // save the mapping
														// between final node :
														// nodes contained in
														// this node

	public GraphOperation() {
		originalGraph = new HashMap<Integer, HashMap<Integer, Integer>>();
		graphMatrix = new HashMap<Integer, HashMap<Integer, Integer>>();
		mergedNodes = new HashMap<Integer, LinkedList<Integer>>();
	}

	public int kargerRandomMinCut() {
		int retval = 0;
		while (graphMatrix.keySet().size() > 2) {
			// printMergedNodes();
			// Integer[] vertexArray = (Integer[])
			// graphMatrix.keySet().toArray();
			ArrayList<Integer> vertexArray = new ArrayList<Integer>(
					graphMatrix.keySet());
			int randomVertexIndex = (int) (Math.random() * (vertexArray.size()));
			int randomVertex = vertexArray.get(randomVertexIndex);

			HashMap<Integer, Integer> neighborTable = graphMatrix
					.get(randomVertex);
			ArrayList<Integer> neighborArray = new ArrayList<Integer>(
					neighborTable.keySet());
			int randomNeighborIndex = (int) (Math.random() * (neighborArray
					.size()));
			int randomNeighbor = neighborArray.get(randomNeighborIndex);

			// System.out.println("VerticeArraylen=" + vertexArray.size()
			// + " neighborArrayLen=" + neighborArray.size());
			// System.out.println("vertex=" + randomVertex + " neighbor="
			// + randomNeighbor);

			shrinkByEdge(randomVertex, randomNeighbor);
			// System.out.println("------------------------------------");
		}

		// when only two elements left in array, they're essentially A,B, print
		// A and B, and the number of crossing edges between them
		Iterator<Integer> it = graphMatrix.keySet().iterator();
		while (it.hasNext()) {
			int vertex = it.next();
			HashMap<Integer, Integer> neighbor = graphMatrix.get(vertex);

			// System.out.print(vertex + ":");
			Iterator<Integer> innerIt = neighbor.keySet().iterator();
			while (innerIt.hasNext()) {
				int neighborVertex = innerIt.next();
				int edgeCount = neighbor.get(neighborVertex);
				retval = edgeCount;
				// System.out.print("(" + neighborVertex + "," + edgeCount +
				// ")");
			}

			// LinkedList<Integer> nodes = mergedNodes.get(vertex);
			// if (nodes != null) {
			// System.out.print("[");
			// for (int i : nodes) {
			// System.out.print(i + ",");
			// }
			// System.out.println("]");
			// } else {
			// System.out.println("null!!");
			// }
		}
		return retval;
	}

	public void printMergedNodes() {
		Iterator<Integer> it = mergedNodes.keySet().iterator();
		while (it.hasNext()) {
			int vertex = it.next();
			System.out.print(vertex + ":");
			LinkedList<Integer> nodes = mergedNodes.get(vertex);
			if (nodes != null) {
				System.out.print("[");
				for (int i : nodes) {
					System.out.print(i + ",");
				}
				System.out.println("]");
			} else {
				System.out.println("null!!");
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public void shrinkByEdge(int vertex1, int vertex2) {
		int lowVertex = vertex1 > vertex2 ? vertex2 : vertex1;
		int highVertex = vertex1 > vertex2 ? vertex1 : vertex2;

		// System.out.println("low:high =" + lowVertex + ":" + highVertex);
		HashMap<Integer, Integer> neighborHigh = graphMatrix.get(highVertex);
		HashMap<Integer, Integer> neighborLow = graphMatrix.get(lowVertex);

		// 1.go through neighbors of highVertex, if it's lowVertex, don't do
		// anything, otherwise replace it with lowVertex in other neighbor's row
		Iterator<Integer> it = neighborHigh.keySet().iterator();
		while (it.hasNext()) {
			int neighborVertex = it.next();
			int edgeToNeighbor = neighborHigh.get(neighborVertex);

			/*
			 * if it's edge to other vertices: 1. add these edges to low
			 * vertex's neighbor table; 2. modify the neighbor list of
			 * corresponding vertices, replacing high vertex by low vertex
			 */
			if (neighborVertex != lowVertex) {
				// 1. if lowVertex has edge(s) to this vertex already, then just
				// add up the No.edges, otherwise add this entry
				if (neighborLow.containsKey(neighborVertex)) {
					int existEdgeNumber = neighborLow.get(neighborVertex);
					int newEdgeNumber = existEdgeNumber + edgeToNeighbor;
					neighborLow.put(neighborVertex, newEdgeNumber);
				} else {
					neighborLow.put(neighborVertex, edgeToNeighbor);
				}

				/*
				 * 2. update this neighborVertex's neighbor table. If it already
				 * has edge to lowVertex, increase the No.edge, otherwise, add
				 * this entry; remove the highVertex entry
				 */
				HashMap<Integer, Integer> curNeighbor = graphMatrix
						.get(neighborVertex);
				// this should happen all the time
				if (curNeighbor.containsKey(highVertex)) {
					int edgeToHigh = curNeighbor.get(highVertex);
					if (curNeighbor.containsKey(lowVertex)) {
						int edgeToLow = curNeighbor.get(lowVertex);
						int newEdgeNum = edgeToLow + edgeToHigh;
						curNeighbor.put(lowVertex, newEdgeNum);
					} else {
						curNeighbor.put(lowVertex, edgeToHigh);
					}
					curNeighbor.remove(highVertex);
				}

			}
		}

		// 3.remove self-loop for low vertex's neighbor table; (if neighbor is
		// highVertex, remove this entry from neighbor table);
		neighborLow.remove(highVertex);

		// 4.remove the entry of high Vertex from graphMatrix
		graphMatrix.remove(highVertex);

		/*
		 * Update the mergedNodes table 1. If lowVertex doesn't have an entry
		 * there, add entry with key lowVertex, value highVertex; 2. If
		 * lowVertex already has an entry there, add highVertex to the end of
		 * it; 3. If highVertex already has an entry there, add lowVertex entry
		 * and copy all nodes in highVertex to lowVertex entry;
		 */
		if (mergedNodes.containsKey(lowVertex)) {
			LinkedList<Integer> existNodesMerged = mergedNodes.get(lowVertex);
			if (mergedNodes.containsKey(highVertex)) {
				LinkedList<Integer> highNodes = mergedNodes.get(highVertex);
				existNodesMerged.addAll(highNodes);
			}
			existNodesMerged.add(highVertex);
		} else {
			LinkedList<Integer> nodesMerged = new LinkedList<Integer>();
			if (mergedNodes.containsKey(highVertex)) {
				LinkedList<Integer> highNodes = mergedNodes.get(highVertex);
				nodesMerged.addAll(highNodes);
			}
			nodesMerged.add(highVertex);
			mergedNodes.put(lowVertex, nodesMerged);
		}
	}

	public void readFileIntoMemory(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int curVertex = 0;
		int neighborVertex = 0;
		int edgeToNeighbor = 1;

		while (line != null) {
			String[] vertexRow = line.split("\t");
			HashMap<Integer, Integer> neighborCount = new HashMap<Integer, Integer>();
			curVertex = Integer.parseInt(vertexRow[0]);
			for (int i = 1; i < vertexRow.length; i++) {
				neighborVertex = Integer.parseInt(vertexRow[i]);
				neighborCount.put(neighborVertex, edgeToNeighbor);
			}
			graphMatrix.put(curVertex, neighborCount);

			line = br.readLine();
		}
		br.close();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int lowest=Integer.MAX_VALUE;
		
		for (int i = 0; i < 100000; i++) {
			GraphOperation obj = new GraphOperation();
			obj.readFileIntoMemory("kargerMinCut.txt");
			
			int retval =obj.kargerRandomMinCut();
			if(retval<lowest){
				lowest=retval;
			}
		}
		
		System.out.print(lowest);

		// Iterator<Integer> it = obj.graphMatrix.keySet().iterator();
		// while (it.hasNext()) {
		// int key = it.next();
		// System.out.print(key + ":");
		// HashMap<Integer, Integer> value = obj.graphMatrix.get(key);
		// Iterator<Integer> innerIt = value.keySet().iterator();
		// while (innerIt.hasNext()) {
		// int innerKey = innerIt.next();
		// int innerValue = value.get(innerKey);
		// System.out.print("(" + innerKey + "," + innerValue + ")" + " ");
		// }
		// System.out.println();
		// }
		//
		// System.out.println(obj.graphMatrix.keySet().size());
	}

}
