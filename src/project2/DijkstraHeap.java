package project2;

import java.io.*;
import java.util.*;

// Class to represent the graph and run Dijkstra using an adjacency list with a minimizing heap (priority queue)
public class DijkstraHeap {

    // Node class to represent vertices and their associated weights in the adjacency list
    static class Node implements Comparable<Node> {
        int vertex, weight;
        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        // Comparator for the priority queue based on the weight (minimizing)
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    // Dijkstra's Algorithm implementation using an adjacency list and priority queue (min-heap)
    public static Result dijkstraList(List<List<Node>> graph, int source) {
        int V = graph.size();
        int[] dist = new int[V];  // Distance array
        boolean[] visited = new boolean[V];  // Visited vertices

        // Initialize distances to "infinity" (max value) and source to 0
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Min-heap priority queue to select the vertex with the smallest distance
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (visited[u]) {
                continue;
            }

            visited[u] = true;

            // Loop through neighbors of u (adjacency list)
            for (Node neighbor : graph.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        return new Result(dist);  // Return distances and comparison count
    }

    // Helper class to store results
    public static class Result {
        int[] distances;

        public Result(int[] distances) {
            this.distances = distances;
        }
    }

 // Method to load the graph from a file (adjacency list format)
    public static List<List<Node>> loadGraphList(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int V = Integer.parseInt(scanner.nextLine().trim());  // Read the number of vertices
        List<List<Node>> graph = new ArrayList<>();

        // Initialize adjacency list for each vertex
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        // Read each line in the file and populate the adjacency list
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] parts = line.split(":");
                int u = Integer.parseInt(parts[0].trim()); // Vertex ID
                if (parts.length > 1) {
                    String[] edges = parts[1].trim().split("\\) "); // Split edges
                    for (String edgeStr : edges) {
                        edgeStr = edgeStr.replace("(", "").replace(")", "");
                        String[] edge = edgeStr.split(", ");
                        int v = Integer.parseInt(edge[0]);  // Neighbor vertex
                        int weight = Integer.parseInt(edge[1]);  // Weight of the edge
                        graph.get(u).add(new Node(v, weight));
                    }
                }
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
        String sparseCSV = "sparse2V_graph_results_heap.csv";
        //String denseCSV = "dense_graph_results_heap.csv";

        // Scenario 1: Testing sparse graphs (V-1 edges)
        System.out.println("Testing sparse graphs (V-1 edges):");
        for (int V : V_values) {
            int E_sparse = 2*V;  // Sparse graph has V-1 edges
            String filename = "sparse2V_list_graph_" + V + "_" + E_sparse + ".txt"; // Matching the file names generated
            List<List<Node>> graph = loadGraphList(filename); // Load the graph from adjacency list file

            // Start timer to measure Dijkstra's algorithm runtime
            long startTime = System.nanoTime();
            
            // Run Dijkstra's algorithm using adjacency list with heap
            Result result = dijkstraList(graph, 0);
            
            // End timer
            long endTime = System.nanoTime();
            
            // Calculate execution time (in seconds)
            double executionTime = (endTime - startTime) / 1_000_000_000.0;

            // Log results to CSV for sparse graph
            logToCSV(sparseCSV, V, E_sparse, executionTime);

            // Print results to console
            System.out.println("Sparse2V graph with " + V + " vertices and " + E_sparse + " edges:");
            System.out.println("Execution time: " + executionTime + " seconds");
            System.out.println("----------------------------------------------------------------");
        }

//        // Scenario 2: Testing dense graphs (V*(V-1)/2 edges)
//        System.out.println("\nTesting dense graphs (V*(V-1)/2 edges):");
//        for (int V : V_values) {
//            int E_dense = V * (V - 1) / 2;  // Dense graph has V*(V-1)/2 edges
//            String filename = "dense_list_graph_" + V + "_" + E_dense + ".txt"; // Matching the file names generated
//            List<List<Node>> graph = loadGraphList(filename); // Load the graph from adjacency list file
//
//            // Start timer to measure Dijkstra's algorithm runtime
//            long startTime = System.nanoTime();
//            
//            // Run Dijkstra's algorithm using adjacency list with heap
//            Result result = dijkstraList(graph, 0);
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
//            System.out.println("Dense graph with " + V + " vertices and " + E_dense + " edges:");
//            System.out.println("Execution time: " + executionTime + " seconds");
//            System.out.println("----------------------------------------------------------------");
//       }
    }
}
