package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary;

    public SpellCorrector(){
        dictionary = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner sc = new Scanner(file);

        while(sc.hasNext()){
            sc.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
            String nextWord = sc.next();

            dictionary.add(nextWord);
        }

        sc.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String word = inputWord.toLowerCase();
        Set<String> suggDistance1 = new TreeSet<>();

        if(dictionary.find(word) != null){
            return word;
        }

        runAllEditMethods(inputWord, suggDistance1);
        String finalSuggestion = compareSuggestions(suggDistance1);

        if(finalSuggestion == null){
            Set<String> suggDistance2 = createNewSet(suggDistance1);
            finalSuggestion = compareSuggestions(suggDistance2);
        }

        return finalSuggestion;

    }

    private void runAllEditMethods(String word, Set<String> suggestions){
        deletion(word, suggestions);
        transposition(word, suggestions);
        alteration(word, suggestions);
        insertion(word, suggestions);
    }

    private void deletion(String word, Set<String> suggestions){
        for(int i  = 0; i < word.length(); i++){
            StringBuilder temp = new StringBuilder(word);

            temp.deleteCharAt(i);

            suggestions.add(temp.toString());
        }
    }

    private void transposition(String word, Set<String> suggestions){
        for(int i = 0; i < word.length()-1; i++){
            StringBuilder temp = new StringBuilder(word);

            char char1 = temp.charAt(i);
            char char2 = temp.charAt(i+1);

            temp.deleteCharAt(i);
            temp.insert(i, char2);

            temp.deleteCharAt(i+1);
            temp.insert(i+1, char1);

            suggestions.add(temp.toString());
        }
    }

    private void alteration(String word, Set<String> suggestions){
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        for(int i = 0; i < word.length(); i++){
            for(char c : alphabet){
                if(word.charAt(i) != c){
                    StringBuilder temp = new StringBuilder(word);

                    temp.deleteCharAt(i);
                    temp.insert(i, c);

                    suggestions.add(temp.toString());
                }
            }
        }
    }

    private void insertion(String word, Set<String> suggestions){
        char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        for(int i = 0; i < word.length()+1; i++){
            for(char c : alphabet){
                StringBuilder temp  =  new StringBuilder(word);

                temp.insert(i, c);

                suggestions.add(temp.toString());
            }
        }
    }

    private String compareSuggestions(Set<String> suggestions){
        int max = 0;
        String suggestion = null;
        for(String s : suggestions){
            if(dictionary.find(s) != null) {
                if (dictionary.find(s).getValue() > max) {
                    suggestion = s;
                    max = dictionary.find(s). getValue();
                }
            }
        }

        return suggestion;
    }

    private Set<String> createNewSet(Set<String> suggestions){
        Set<String> newSuggestions  = new TreeSet<>();
        for(String s : suggestions){
            runAllEditMethods(s, newSuggestions);
        }

        return newSuggestions;
    }
}
