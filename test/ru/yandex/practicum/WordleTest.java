package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    @Test
    public void testWordleDictionoryLoaderFileNotFound() {
        try (PrintWriter log = new PrintWriter("testLog.txt");) {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(log);
            BufferedReader reader = new BufferedReader(new FileReader("testLog.txt"));
            wordleDictionaryLoader.load("notFound.txt");
            log.close();
            Assertions.assertEquals("Файл не найден.", reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWordleDictionoryLoaderSize4159() {
        try (PrintWriter log = new PrintWriter("testLog.txt");) {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(log);
            WordleDictionary wordleDictionary = wordleDictionaryLoader.load("words_ru.txt");
            Assertions.assertEquals(4159, wordleDictionary.getWords().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWordleDictionaryAddWord() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.add("Тест");
        Assertions.assertEquals(List.of("Тест"), dictionary.getWords());
    }

    @Test
    public void testWordleDictionaryLeaveCompatibleWords() {
        WordleDictionary dictionary = new WordleDictionary();
        dictionary.add("Тест");
        Set<Character> incompatibleCharacters = new HashSet<>(List.of('е'));
        dictionary = dictionary.leaveCompatibleWords(incompatibleCharacters, new HashSet<>(), new HashMap<>());
        Assertions.assertEquals(Collections.emptyList(), dictionary.getWords());
    }

    @Test
    public void testWordleGameNot5Charaters() {
        String simulatedInput = "Тест\nexit";
        try (PrintWriter log = new PrintWriter("testLog.txt");) {
            // Подменяем системный ввод нашей строкой
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            WordleDictionary dictionary = new WordleDictionary();
            WordleGame wordleGame = new WordleGame("Тест", 6, dictionary, log);
            wordleGame.run();
            log.close();
            BufferedReader reader = new BufferedReader(new FileReader("testLog.txt"));
            Assertions.assertEquals("Слово должно состоять из 5 символов", reader.readLine());
        } catch (Exception e) {

        }
    }

    @Test
    public void testWordleGameWordNotFound() {
        String simulatedInput = "Тестт\nexit";
        try (PrintWriter log = new PrintWriter("testLog.txt");) {
            // Подменяем системный ввод нашей строкой
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            WordleDictionary dictionary = new WordleDictionary();
            WordleGame wordleGame = new WordleGame("Тест", 6, dictionary, log);
            wordleGame.run();
            log.close();
            BufferedReader reader = new BufferedReader(new FileReader("testLog.txt"));
            Assertions.assertEquals("Такого слова нет в словаре.", reader.readLine());
        } catch (Exception e) {

        }
    }

    @Test
    public void testWordleGameGetCompareStringCorrectAnswer() {
        String simulatedInput = "Тестт\nexit";
        try (PrintWriter log = new PrintWriter("testLog.txt");) {
            // Подменяем системный ввод нашей строкой
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            WordleDictionary dictionary = new WordleDictionary();
            dictionary.add("тестт");
            WordleGame wordleGame = new WordleGame("тестт", 6, dictionary, log);
            Assertions.assertEquals("+++++", wordleGame.getCompareString("тестт"));
        } catch (Exception e) {

        }
    }
}
