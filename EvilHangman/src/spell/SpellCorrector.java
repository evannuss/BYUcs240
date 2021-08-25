package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector{

    private Trie dictionary;

    public SpellCorrector(){
        dictionary = new Trie();
    }
    
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while(scanner.hasNext()){
            String nextWord = scanner.next();
            dictionary.add(nextWord);
        }
        scanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Set<String> suggestionsDistance1 = new TreeSet<>();
        if(dictionary.find(inputWord) != null){
            return inputWord.toLowerCase();
        }

        runAllEditMethods(inputWord, suggestionsDistance1);
        String finalSuggestion = compareSuggestions(suggestionsDistance1);

        if(finalSuggestion == null) {
            Set<String> suggestionsDistance2 = createDis2Suggestions(suggestionsDistance1);
            finalSuggestion = compareSuggestions(suggestionsDistance2);
        }

        return finalSuggestion;
    }

    private void runAllEditMethods(String input, Set<String> suggestions){
        deletion(input, suggestions);
        transposition(input, suggestions);
        alteration(input, suggestions);
        insertion(input, suggestions);
    }

    private void deletion(String input, Set<String> suggestions){
        for(int i = 0; i < input.length(); i++){
            StringBuilder temp = new StringBuilder(input);

            temp.deleteCharAt(i);

            suggestions.add(temp.toString());
        }
    }

    private void transposition(String input, Set<String> suggestions){
        char tempChar1;
        char tempChar2;
        for(int i = 0; i < input.length()-1; i++){
            StringBuilder temp = new StringBuilder();
            temp.append(input);

            tempChar1 = input.charAt(i);
            tempChar2 = input.charAt(i+1);

            temp.setCharAt(i, tempChar2);
            temp.setCharAt(i+1, tempChar1);

            suggestions.add(temp.toString());
        }
    }

    private void alteration(String input, Set<String> suggestions){
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l',
                'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        for(int i = 0; i < input.length(); i++){
            for (char c : alphabet) {
                StringBuilder temp = new StringBuilder();
                temp.append(input);

                if (c != temp.charAt(i)) {
                    temp.setCharAt(i, c);

                    suggestions.add(temp.toString());
                }
            }
        }
    }

    private void insertion(String input, Set<String> suggestions){
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l',
                'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        for(int i = 0; i < input.length()+1; i++){
            for (char c : alphabet) {
                StringBuilder temp = new StringBuilder();
                temp.append(input);

                temp.insert(i, c);

                suggestions.add(temp.toString());
            }
        }
    }

    private String compareSuggestions(Set<String> suggestions){
        int max = 0;
        String suggestion = null;
        for(String str : suggestions){
            if(dictionary.find(str) != null) {
                TrieNode tempNode = dictionary.find(str);
                if (tempNode.getValue() > max) {
                    max = tempNode.getValue();
                    suggestion = str;
                }
            }
        }
        return suggestion;
    }

    private Set<String> createDis2Suggestions(Set<String> suggestions){
        Set<String> suggestionsDistance2 = new TreeSet<>();
        for(String str : suggestions){
            runAllEditMethods(str, suggestionsDistance2);
        }
        return suggestionsDistance2;
    }

}

