package spell;

public class TrieNode implements INode{

    private TrieNode[] children;
    private int count;

    public TrieNode(){
        children  = new TrieNode[26];
        count = 0;
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public TrieNode[] getChildren() {
        return children;
    }
}
