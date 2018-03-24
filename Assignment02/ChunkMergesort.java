/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 2 - Template for ChunkMergesort
 * 
 * This template includes some testing code to help verify the implementation.
 * To interactively provide test inputs, run the program with:
 * 
 *     java ChunkMergesort
 * 
 * To conveniently test the algorithm with a large input, create a text file
 * containing space-separated integer values and run the program with:
 * 
 *     java ChunkMergesort file.txt
 * 
 * where file.txt is replaced by the name of the text file.
 * 
 * Miguel Jimenez
 * 
 */
 
 /*
  * Name: Noah Boulton
  * Date: Oct 8, 2016
  * Filename: ChunkMergesort.java
  * Details: CSC225 Assignment02
  */ 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Do not change the name of the ChunkMergesort class
public final class ChunkMergesort {
	
	/**
	 * Use this class to return two lists.
	 * 
	 * Example of use:
	 * 
	 * Chunks p = new Chunks(S1, S2); // where S1 and S2 are lists of integers
	 */
	public static final class Chunks {
		private final List<Integer> left;
		private final List<Integer> right;
		
		public Chunks(List<Integer> left, List<Integer> right) {
			this.left = left;
			this.right = right;
		}
		
		public List<Integer> left() {
			return this.left;
		}
		
		public List<Integer> right() {
			return this.right;
		}
	}

	/**
	 * The list containing the integer comparisons in order of occurrence. Use
	 * this list to persist the comparisons; the method report will be invoked
	 * to write a text file containing this information.
	 * 
	 * Example of use (when comparing 1 and 9):
	 * 
	 * Integer[] d = new Integer[2];
	 * d[0] = 1;
	 * d[1] = 9;
	 * this.comparisons.add(d);
	 * 
	 * or just:
	 * 
	 * this.comparisons.add(new Integer[]{1, 9});
	 */
	private final List<Integer[]> comparisons;

	public ChunkMergesort() {
		this.comparisons = new ArrayList<Integer[]>();
	}
	
	/** 
	 * The recursive method that sorted the list
	 * @param The list to be sorted
 	 * @return The sorted list
 	 */
	public List<Integer> chunkMergesort(List<Integer> S) {
		if (S.size() < 2) {
 			return S;
 		}
 		int c = chunks(S);
 		if (c == 1){
 			return S;
 		}
 		Chunks p = chunkDivide(S, c);
 		List<Integer> S1 = p.left;
 		List<Integer> S2 = p.right;
		S1 = chunkMergesort(S1);
		S2 = chunkMergesort(S2);
		S = merge(S1, S2);
		return S;
	}
	
	/** 
	 * Keeps track of comparisons between elements
	 * @param Two integers that were compared in another method
 	 * @return The item of a specified node in the tree
 	 */
	private boolean comps(int a, int b){
		this.comparisons.add(new Integer[]{a, b});
		return true;
	}
	
	/** 
	 * Determines the number of already sorted chunks in a list
	 * @param The list 
 	 * @return The number of chunks in the list
 	 */
	private int chunks(List<Integer> S){
		int c = 1;
		for(int i = 0; i < S.size() -1; i++) {
			if(comps(S.get(i), S.get(i+1)) && S.get(i+1) < S.get(i)){		
				c++;
			}
		}
		return c;
	}
	
	/** 
	 * Divides the list into two sorted lists, perserving the already sorted chunks
	 * @param The lists to be split and the number of chunks in the list
 	 * @return The Chunks data type containing the two lists
 	 */
	public Chunks chunkDivide(List<Integer> S, int c) {
		double chunks = Math.ceil(c/2.0);
		List<Integer> S1 = new ArrayList<Integer>();
		List<Integer> S2 = new ArrayList<Integer>();
		int j = 0;
		while(j < chunks) {	
			while(comps(S.get(0), S.get(1)) && S.size() > 1 && (S.get(0) < S.get(1))){		//comparisons
				S1.add(S.remove(0));
			}
			S1.add(S.remove(0));
			j++;
		} 
		while(S.size() != 0) {
			S2.add(S.remove(0));
		}	
		Chunks p = new Chunks(S1, S2);
		return p;
	}
	
	/** 
	 * Merges the two sorted lists
	 * @param Two sorted lists to be merged 
 	 * @return The merged and sorted list
 	 */
	public List<Integer> merge(List<Integer> S1, List<Integer> S2) {
		List<Integer> S = new ArrayList<Integer>();
		while(!(S1.isEmpty() || S2.isEmpty())){
			if(comps(S1.get(0), S2.get(0)) && S1.get(0) < S2.get(0)) {					
				S.add(S1.remove(0));
			} else {
				S.add(S2.remove(0));
			}
		}
		while(!(S1.isEmpty())){
			S.add(S1.remove(0));
		}while(!(S2.isEmpty())){
			S.add(S2.remove(0));
		}
		return S;
	}

	/**
	 * Writes a text file containing all the integer comparisons in order of
	 * ocurrence.
	 * 
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public void report() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("comparisons.txt", false));
		for (Integer[] data : this.comparisons)
			writer.append(data[0] + " " + data[1] + "\n");
		writer.close();
	}

	/**
	 * Contains code to test the chunkMergesort method. Nothing in this method
	 * will be marked. You are free to change the provided code to test your
	 * implementation, but only the contents of the methods above will be
	 * considered during marking.
	 */
	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0) {
			try {
				s = new Scanner(new File(args[0]));
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		} else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of integers:\n");
		}
		List<Integer> inputList = new ArrayList<Integer>();

		int v;
		while (s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputList.add(v);

		s.close();
		System.out.printf("Read %d values.\n", inputList.size());

		long startTime = System.currentTimeMillis();

		ChunkMergesort mergesort = new ChunkMergesort();
		List<Integer> sorted = mergesort.chunkMergesort(inputList);

		long endTime = System.currentTimeMillis();
		double totalTimeSeconds = (endTime - startTime) / 1000.0;

		System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
		System.out.println(sorted);
		
		try {
			mergesort.report();
			System.out.println("Report written to comparisons.txt");
		} catch (IOException e) {
			System.out.println("Unable to write file comparisons.txt");
			return;
		}
	}

}
