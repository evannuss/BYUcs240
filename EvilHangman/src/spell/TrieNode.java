package spell;

import java.util.Arrays;

public class TrieNode implements INode {

    private static final int ALPHABET_SIZE = 26;
    private TrieNode[] children;
    private int count;


    public TrieNode() {
        children = new TrieNode[ALPHABET_SIZE];
        Arrays.fill(children, null);
        count = 0;
    }

    @Override
    public int getValue() { return count; }

    @Override
    public void incrementValue() { count++; }

    @Override
    public TrieNode[] getChildren() { return children; }

}
