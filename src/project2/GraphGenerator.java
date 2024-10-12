package project2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    // Method to generate a random graph with V vertices and E edges in adjacency matrix format
    public static int[][] generateGraphMatrix(int V, int E) {
        int[][] graph = new int[V][V];
        Random rand = new Random();
        int edgeCount = 0;

        // Generate random edges
        while (edgeCount < E) {
            int u = rand.nextInt(V);  // Random vertex u
            int v = rand.nextInt(V);  // Random vertex v

            if (u != v && graph[u][v] == 0) {  // Ensure no self-loop and no duplicate edges
                int weight = rand.nextInt(10) + 1;  // Random weight between 1 and 10
                graph[u][v] = weight;
                graph[v][u] = weight;  // For undirected graph
                edgeCount++;
            }
        }
        return graph;
    }

    // Method to generate a random graph with V vertices and E edges in adjacency list format
    public static List<List<int[]>> generateGraphList(int V, int E) {
        List<List<int[]>> adjList = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }

        Random rand = new Random();
        int edgeCount = 0;

        // Generate random edges
        while (edgeCount < E) {
            int u = rand.nextInt(V);  // Random vertex u
            int v = rand.nextInt(V);  // Random vertex v

            if (u != v && adjList.get(u).stream().noneMatch(e -> e[0] == v)) {  // Ensure no self-loop and no duplicate edges
                int weight = rand.nextInt(10) + 1;  // Random weight between 1 and 10
                adjList.get(u).add(new int[]{v, weight});
                adjList.get(v).add(new int[]{u, weight});  // For undirected graph
                edgeCount++;
            }
        }
        return adjList;
    }

    // Method to save the graph in adjacency matrix format to a file
    public static void saveGraphMatrix(int[][] graph, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        int V = graph.length;

        writer.write(V + "\n");  // Write the number of vertices
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                writer.write(graph[i][j] + " ");
            }
            writer.write("\n");
        }
        writer.close();
    }

    // Method to save the graph in adjacency list format to a file
    public static void saveGraphList(List<List<int[]>> adjList, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        int V = adjList.size();

        writer.write(V + "\n");  // Write the number of vertices
        for (int i = 0; i < V; i++) {
            writer.write(i + ": ");
            for (int[] edge : adjList.get(i)) {
                writer.write("(" + edge[0] + ", " + edge[1] + ") ");
            }
            writer.write("\n");
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        // Define the range and step for V values
        int startV = 100;
        int endV = 5000;
        int step = 100;

        // Calculate the number of steps for the array size
        int arraySize = (endV - startV) / step + 1;
        int[] V_values = new int[arraySize];

        // Fill the V_values array with the range of values
        for (int i = 0; i < arraySize; i++) {
            V_values[i] = startV + (i * step);
        }

        // Generate and save sparse and dense graphs for both adjacency matrix and adjacency list formats
        System.out.println("Generating graphs in both matrix and list format:");

        for (int V : V_values) {
            // Sparse graph (V-1 edges)
            int E_sparse = 2*V;

            // Generate and save the graph in matrix format
            int[][] matrixSparse = generateGraphMatrix(V, E_sparse);
            String matrixFilename = "sparse2V_matrix_graph_" + V + "_" + E_sparse + ".txt";
            saveGraphMatrix(matrixSparse, matrixFilename);
            System.out.println("Sparse2V matrix graph with " + V + " vertices and " + E_sparse + " edges saved to " + matrixFilename);

            // Generate and save the graph in list format
            List<List<int[]>> listSparse = generateGraphList(V, E_sparse);
            String listFilename = "sparse2V_list_graph_" + V + "_" + E_sparse + ".txt";
            saveGraphList(listSparse, listFilename);
            System.out.println("Sparse2V list graph with " + V + " vertices and " + E_sparse + " edges saved to " + listFilename);

//            // Dense graph (V*(V-1)/2 edges)
//            int E_dense = V * (V - 1) / 2;
//
//            // Generate and save the graph in matrix format
//            int[][] matrixDense = generateGraphMatrix(V, E_dense);
//            String matrixDenseFilename = "dense_matrix_graph_" + V + "_" + E_dense + ".txt";
//            saveGraphMatrix(matrixDense, matrixDenseFilename);
//            System.out.println("Dense matrix graph with " + V + " vertices and " + E_dense + " edges saved to " + matrixDenseFilename);
//
//            // Generate and save the graph in list format
//            List<List<int[]>> listDense = generateGraphList(V, E_dense);
//            String listDenseFilename = "dense_list_graph_" + V + "_" + E_dense + ".txt";
//            saveGraphList(listDense, listDenseFilename);
//            System.out.println("Dense list graph with " + V + " vertices and " + E_dense + " edges saved to " + listDenseFilename);
        }
    }
}
