package strategy;

import model.Movie;
import model.MovieCollection;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ManualStrategy implements MovieCollectionCreationStrategy {

    private final Scanner scanner;
    private final int size;

    public ManualStrategy(Scanner scanner, int size) {
        this.scanner = scanner;
        this.size = size;
    }

    @Override
    public MovieCollection create() {
        return IntStream.range(0, size)
                .mapToObj(i -> askMovie(i + 1))
                .collect(Collectors.toCollection(MovieCollection::new));
    }

    private Movie askMovie(int index) {
        System.out.printf("%nФильм #%d%n", index);
        String name = askNonEmptyString("Название: ");
        String genre = askNonEmptyString("Жанр: ");
        while (true) {
            int year = askInt("Год выпуска: ");
            try {
                return new Movie.Builder()
                        .name(name)
                        .genre(genre)
                        .releaseYear(year)
                        .build();
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте снова.");
            }
        }
    }

    private String askNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Значение не может быть пустым. Попробуйте снова.");
        }
    }

    private int askInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Нужно ввести целое число. Попробуйте снова.");
            }
        }
    }
}