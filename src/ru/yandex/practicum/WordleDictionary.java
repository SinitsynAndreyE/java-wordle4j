package ru.yandex.practicum;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;

    public WordleDictionary() {
        this.words = new ArrayList<>();
    }

    public void add(String word) {
        words.add(word);
    }

    public String generateRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getWords() {
        return words;
    }

    public WordleDictionary leaveCompatibleWords(Set<Character> incompatibleCharacters, Set<Character> compatibleCharacters, Map<Character, Integer> sameCharacters) {
        List<String> wordsToRemove = new ArrayList<>();
        for (String word : words) {
            char[] wordArray = word.toCharArray();
            for (char wordChar : wordArray) {
                if (incompatibleCharacters.contains(wordChar)) {
                    wordsToRemove.add(word);
                    break;
                }
            }
            for (Character character : compatibleCharacters) {
                if (!word.contains(character.toString())) {
                    wordsToRemove.add(word);
                    break;
                }
            }
            for (Map.Entry<Character, Integer> entry : sameCharacters.entrySet()) {
                if (word.charAt(entry.getValue()) != entry.getKey()) {
                    wordsToRemove.add(word);
                    break;
                }
            }
        }
        words.removeAll(wordsToRemove);
        return this;
    }
}
