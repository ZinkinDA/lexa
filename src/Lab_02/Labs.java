package Lab_02;

/** Реализовать внешнюю сортировку **/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Labs {

    public static void main(String[] args) {
        Scanner scanner = null;
        int size = 0;
        File file = new File("input.txt");
        File fileOutput = new File("output.txt");
        File fileResult = new File("result.txt");
        Integer[] list = null;

        /** Инициализация сканера и размера файла и считывание из файла **/
        try {
            scanner = new Scanner(file);
            size = scanner.nextLine().split(" ").length;
            scanner = new Scanner(file);
            list = new Integer[size / 2];
            for (int i = 0; i < size / 2; i++) {
                list[i] = scanner.nextInt();
            }
            fileOutput.createNewFile();

        } catch (IOException e) {
            System.out.println("Файл input.txt не существует");
            System.exit(200);
        }
        /** Запись в файл output.txt первой половины текста **/
        try (FileWriter fileWriter = new FileWriter(fileOutput)) {
            sort(list);
            for (var el : list) {
                fileWriter.write(el + " ");
            }
        } catch (IOException e) {
            System.err.println("ОШИБКА ЗАПИСИ В ФАЙЛЫ!");
            System.exit(300);
        }

        /** Записать в массив вторую половину текста **/
        for (int i = 0; scanner.hasNextInt(); i++) {
            list[i] = scanner.nextInt();
        }

        sort(list); // Сортируем 1 половину текста

        List<Integer> integerList = new ArrayList<>(List.of(list)); // Преобразуем в список
        try (FileWriter fileWriter = new FileWriter(fileResult)) {
            scanner = new Scanner(fileOutput);
            /**Идем по первому массиву **/
            while (scanner.hasNextInt()) {
                int num = scanner.nextInt(); // Текущее число из 1  половины
                int count = 0;
                /** Обход по второму массиву **/
                while (integerList.size() != 0) { // Текущее число из буфера обмена.
                    if (num > integerList.get(0)) {

                        fileWriter.write(integerList.get(0) + " ");
                        integerList.remove(0);

                    } else if (num == integerList.get(0)) {

                        fileWriter.write(integerList.get(0) + " "); // Запись и удаление из динамического массива
                        integerList.remove(0);
                    } else  {
                        break;
                    }
                }
                fileWriter.write(num + " "); // Запись числа в конец.
            }
        } catch (IOException e) {
            System.exit(400);
        }
        fileOutput.deleteOnExit(); // Удаление ненужных  файлов
        scanner.close();// Закрытие сканера
    }

    /** Пузырьковая сортировка для целочисленного массива **/
    static void sort(Integer[] arr) {
        int n = arr.length;
        /*
            Используем стримы для создания пузырьковой сортировки
         */
        IntStream.range(0, n - 1)
                .flatMap(i -> IntStream.range(1, n - i))
                .forEach(j -> {
                    // Условие для пузырьковой сортировки
                    if (arr[j - 1] > arr[j]) {
                        int temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                    }
                });
    }
}
