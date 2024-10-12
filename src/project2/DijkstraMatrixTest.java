package project2;

import java.util.Arrays;

public class DijkstraMatrixTest{

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
        
        return new Result(dist);  // Return distances and comparison count
    }
    
    // Helper class to store results
    public static class Result {
        int[] distances;
      
        
        public Result(int[] distances) {
            this.distances = distances;
            
        }
    }
    
    public static void main(String[] args) {
        // Example graph in adjacency matrix form (small for testing)
        int[][] graph = {
            {0, 1, 4, 0, 0, 0},
            {1, 0, 4, 2, 7, 0},
            {4, 4, 0, 3, 5, 0},
            {0, 2, 3, 0, 4, 6},
            {0, 7, 5, 4, 0, 7},
            {0, 0, 0, 6, 7, 0}
        };
        
        int source = 0;

        // Start timer
        long startTime = System.currentTimeMillis();
        
        // Run Dijkstra's algorithm
        Result result = dijkstraMatrix(graph, source);
        
        // End timer
        long endTime = System.currentTimeMillis();
        
        // Calculate execution time
        long executionTime = endTime - startTime;
        
        // Print results
        System.out.println("Shortest distances from source:");
        System.out.println(Arrays.toString(result.distances));
        System.out.println("Execution time: " + executionTime + " milliseconds");
    }
}
