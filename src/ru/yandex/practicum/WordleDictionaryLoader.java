package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String filename) {
        WordleDictionary wordleDictionary = new WordleDictionary();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));) {
            while (reader.ready()) {
                String word = reader.readLine().toLowerCase().replace("ё","e");
                if (word.length() == 5) {
                    wordleDictionary.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            log.write("Файл не найден.\n");
        } catch (IOException e) {
            log.write("Ошибка ввода/вывода.\n");
        }
        return wordleDictionary;
    }
}
