package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectedAcyclicGraph {
    private HashMap<Integer, List<Integer>> adjList;
    private HashMap<Integer, List<Integer>> reverseAdjList;

    public DirectedAcyclicGraph() {
        adjList = new HashMap<>();
        reverseAdjList = new HashMap<>();
    }

    public void addNode(int node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        reverseAdjList.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
    }

    public Set<Integer> traverseFrom(int start) {
        Set<Integer> visited = new HashSet<>();
        traverse(start, visited);
        return visited;
    }

    private void traverse(int current, Set<Integer> visited) {
        if (visited.contains(current)) {
            return;
        }

        visited.add(current);
        if (adjList.containsKey(current)) {
            for (int neighbor : adjList.get(current)) {
                traverse(neighbor, visited);
            }
        }
    }
    
    public Set<Integer> traverseTo(int start) {
        Set<Integer> visited = new HashSet<>();
        traverseReverse(start, visited);
        return visited;        
    }

    private void traverseReverse(int current, Set<Integer> visited) {
        if (visited.contains(current)) {
            return;
        }

        visited.add(current);
        if (reverseAdjList.containsKey(current)) {
            for (int neighbor : reverseAdjList.get(current)) {
                traverseReverse(neighbor, visited);
            }
        }
    }
}
