package ru.yandex.practicum;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
       try (PrintWriter log = new PrintWriter("log.txt");) {
           try {
               WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(log);
               WordleDictionary dictionary = wordleDictionaryLoader.load("words_ru.txt");
               int steps = 6;
               WordleGame game = new WordleGame(dictionary.generateRandomWord(), steps, dictionary, log);
               game.run();
           } catch (Exception e) {
               log.write("Неизвестная ошибка: " + e.getMessage());
           }
       } catch (FileNotFoundException e) {
           throw new RuntimeException();
       }
    }

}
