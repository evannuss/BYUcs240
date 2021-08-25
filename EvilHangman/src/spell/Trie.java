package spell;

public class Trie implements ITrie {

    private int wordCount;
    private int nodeCount;
    private TrieNode root;

    public Trie() {
        wordCount = 0;
        nodeCount = 1;
        root = new TrieNode();
    }

    @Override
    public void add(String word) {
        String newWord = word.toLowerCase();
        TrieNode prevChild = root;

        for (int i = 0; i < newWord.length(); i++) {
            char currChar = newWord.charAt(i);
            int currIndex = currChar - 'a';

            if (prevChild.getChildren()[currIndex] == null) {
                nodeCount++;
                prevChild.getChildren()[currIndex] = new TrieNode();
            }

            prevChild = prevChild.getChildren()[currIndex];
        }
        if(prevChild.getValue() == 0) {
            wordCount++;
        }
        prevChild.incrementValue();
    }

    @Override
    public TrieNode find(String word) {
        String newWord = word.toLowerCase();
        TrieNode prevChild = root;

        for (int i = 0; i < newWord.length(); i++) {
            char currChar = newWord.charAt(i);
            int currIndex = currChar - 'a';
            TrieNode currChild = prevChild.getChildren()[currIndex];

            if (currChild == null) {
                return null;
            }
            if (i == (newWord.length() - 1) && (currChild.getValue() != 0)) {
                return currChild;
            }

            prevChild = currChild;
        }

        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        createStringOutput(root, currWord, output);

        return output.toString();
    }

    @Override
    public int hashCode() {
        return (nodeCount * wordCount + calculateHashSum());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }

        if (obj == this) { return true; }

        if (this.getClass() != obj.getClass()) { return false; }

        Trie newObj = (Trie) obj;

        if((this.getWordCount() != newObj.getWordCount()) || (this.getNodeCount() != newObj.getNodeCount())){ return false; }

        return compareChildren(this.root, newObj.root);
    }

    private void createStringOutput(TrieNode n, StringBuilder currWord, StringBuilder output){
        if(n.getValue() > 0){
            output.append(currWord.toString());
            output.append("\n");
        }

        for(int i = 0; i < n.getChildren().length; i++){
            TrieNode currChild = n.getChildren()[i];

            if(currChild != null){
                char childLetter = (char) ('a' + i);
                currWord.append(childLetter);

                createStringOutput(currChild, currWord, output);

                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    private int calculateHashSum() {
        int sum = 0;

        for (int i = 0; i < root.getChildren().length; i++) {
            if (root.getChildren()[i] != null) {
                sum += i;
            }
        }

        return sum;
    }

    private boolean compareChildren(TrieNode n1, TrieNode n2){
        if(n1.getValue() != n2.getValue()){ return false; }

        for(int i = 0; i < n1.getChildren().length; i++){
            TrieNode n1Child = n1.getChildren()[i];
            TrieNode n2Child = n2.getChildren()[i];

            if((n1Child != null) && (n2Child != null)){
                if(!compareChildren(n1Child, n2Child)){
                    return false;
                }
            }
        }

        return true;
    }
}
