package spell;

public class Trie implements ITrie {

    private int wordCount;
    private int nodeCount;
    private TrieNode root;

    public Trie(){
        wordCount = 0;
        nodeCount = 1;
        root = new TrieNode();
    }

    @Override
    public void add(String word) {
        String newWord = word.toLowerCase();
        TrieNode prevChild = root;

        for(int i = 0; i < newWord.length(); i++){
            char currChar = newWord.charAt(i);
            int currIndex = currChar - 'a';

            if(prevChild.getChildren()[currIndex] == null){
                nodeCount++;
                prevChild.getChildren()[currIndex] = new TrieNode();
            }

            prevChild = prevChild.getChildren()[currIndex];
        }

        if(prevChild.getValue() == 0){
            wordCount++;
        }

        prevChild.incrementValue();
    }

    @Override
    public TrieNode find(String word) {
        String newWord = word.toLowerCase();
        TrieNode prevChild = root;

        for(int i = 0; i < newWord.length(); i++){
            char currChar = newWord.charAt(i);
            int currIndex = currChar - 'a';
            TrieNode currChild = prevChild.getChildren()[currIndex];

            if(currChild == null){
                return null;
            }

            if(i == newWord.length()-1 && currChild.getValue() != 0){
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
    public int hashCode() {
        return (nodeCount * wordCount + calculateHashSum());
    }

    private int calculateHashSum(){
        int sum  = 0;
        for(int i = 0; i < root.getChildren().length; i++){
            if(root.getChildren()[i] != null){
                sum += i;
            }
        }

        return sum;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }

        Trie newObj = (Trie) obj;

        if((this.getNodeCount() != newObj.getNodeCount()) || (this.getWordCount() != newObj.getWordCount())){
            return false;
        }

        return compareChildren(this.root, newObj.root);
    }

    private boolean compareChildren(TrieNode n1, TrieNode n2){
        if(n1.getValue() != n2.getValue()){
            return false;
        }

        for(int i = 0; i < n1.getChildren().length; i++){
            if(n1.getChildren()[i] != null && n2.getChildren()[i] != null) {
                if (!compareChildren(n1.getChildren()[i], n2.getChildren()[i])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();

        createStringOutput(root, currWord, output);

        return output.toString();
    }

    private void createStringOutput(TrieNode n, StringBuilder currWord, StringBuilder output){
        if(n.getValue() > 0){
            output.append(currWord);
            output.append("\n");
        }

        for(int i = 0; i < n.getChildren().length; i++){
            if(n.getChildren()[i] != null){
                char currLetter = (char) ('a' + i);
                currWord.append(currLetter);

                createStringOutput(n.getChildren()[i], currWord, output);

                currWord.deleteCharAt(currWord.length()-1);
            }
        }
    }






}
