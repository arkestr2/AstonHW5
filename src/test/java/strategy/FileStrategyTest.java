package strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileStrategyTest {

    @TempDir
    Path tempDir;

    @Test
    void parsesAllLines() throws IOException {
        Path file = write(
                "Матрица;боевик;1999",
                " Титаник ; драма ; 1997 ",
                "Начало;фантастика;2010");

        var result = new FileStrategy(file, 10).create();

        assertEquals(3, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("боевик", result.get(0).getGenre());
        assertEquals(1999, result.get(0).getReleaseYear());
        assertEquals("Титаник", result.get(1).getName());
        assertEquals("драма", result.get(1).getGenre());
        assertEquals(1997, result.get(1).getReleaseYear());
        assertEquals("Начало", result.get(2).getName());
    }

    @Test
    void skipsBlankLinesWithoutCountingTowardLimit() throws IOException {
        Path file = write(
                "Матрица;боевик;1999",
                "",
                "   ",
                "Титаник;драма;1997");

        var result = new FileStrategy(file, 2).create();

        assertEquals(2, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("Титаник", result.get(1).getName());
    }

    @Test
    void limitTakesFirstNLines() throws IOException {
        Path file = write(
                "Фильм 1;жанр;1991",
                "Фильм 2;жанр;1992",
                "Фильм 3;жанр;1993",
                "Фильм 4;жанр;1994",
                "Фильм 5;жанр;1995");

        var result = new FileStrategy(file, 2).create();

        assertEquals(2, result.size());
        assertEquals("Фильм 1", result.get(0).getName());
        assertEquals("Фильм 2", result.get(1).getName());
    }

    @Test
    void unlimitedConstructorReadsAllLines() throws IOException {
        Path file = write(
                "Фильм 1;жанр;1991",
                "Фильм 2;жанр;1992",
                "Фильм 3;жанр;1993",
                "Фильм 4;жанр;1994",
                "Фильм 5;жанр;1995");

        var result = new FileStrategy(file).create();

        assertEquals(5, result.size());
    }

    @Test
    void wrongFieldCountThrowsWithContext() throws IOException {
        Path file = write("Матрица;боевик");

        var e = assertThrows(IllegalArgumentException.class,
                () -> new FileStrategy(file, 10).create());

        assertTrue(e.getMessage().contains(file.getFileName().toString()),
                "сообщение должно содержать имя файла: " + e.getMessage());
    }

    @Test
    void nonNumericYearThrows() throws IOException {
        Path file = write("Матрица;боевик;abc");

        assertThrows(IllegalArgumentException.class,
                () -> new FileStrategy(file, 10).create());
    }

    @Test
    void builderInvalidYearThrows() throws IOException {
        Path file = write("Матрица;боевик;1000");

        assertThrows(IllegalArgumentException.class,
                () -> new FileStrategy(file, 10).create());
    }

    @Test
    void missingFileThrowsIllegalState() {
        Path missing = tempDir.resolve("нет_такого.txt");

        assertThrows(IllegalStateException.class,
                () -> new FileStrategy(missing, 10).create());
    }

    @Test
    void emptyFileThrowsIllegalState() throws IOException {
        Path empty = write("");

        assertThrows(IllegalStateException.class,
                () -> new FileStrategy(empty).create());
    }

    private Path write(String... lines) throws IOException {
        Path file = tempDir.resolve("movies.txt");
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }
}