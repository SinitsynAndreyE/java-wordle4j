package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    private final PrintWriter log;

    private List<Character> incompatibleCharacters;

    private List<Character> compatibleCharacters;

    private Map<Character, Integer> sameCharacters;

    public WordleGame(String answer, int steps, WordleDictionary dictionary, PrintWriter log) {
        this.answer = answer;
        this.steps = steps;
        this.dictionary = dictionary;
        this.log = log;
        incompatibleCharacters = new ArrayList<>();
        compatibleCharacters = new ArrayList<>();
        sameCharacters = new HashMap<>();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите exit для выхода");
        while(steps > 0) {
            try {
                System.out.println("Введите слово:");
                String word = scanner.nextLine();
                word = word.toLowerCase().replace("ё", "е");
                if (word.equals("exit")) {
                    break;
                }
                if (word.isEmpty()) {
                    word = generateTip(dictionary);
                    System.out.println(word);
                }
                if (word.length() != 5) {
                    System.out.println("Слово должно состоять из 5 символов");
                    throw new NoSuchLength("Слово должно состоять из 5 символов");
                } else if (!dictionary.getWords().contains(word)) {
                    System.out.println("Такого слова нет в словаре.");
                    throw new WordNotFoundInDictionary("Такого слова нет в словаре.");
                } else if (word.equals(answer)) {
                    System.out.println("Победа! Загаданное слово: " + answer);
                    break;
                } else {
                    steps--;
                    System.out.println(getCompareString(word));
                    dictionary = dictionary.leaveCompatibleWords(incompatibleCharacters, compatibleCharacters, sameCharacters);
                    incompatibleCharacters.clear();
                    compatibleCharacters.clear();
                    sameCharacters.clear();
                }
            } catch (WordNotFoundInDictionary e) {
                log.write(e.getMessage() + "\n");
            } catch (NoSuchLength e) {
                log.write(e.getMessage() + "\n");
            } catch (Exception e) {
                log.write("Неизвестная ошибка: " + e.getMessage());
            }
        }
        if (steps == 0) {
            System.out.println("Проигрыш. Загаданное слово: " + answer);
        }
    }

    public String generateTip(WordleDictionary dictionary) {
        Random random = new Random();
        List<String> words = dictionary.getWords();
        return words.get(random.nextInt(words.size()));
    }

    public String getCompareString(String word) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < answer.length(); i++) {
            int index = answer.indexOf(word.charAt(i));
            if (index == -1) {
                builder.append("-");
                incompatibleCharacters.add(word.charAt(i));
            } else if (index == i) {
                builder.append("+");
                sameCharacters.put(word.charAt(i), i);
            } else {
                builder.append("^");
                compatibleCharacters.add(word.charAt(i));
            }
        }
        return builder.toString();
    }
}
