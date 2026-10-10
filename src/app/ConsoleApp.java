package app;

import model.Movie;
import model.MovieCollection;
import strategy.FileStrategy;
import strategy.MovieCollectionCreator;
import strategy.RandomStrategy;
import utils.MovieCollectionUtils;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Scanner;

public class ConsoleApp {

    private ConsoleApp() {
        throw new UnsupportedOperationException();
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static MovieCollection movieCollection;
    private static Path outputFilePath;

    public static void run() {
        System.out.println("""
            
            Приветствуем в приложении по созданию и сортировке коллекций фильмов!
        
            Возможности:
                1. Создавать свою коллекцию фильмов тремя способами:
                    1.1 Из текстового файла, строки которого заполнены в формате: [Название фильма];[Жанр фильма];[Год выпуска фильма]
                    1.2 Заполнить случайными фильмами из нашей внутренней коллекции
                    1.3 [WIP] Ввести фильмы по очереди вручную
        
                2. Сортировать созданную коллекцию четырьмя способами:
                    2.1 В алфавитном порядке по названию
                    2.2 В алфавитном порядке по жанру
                    2.3 В натуральном порядке по году выпуска
                    2.4 В натуральном порядке по году выпуска ТОЛЬКО фильмы с четными годами. С нечетными - останутся на месте
        
                3. [WIP] Подсчитать количество вхождений элемента в коллекции
        
                4. Сохранить созданную коллекцию в текстовый файл
        
            Хотите начать? (Y/N) (по умолчанию: Y)
            """);

        String input = scanner.nextLine().trim().toLowerCase();

        while (input.isEmpty() || input.equals("y")) {
            outputFilePath = null;
            movieCollection = null;

            if (!tryAskCollectionCreationStrategy())
                return;

            System.out.println("\n");
            System.out.println("Отлично! Ваша коллекция фильмов успешно создана:");
            movieCollection.forEach(System.out::println);
            System.out.println("\n");

            if (!tryAskSortCollection()){
                return;
            }

            System.out.println("\n");
            System.out.println("Отлично! Ваша коллекция фильмов после успешного этапа сортировки:");
            movieCollection.forEach(System.out::println);
            System.out.println("\n");

            if (!tryAskCountElement()) {
                return;
            }

            if (!tryAskSaveToFile()) {
                return;
            }

            if (outputFilePath != null) {
                System.out.println("\n");
                System.out.printf("Отлично! Ваша коллекция фильмов успешно сохранена в файл: %s%n", outputFilePath);
                movieCollection.forEach(System.out::println);
                System.out.println("\n");
            }

            System.out.println("Хотите создать еще одну коллекцию? (Y/N) (default: Y)");
            input = scanner.nextLine().trim().toLowerCase();
        }

        System.out.println("Спасибо, что воспользовались нашим приложением! Заходите ещё!");
    }

    private static boolean tryAskCollectionCreationStrategy() {
        while (true) {
            System.out.println("""
                    1. Выберите способ создания коллекции (0-3):
                        1. Из текстового файла, строки которого заполнены в формате: [Название фильма];[Жанр фильма];[Год выпуска фильма]
                        2. Заполнить случайными фильмами из нашей внутренней коллекции
                        3. [WIP] Ввести фильмы по очереди вручную
            
                        0. Выйти
                    """);

            MovieCollectionCreator creator = new MovieCollectionCreator();

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1":
                        System.out.println("Введите название файла (без расширения), который будет использован для создания коллекции");
                        input = scanner.nextLine().trim();
                        creator.setStrategy(new FileStrategy(Path.of("data","%s.txt".formatted(input))));
                        movieCollection = creator.create();
                        return true;
                    case "2":
                        creator.setStrategy(new RandomStrategy(new FileStrategy(Path.of("data", "internal_movies.txt"))));
                        movieCollection = creator.create();
                        return true;
                    case "3":
                        System.out.println("WIP");
                        break;
                    case "0":
                        return false;
                    default:
                        System.out.println("Похоже, вы ввели неподходящий ответ. Попробуйте снова");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean tryAskSortCollection() {
        while (true) {
            System.out.println("""            
                2. Выберите способ сортировки коллекции (0-4):
                    1. В алфавитном порядке по названию
                    2. В алфавитном порядке по жанру
                    3. В натуральном порядке по году выпуска
                    4. В натуральном порядке по году выпуска ТОЛЬКО фильмы с четными годами. С нечетными - останутся на месте
                    5. Не сортировать
                
                    0. Выйти
                """);

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    movieCollection.sort(Comparator.comparing(Movie::getName));
                    return true;
                case "2":
                    movieCollection.sort(Comparator.comparing(Movie::getGenre));
                    return true;
                case "3":
                    movieCollection.sort(Comparator.comparingInt(Movie::getReleaseYear));
                    return true;
                case "4":
                    movieCollection.sortByOnlyEvenYear();
                    return true;
                case "5":
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("Похоже, вы ввели неподходящий ответ. Попробуйте снова");
                    break;
            }
        }
    }

    private static boolean tryAskCountElement() {
        while (true) {
            System.out.println("""
                    3. [WIP] Подсчитать количество вхождений элемента в коллекции? (Y/N) (по умолчанию: N)
                        0. Выйти
                    """);

            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "y":
                    //WIP
                    return true;
                case "":
                case "n":
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("Похоже, вы ввели неподходящий ответ. Попробуйте снова");
                    break;
            }
        }
    }

    private static boolean tryAskSaveToFile() {
        while (true) {
            System.out.println("""            
                    4. Сохранить созданную коллекцию в текстовый файл? (Y/N) (по умолчанию: Y)
                        0. Выйти
                    """);

            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "":
                case "y":
                    System.out.println("Введите название файла (без расширения), в который нужно сохранить коллекцию");
                    input = scanner.nextLine().trim();
                    outputFilePath = Path.of("data", "%s.txt".formatted(input));
                    MovieCollectionUtils.saveToFile(movieCollection, outputFilePath);
                    return true;
                case "n":
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("Похоже, вы ввели неподходящий ответ. Попробуйте снова");
                    break;
            }
        }
    }
}
