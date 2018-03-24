/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Code template for assignment 4
 */
import java.io.IOException;
import java.io.*;
import java.util.*;

// DO NOT CHANGE THE CLASS NAME OR PACKAGE
public class Graph225 {

	/**
	 * Simple representation of an undirected graph, using a square, symmetric
	 * adjacency matrix.
	 * <p>
	 * An adjacency matrix M represents a graph G=(V,E) where V is a set of n
	 * vertices and E is a set of m edges. The size of the matrix is {@code n},
	 * where {@code n} is in the range {@code [4, 15]} only. Thus, the rows and
	 * columns of the matrix are in the range {@code [0, n-1]} representing
	 * vertices. The elements of the matrix are 1 if the edge exists in the
	 * graph and 0 otherwise. Since the graph is undirected, the matrix is
	 * symmetric and contains 2m 1’s.
	 */
	public static class Graph {

		/**
		 * An adjacency matrix representation of this graph
		 */
		private int[][] adjacencyMatrix;

		/*
		 * You are free to add constructors, but the empty constructor is the
		 * only one invoked during marking.
		 */
		public Graph() {
		}

		/**
		 * Generate a random graph as specified in the assignment statement.
		 * 
		 * @param n
		 *            The size of the graph
		 * @param density
		 *            The density of the graph
		 */
		public void generate(int n, int density) {
			if(n < 4 || n > 15){
				return;
			}
			this.adjacencyMatrix = new int[n][n];
			Random random = new Random();
			int m = 0;
			if(density < 1 || density > 3){
				return;
			}
			if(density == 1){
				m = (7*n)/5;
			}
			if(density == 2){
				m = (n*n)/4;
			}
			if(density == 3){
				m = (2*n*n)/5;
			}
			int k = 0;
			while(k < m){
				int col = random.nextInt(n);
				int row = random.nextInt(n);
				if(this.adjacencyMatrix[row][col] == 0){
					this.adjacencyMatrix[row][col] =1;
					k++;
				}
			}
		//	throw new UnsupportedOperationException("This method has not been implemented yet.");
		}

		/**
		 * Reads an adjacency matrix from the specified file, and updates this
		 * graph's data. For the file structure please refer to the sample input
		 * file {@code testadjmat.txt}).
		 * 
		 * @param file
		 *            The input file
		 * @throws IOException
		 *             If something bad happens while reading the input file.
		 */
		public void read(String file) throws IOException {
			int rows = 0;
			int columns = 0;
			File input_file = new File(file);
			if(input_file == null){
				System.out.println("Sorry that file doesn't exist");
			}
			Scanner row = new Scanner(input_file);
			while (row.hasNextLine()){
				String line = row.nextLine();
				rows++;
			}
			columns = rows;
			this.adjacencyMatrix = new int[rows][columns];
			row.close();
			Scanner input = new Scanner(input_file);
			for(int i = 0; i < rows; i++){
    			for(int j = 0; j < columns; j++){
        			if(input.hasNextInt()){
            			this.adjacencyMatrix[i][j] = input.nextInt();
        			}			
    			}
			}
			input.close();
		//	throw new UnsupportedOperationException("This method has not been implemented yet.");
		}

		/**
		 * Writes the adjacency matrix representing this graph in the specified
		 * file.
		 * 
		 * @param file
		 *            The path of the output file
		 * @throws IOException
		 *             If something bad happens while writing the file.
		 */
		public void write(String file) throws IOException{
			if(adjacencyMatrix == null){
				System.out.println("Nothing to print.");
				return;
			}
			File output_file = new File(file);
			if(output_file == null){
				System.out.println("Sorry that file doesn't exist");
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			int rows = adjacencyMatrix.length;
			int cols = adjacencyMatrix[0].length;
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){
					writer.append(adjacencyMatrix[i][j] + " ");
				}
				writer.append("\n");
			}
			writer.close();
		//	throw new UnsupportedOperationException("This method has not been implemented yet.");
		}

		/**
		 * @return an adjacency matrix representation of this graph
		 */
		public int[][] getAdjacencyMatrix() {
			return this.adjacencyMatrix;
		}

		/**
		 * Updates this graph's adjacency matrix
		 * 
		 * @param m
		 *            The adjacency matrix representing the new graph
		 */
		public void setAdjacencyMatrix(int[][] m) {
			this.adjacencyMatrix = m;
		}

	}

	/**
	 * Traverses the given graph starting at the specified vertex, using the
	 * depth first search graph traversal algorithm.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph to traverse
	 * @param vertex
	 *            The starting vertex (as per its position in the adjacency
	 *            matrix)
	 * @return a vector R of n elements where R[j] is 1 if vertex j can be
	 *         reached from {@code vertex} and 0 otherwise
	 */
	public int[] reach(Graph graph, int vertex) {
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		int[] reach = new int[adjacencyMatrix.length];
		dfs(graph, vertex, reach);
		return reach;
	//	throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	private void dfs(Graph graph, int vertex, int[] visited){
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		visited[vertex] = 1;
		for(int i = 0; i < adjacencyMatrix.length; i++){
			if(adjacencyMatrix[vertex][i] == 1 && visited[i] == 0){
				dfs(graph, i, visited);
			}
		}
	}	

	/**
	 * Computes the number connected components of a given graph.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return The number of connected component in {@code graph}
	 */
	public int connectedComponents(Graph graph) {
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		int components = 0;
		int[] component = new int[adjacencyMatrix.length];
		for(int i = 0; i < adjacencyMatrix.length; i++){
			if(component[i] == 0){
				dfs(graph, i, component);
				components++;
			}
		}
		return components;
	//	throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	/**
	 * Determines whether a given graph contains at least one cycle.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return whether or not {@code graph} contains at least one cycle
	 */
	public boolean hasCycle(Graph graph) {
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		boolean[] visited = new boolean[graph.adjacencyMatrix.length];
		boolean result = false;
		int[][] edge = new int[adjacencyMatrix.length][adjacencyMatrix.length];
		for(int i = 0; i < adjacencyMatrix.length; i++){
			if(dfsCycle(graph, i, visited, edge)){
				result = true;
			}
		}
		return result;
	//	throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	private boolean dfsCycle(Graph graph, int vertex, boolean[] visited, int[][] edge){
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		visited[vertex] = true;
		for(int i = 0; i < adjacencyMatrix.length; i++){
			if(adjacencyMatrix[vertex][i] == 1){
				if(edge[vertex][i] == -1){
					return true;
				}else if(edge[vertex][i] == 0 && visited[i] == false){
					visited[i] = true;
					edge[vertex][i] = 1;
					return dfsCycle(graph, i, visited, edge);
				} else if(edge[vertex][i] == 0 && visited[i] == true){
					edge[vertex][i] = -1;
				}
			}
		}
		return false;
	}

	/**
	 * Computes the pre-order for each vertex in the given graph.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return a vector R of n elements, representing the pre-order of
	 *         {@code graph}
	 */
	public int[][] preOrder(Graph graph) {
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		int[][] order = new int[adjacencyMatrix.length][adjacencyMatrix.length];
		int[][] edge = new int[adjacencyMatrix.length][adjacencyMatrix.length];
		for(int i = 0; i < adjacencyMatrix.length; i++){
			for(int k = 0; k < adjacencyMatrix.length; k++){
				order[i][k] = -1;
			}
		}
		for(int i = 0; i < adjacencyMatrix.length; i++){
			boolean[] visited = new boolean[adjacencyMatrix.length];
			dfs(graph, i, visited, edge, order, true, false, 0, i);
		}
		return order;
	//	throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	private void dfs(Graph graph, int vertex, boolean[] visited, int[][] edge, int[][] order, boolean pre, boolean post, int n, int row){
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		if(pre && vertex != row){
			while(order[row][n] >-1){
				n++;
			}
			order[row][n] = vertex;
		}	
		visited[vertex] = true;
		for(int i = 0; i < adjacencyMatrix.length; i++){
			if(adjacencyMatrix[vertex][i] == 1){
				if(visited[i] == true){
					edge[vertex][i] = -1;
				}else{
					edge[vertex][i] = 1;
					if(pre){
						dfs(graph, i, visited, edge, order, true, false, n, row);
					}
					if(post){
						dfs(graph, i, visited, edge, order, false, true, n, row);
					}
				}
			}
		}
		if(post && vertex != row){
			while(order[row][n] >-1){
				n++;
			}
			order[row][n] = vertex;
			n++;
		}
	}

	/**
	 * Computes the post-order for each vertex in the given graph.
	 * <p>
	 * <b>NOTICE</b>: adjacent vertices must be visited in strictly increasing
	 * order (for automated marking)
	 * 
	 * @param graph
	 *            The graph
	 * @return a vector R of n elements, representing the post-order of
	 *         {@code graph}
	 */
	public int[][] postOrder(Graph graph) {
		int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
		int[][] order = new int[adjacencyMatrix.length][adjacencyMatrix.length];
		int[][] edge = new int[adjacencyMatrix.length][adjacencyMatrix.length];
		for(int i = 0; i < adjacencyMatrix.length; i++){
			for(int k = 0; k < adjacencyMatrix.length; k++){
				order[i][k] = -1;
			}
		}
		for(int i = 0; i < adjacencyMatrix.length; i++){
			boolean[] visited = new boolean[adjacencyMatrix.length];
			dfs(graph, i, visited, edge, order, false, true, 0, i);
		}
		return order;
	//	throw new UnsupportedOperationException("This method has not been implemented yet.");
	}

	/**
	 * test and exercise the algorithms and data structures developed for the
	 * first five parts of this assignment extensively. The output generated by
	 * this method must convince the marker that the algorithms and data
	 * structures are implemented as specified. For example:
	 * <ul>
	 * <li>Generate graphs of different sizes and densities
	 * <li>Test the algorithms for different graphs
	 * <li>Test your algorithms using the sample input file testadjmat.txt
	 * 
	 * @throws Exception
	 *             if something bad happens!
	 */
	public void test() throws Exception {
		String file = "OutputForGraph225Testing.txt";
		File output_file = new File(file);
			if(output_file == null){
				System.out.println("Sorry that file doesn't exist");
			}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		writer.append("Testing the generate method by making graphs of size 4-15 with random density.");
		writer.append("\n");
		writer.append("\n");
		Random ran = new Random();
		for(int i = 4; i < 16; i++){
			Graph n = new Graph();
			int k = ran.nextInt(3)+1;
			n.generate(i, k);
		}
		Graph test = new Graph();
		test.generate(6, 2);
		try{
			test.write("test.txt");
		} catch(IOException e){
			System.out.println("Something bad happened in the write method.");
		}
		
		Graph test1 = new Graph();
		try{
			test1.read("testadjmat.txt");
		} catch(IOException e){
			System.out.println("Something bad happened in the read method.");
		}
		try{
			test1.write("test1output.txt");
		} catch(IOException e){
			System.out.println("Something bad happened in the write method.");
		}
		int[] test1Reach = reach(test1,0);
		writer.append("Reachability of given test graph: ");
		for(int i = 0; i < test1Reach.length; i++){
			writer.append(test1Reach[i] + " ");
		}
		writer.append("\n");
		writer.append("\n");
		int[] testReach = reach(test,0);
		writer.append("Reachability of random test graph: ");
		for(int i = 0; i < testReach.length; i++){
			writer.append(testReach[i] + " ");
		}
		writer.append("\n");
		writer.append("\n");
		writer.append("Connected components of given test graph: ");
		int test1Components = connectedComponents(test1);
		writer.append(test1Components + "");
		writer.append("\n");
		writer.append("\n");
		writer.append("Connected components of random test graph: ");
		int testComponents = connectedComponents(test);
		writer.append(testComponents + "");
		writer.append("\n");
		writer.append("\n");
		writer.append("Testing the cycle detection method on given test graph.");
		writer.append("\n");
		writer.append("Output should be true: ");
		boolean test1Cycle = hasCycle(test1);
		writer.append(test1Cycle + "");
		writer.append("\n");
		writer.append("\n");
		writer.append("Testing the cycle detection method on random test graph.");
		writer.append("\n");
		boolean testCycle = hasCycle(test);
		writer.append(testCycle  + "");
		writer.append("\n");
		writer.append("\n");
		//Reads a graph I generated
		Graph noCycleTest = new Graph();
		try{
			noCycleTest.read("noCycleTest.txt");
		} catch(IOException e){
			System.out.println("Something bad happened in the read method.");
		}
		writer.append("Testing the cycle detection method on test graph with no cycle.");
		writer.append("\n");
		writer.append("Output should be false: ");
		boolean noCycleTestHasCycle = hasCycle(noCycleTest);
		writer.append(noCycleTestHasCycle  + "");
		writer.append("\n");
		
		//Reads a graph I generated
		Graph preOrdertest = new Graph();
		preOrdertest.read("preOrdertest.txt");
		int[][] preOrdertestOut = preOrder(preOrdertest);
		writer.append("\n");
		writer.append("Testing pre order method based on the example given.");
		writer.append("\n");
		for(int i = 0; i < preOrdertestOut.length; i++){
			for(int k = 0; k < preOrdertestOut.length; k++){
				int tmp = preOrdertestOut[i][k];
				if(tmp >= 0){
					writer.append(" " + tmp + " ");
				}else{
					writer.append(tmp + " ");
				}
			}
			writer.append("\n");
		}
		writer.append("\n");

		//Reads a graph I generated
		Graph postOrdertest = new Graph();
		postOrdertest.read("preOrdertest.txt");
		int[][] postOrdertestOut = postOrder(postOrdertest);
		writer.append("Testing post order method based on the example given.");
		writer.append("\n");
		for(int i = 0; i < postOrdertestOut.length; i++){
			for(int k = 0; k < postOrdertestOut.length; k++){
				int tmp = postOrdertestOut[i][k];
				if(tmp >= 0){
					writer.append(" " + tmp + " ");
				}else{
					writer.append(tmp + " ");
				}
			}
			writer.append("\n");
		}
		writer.append("\n");

		//Uses a random graph
		Graph preOrderRanTest = new Graph();
		preOrderRanTest.generate(7, 3);
		int[][] preOrderRanTestOut = preOrder(preOrderRanTest);
		writer.append("\n");
		writer.append("Testing pre order method based on random graph.");
		writer.append("\n");
		for(int i = 0; i < preOrderRanTestOut.length; i++){
			for(int k = 0; k < preOrderRanTestOut.length; k++){
				int tmp = preOrderRanTestOut[i][k];
				if(tmp >= 0){
					writer.append(" " + tmp + " ");
				}else{
					writer.append(tmp + " ");
				}
			}
			writer.append("\n");
		}
		writer.append("\n");

		Graph postOrderRanTest = new Graph();
		postOrderRanTest.generate(5, 1);
		int[][] postOrderRanTestOut = postOrder(postOrderRanTest);
		writer.append("Testing post order method based on random graph.");
		writer.append("\n");
		for(int i = 0; i < postOrderRanTestOut.length; i++){
			for(int k = 0; k < postOrderRanTestOut.length; k++){
				int tmp = postOrderRanTestOut[i][k];
				if(tmp >= 0){
					writer.append(" " + tmp + " ");
				}else{
					writer.append(tmp + " ");
				}
			}
			writer.append("\n");
		}
		writer.append("\n");
		writer.append("Making a 15 x 15 graph with density 3, printing to largeGraph.txt.");
		writer.append("\n");
		Graph largeGraph = new Graph();
		largeGraph.generate(15,3);
		try{
			largeGraph.write("largeGraph.txt");
		} catch(IOException e){
			System.out.println("Something bad happened in the write method.");
		}
		writer.close();
	//	throw new UnsupportedOperationException("This method has not been implemented yet.");
	}
	public static void main(String[] args){
		Graph225 g = new Graph225();
		try{
			g.test();
		}catch (Exception e){
			System.out.println("Something bad happened in the test method.");
		}
	}
}

