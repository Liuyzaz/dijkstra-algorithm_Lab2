# Dijkstra Algorithm Lab 2
**Dijkstra’s Algorithm Analysis and Implementation**

---

## Overview
This project explores the implementation and analysis of Dijkstra's algorithm using two different graph representations and priority queue structures. The goal is to evaluate the impact of these choices on the algorithm's time complexity both theoretically and empirically.

---

## Objectives
### Graph Representations:
- **Sparse Graph**: Minimal-Connected Graph (E = V - 1)
- **Dense Graph**: Complete Graph (E = V(V - 1)/2)

### Implementation Settings:
1. **Adjacency Matrix + Array-Based Priority Queue**
2. **Adjacency List + Min-Heap Priority Queue**

### Analysis:
- Measure and compare time complexities of the two implementations with respect to the number of vertices (|V|) and edges (|E|).
- Test and analyze performance across sparse and dense graphs.

---

## Key Features
- **Sparse and Dense Graphs**:
  - Sparse: Few neighbors per vertex (E = V - 1).
  - Dense: Each vertex connects to almost every other vertex (E = V(V - 1)/2).

- **Implementation Comparison**:
  - Adjacency matrix with array priority queue.
  - Adjacency list with min-heap priority queue.

- **Performance Metrics**:
  - Theoretical and empirical analysis of time complexity.
  - Suitable implementation for specific graph types and scenarios.

---

## Results
The project highlights the trade-offs between the two implementations and determines their suitability under varying graph densities.

---

## Technologies
- **Programming Languages**: Java, Python
- **Libraries**: Matplotlib (for graph generation and visualization)
- **Empirical Testing**: Time complexity and performance metrics

---
