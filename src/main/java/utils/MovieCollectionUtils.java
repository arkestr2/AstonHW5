package utils;

import model.Movie;
import model.MovieCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MovieCollectionUtils {

    private MovieCollectionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void saveToFile(MovieCollection movies, Path filePath) {
        try {
            List<String> lines = movies.stream()
                    .map(Movie::toString)
                    .toList();
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить фильмы в файл: " + filePath, e);
        }
    }
}
