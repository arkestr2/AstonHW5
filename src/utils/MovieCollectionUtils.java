package utils;

import model.Movie;
import model.MovieCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MovieCollectionUtils {

    public static void saveToFile(MovieCollection movies, String filePath) {
        try {
            List<String> lines = movies.stream()
                    .map(Movie::toString)
                    .toList();
            Files.write(Path.of(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось сохранить фильмы в файл: " + filePath, e);
        }
    }
}
