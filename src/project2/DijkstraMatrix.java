package project2;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DijkstraMatrix {

    // Dijkstra's Algorithm implementation using an adjacency matrix
    public static Result dijkstraMatrix(int[][] graph, int source) {
        int V = graph.length;
        int[] dist = new int[V];  // Distance array
        boolean[] visited = new boolean[V];  // Visited vertices
        
        // Initialize distances to "infinity" (max value) and source to 0
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        
        for (int i = 0; i < V; i++) {
            // Find the unvisited vertex with the smallest distance
            int u = -1;
            int minDistance = Integer.MAX_VALUE;
            
            for (int j = 0; j < V; j++) {
                if (!visited[j] && dist[j] < minDistance) {
                    minDistance = dist[j];
                    u = j;
                }
            }
            
            if (u == -1) {  // All reachable nodes processed
                break;
            }
            
            visited[u] = true;
            
            // Loop through neighbors of u
            for (int v = 0; v < V; v++) {
                if (graph[u][v] > 0 && !visited[v]) {
                    int newDistance = dist[u] + graph[u][v];
                    if (newDistance < dist[v]) {
                        dist[v] = newDistance;
                    }
                }
            }
        }
        
        return new Result(dist);  // Return distances 
    }

    // Helper class to store results
    public static class Result {
        int[] distances;
        
        public Result(int[] distances) {
            this.distances = distances;
        }
    }

    // Method to load the graph from a file
    public static int[][] loadGraph(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int V = scanner.nextInt();
        int[][] graph = new int[V][V];
        
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
        return graph;
    }

    // Method to log results into CSV
    public static void logToCSV(String filename, int V, int E, double executionTime) throws IOException {
        FileWriter csvWriter = new FileWriter(filename, true); // Append mode
        csvWriter.append(V + "," + E + "," + executionTime + "\n");
        csvWriter.flush();
        csvWriter.close();
    }

    public static void main(String[] args) throws IOException {
        // Vertex Sizes (V): Specific sizes matching the graph generator
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

        // File paths to store results
        String sparseCSV = "sparse2V_matrix_graph_results.csv";
        //String denseCSV = "dense_matrix_graph_results.csv";

        // Scenario 1: Testing sparse graphs (V-1 edges)
        System.out.println("Testing sparse graphs (V-1 edges):");
        for (int V : V_values) {
            int E_sparse = 2*V;  // Sparse graph has V-1 edges
            String filename = "sparse2V_matrix_graph_" + V + "_" + E_sparse + ".txt"; // Matching the file names generated
            int[][] graph = loadGraph(filename); // Load the graph from file

            // Start timer to measure Dijkstra's algorithm runtime
            long startTime = System.nanoTime();
            
            // Run Dijkstra's algorithm
            Result result = dijkstraMatrix(graph, 0);
            
            // End timer
            long endTime = System.nanoTime();
            
            // Calculate execution time (in seconds)
            double executionTime = (endTime - startTime) / 1_000_000_000.0;

            // Log results to CSV for sparse graph
            logToCSV(sparseCSV, V, E_sparse, executionTime);

            // Print results to console
            System.out.println("Sparse2V matrix graph with " + V + " vertices and " + E_sparse + " edges:");
            System.out.println("Execution time: " + executionTime + " seconds");
            System.out.println("----------------------------------------------------------------");
        }

//        // Scenario 2: Testing dense graphs (V*(V-1)/2 edges)
//        System.out.println("\nTesting dense graphs (V*(V-1)/2 edges):");
//        for (int V : V_values) {
//            int E_dense = V * (V - 1) / 2;  // Dense graph has V*(V-1)/2 edges
//            String filename = "dense_matrix_graph_" + V + "_" + E_dense + ".txt"; // Matching the file names generated
//            int[][] graph = loadGraph(filename); // Load the graph from file
//
//            // Start timer to measure Dijkstra's algorithm runtime
//            long startTime = System.nanoTime();
//            
//            // Run Dijkstra's algorithm
//            Result result = dijkstraMatrix(graph, 0);
//            
//            // End timer
//            long endTime = System.nanoTime();
//            
//            // Calculate execution time (in seconds)
//            double executionTime = (endTime - startTime) / 1_000_000_000.0;
//
//            // Log results to CSV for dense graph
//            logToCSV(denseCSV, V, E_dense, executionTime);
//
//            // Print results to console
//            System.out.println("Dense matrix graph with " + V + " vertices and " + E_dense + " edges:");
//            System.out.println("Execution time: " + executionTime + " seconds");
//            System.out.println("----------------------------------------------------------------");
//        }
    }
}

