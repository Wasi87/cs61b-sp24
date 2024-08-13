package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkerframework.checker.units.qual.K;
import org.eclipse.jetty.util.component.Graceful;

import edu.princeton.cs.algs4.In;

public class DirectedAcyclicGraph {
    private HashMap<Integer, List<Integer>> adjList;

    public DirectedAcyclicGraph() {
        adjList = new HashMap<>();
    }

    public void addNode(int node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
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
    
    public boolean containsNode(int node) {
        return adjList.containsKey(node);
    }
}
