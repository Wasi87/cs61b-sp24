package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;

public class FileRead {
    private HashMap<Integer, Set<String>> synsets; // id-words
    private HashMap<String, Set<Integer>> wordToIds; // word-ids
    private DirectedAcyclicGraph dag; // id-graph

    public FileRead() {
        synsets = new HashMap<>();
        wordToIds = new HashMap<>();
        dag = new DirectedAcyclicGraph();
    }

    public void readSynsets(String filename) {
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] splitLine = in.readLine().split(",");
            int id = Integer.parseInt(splitLine[0]);
            String[] words = splitLine[1].split(" ");
            Set<String> wordSet = new TreeSet<>(Arrays.asList(words));
            synsets.put(id, wordSet);
            dag.addNode(id);

            for (String word : words) {
                wordToIds.computeIfAbsent(word, k -> new HashSet<>()).add(id);
            }
        }
    }

    public void readHyponyms(String filename) {
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] splitLine = in.readLine().split(",");
            int upperId = Integer.parseInt(splitLine[0]);

            for (int i = 1; i < splitLine.length; i++) {
                int lowerId = Integer.parseInt(splitLine[i]);
                dag.addEdge(upperId, lowerId);
            }
        }
    }

    public DirectedAcyclicGraph getDag() {
        return dag;
    }
    public HashMap<Integer, Set<String>> getSynsets() {
        return synsets;
    }
    public HashMap<String, Set<Integer>> getWordToIds() {
        return wordToIds;
    }
}
