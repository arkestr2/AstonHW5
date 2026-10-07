package strategy;

import model.Movie;
import model.MovieCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStrategy implements CollectionCreationStrategy {

    private final String filePath;

    public FileStrategy(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public MovieCollection create() {
        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            return stream.map(line -> line.split(";"))
                    .map(fields -> new Movie.Builder()
                            .name(fields[0])
                            .genre(fields[1])
                            .releaseYear(Integer.parseInt(fields[2]))
                            .build())
                    .collect(Collectors.toCollection(MovieCollection::new));
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать фильмы из файла: " + filePath, e);
        }
    }
}
