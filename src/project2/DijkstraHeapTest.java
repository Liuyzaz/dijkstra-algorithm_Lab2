package project2;

import java.util.*;

public class DijkstraHeapTest{

    // Class to represent an edge in the graph
    static class Edge {
        int vertex, weight;

        public Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    // Dijkstra's Algorithm implementation using a min-heap (priority queue)
    public static Result dijkstraHeap(List<List<Edge>> graph, int source) {
        int V = graph.size();  // Number of vertices
        int[] dist = new int[V];  // Distance array to store shortest distances
        boolean[] visited = new boolean[V];  // Visited array to track visited nodes
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));  // Min-heap

        // Initialize distances to infinity (represented by Integer.MAX_VALUE) and source to 0
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Initialize the heap with the source vertex
        minHeap.add(new Edge(source, 0));

        int comparisonCount = 0;  // Initialize comparison count

        while (!minHeap.isEmpty()) {
            // Pop the vertex with the smallest distance from the heap
            Edge current = minHeap.poll();
            int u = current.vertex;

            if (visited[u]) {
                continue;  // Skip already visited nodes
            }

            visited[u] = true;  // Mark this node as visited

            // Explore the neighbors of vertex u
            for (Edge neighbor : graph.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (!visited[v]) {
                    comparisonCount++;  // Count this comparison

                    int newDistance = dist[u] + weight;

                    // If a shorter path is found
                    if (newDistance < dist[v]) {
                        dist[v] = newDistance;  // Update the shortest distance
                        minHeap.add(new Edge(v, newDistance));  // Push the updated distance and vertex to the heap
                    }
                }
            }
        }

        return new Result(dist, comparisonCount);  // Return distances and comparison count
    }

    // Helper class to store the result
    static class Result {
        int[] distances;
        int comparisonCount;

        public Result(int[] distances, int comparisonCount) {
            this.distances = distances;
            this.comparisonCount = comparisonCount;
        }
    }

    public static void main(String[] args) {
        // Example graph in adjacency list form
        List<List<Edge>> graph = new ArrayList<>();

        // Initialize the graph
        graph.add(Arrays.asList(new Edge(1, 1), new Edge(2, 4)));  // Neighbors of vertex 0
        graph.add(Arrays.asList(new Edge(0, 1), new Edge(2, 4), new Edge(3, 2), new Edge(4, 7)));  // Neighbors of vertex 1
        graph.add(Arrays.asList(new Edge(0, 4), new Edge(1, 4), new Edge(3, 3), new Edge(4, 5)));  // Neighbors of vertex 2
        graph.add(Arrays.asList(new Edge(1, 2), new Edge(2, 3), new Edge(4, 4), new Edge(5, 6)));  // Neighbors of vertex 3
        graph.add(Arrays.asList(new Edge(1, 7), new Edge(2, 5), new Edge(3, 4), new Edge(5, 7)));  // Neighbors of vertex 4
        graph.add(Arrays.asList(new Edge(3, 6), new Edge(4, 7)));  // Neighbors of vertex 5

        int source = 0;  // Source vertex
        Result result = dijkstraHeap(graph, source);

        // Output the shortest distances
        System.out.println("Shortest distances from source vertex " + source + ":");
        for (int i = 0; i < result.distances.length; i++) {
            System.out.println("To vertex " + i + ": " + result.distances[i]);
        }

        // Output the number of comparisons
        System.out.println("Number of comparisons: " + result.comparisonCount);
    }
}
