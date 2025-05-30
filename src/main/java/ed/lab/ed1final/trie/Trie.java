package ed.lab.ed1final.trie; //Kevin Palencia

import java.util.HashMap;
import java.util.Map;

public class Trie {

    private static class Node {
        Map<Character, Node> children = new HashMap<>();
        int wordsEndingHere = 0;
        int wordsThroughHere = 0;
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            current.wordsThroughHere++;
            current = current.children.computeIfAbsent(c, k -> new Node());
        }
        current.wordsThroughHere++;
        current.wordsEndingHere++;
    }

    public int countWordsEqualTo(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) return 0;
            current = current.children.get(c);
        }
        return current.wordsEndingHere;
    }

    public int countWordsStartingWith(String prefix) {
        Node current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) return 0;
            current = current.children.get(c);
        }
        return current.wordsThroughHere;
    }

    public void erase(String word) {
        if (countWordsEqualTo(word) == 0) return;

        Node current = root;
        for (char c : word.toCharArray()) {
            current.wordsThroughHere--;
            current = current.children.get(c);
        }
        current.wordsThroughHere--;
        current.wordsEndingHere--;
    }
}
