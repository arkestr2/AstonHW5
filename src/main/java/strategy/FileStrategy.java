package strategy;

import model.Movie;
import model.MovieCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStrategy implements MovieCollectionCreationStrategy {

    private final Path filePath;

    public FileStrategy(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public MovieCollection create() {
        try (Stream<String> stream = Files.lines(filePath)) {
            return stream
                    .filter(line -> !line.isBlank())
                    .map(line -> {
                        String[] fields = line.split(";");
                        if (fields.length != 3) {
                            throw new IllegalArgumentException("Некорректная строка в файле %s: %s".formatted(filePath, line));
                        }

                        try {
                            return new Movie.Builder()
                                    .name(fields[0].trim())
                                    .genre(fields[1].trim())
                                    .releaseYear(Integer.parseInt(fields[2].trim()))
                                    .build();
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Год должен быть числом (файл: %s, строка: %s)".formatted(filePath, line));
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException(e.getMessage() + " (файл: %s, строка: %s)".formatted(filePath, line));
                        }
                    })
                    .collect(Collectors.toCollection(MovieCollection::new));
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать фильмы из файла: " + filePath, e);
        }
    }
}
