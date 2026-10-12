package strategy;

import model.MovieCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ManualStrategyTest {

    private final ByteArrayOutputStream captured = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void captureStdout() {
        originalOut = System.out;
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void readsRequestedNumberOfMovies() {
        Scanner scanner = new Scanner("""
                Матрица
                боевик
                1999
                Титаник
                драма
                1997
                """);

        MovieCollection result = new ManualStrategy(scanner, 2).create();

        assertEquals(2, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("боевик", result.get(0).getGenre());
        assertEquals(1999, result.get(0).getReleaseYear());
        assertEquals("Титаник", result.get(1).getName());
        assertEquals(1997, result.get(1).getReleaseYear());
    }

    @Test
    void retriesOnInvalidYearOnly() {
        Scanner scanner = new Scanner("""
                Матрица
                боевик
                3000
                1999
                """);

        MovieCollection result = new ManualStrategy(scanner, 1).create();

        assertEquals(1, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("боевик", result.get(0).getGenre());
        assertEquals(1999, result.get(0).getReleaseYear());
        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("Ошибка"),
                "пользователь должен увидеть сообщение об ошибке");
    }

    @Test
    void retriesOnNonNumericYear() {
        Scanner scanner = new Scanner("""
                Матрица
                боевик
                abc
                1999
                """);

        MovieCollection result = new ManualStrategy(scanner, 1).create();

        assertEquals(1, result.size());
        assertEquals(1999, result.get(0).getReleaseYear());
    }

    @Test
    void retriesOnEmptyName() {
        Scanner scanner = new Scanner("""

                Матрица
                боевик
                1999
                """);

        MovieCollection result = new ManualStrategy(scanner, 1).create();

        assertEquals(1, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("боевик", result.get(0).getGenre());
        assertEquals(1999, result.get(0).getReleaseYear());
    }

    @Test
    void retriesOnEmptyGenre() {
        Scanner scanner = new Scanner("""
                Матрица
                
                боевик
                1999
                """);

        MovieCollection result = new ManualStrategy(scanner, 1).create();

        assertEquals(1, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("боевик", result.get(0).getGenre());
        assertEquals(1999, result.get(0).getReleaseYear());
    }

    @Test
    void retriesOnEmptyYear() {
        Scanner scanner = new Scanner("""
                Матрица
                боевик
                
                1999
                """);

        MovieCollection result = new ManualStrategy(scanner, 1).create();

        assertEquals(1, result.size());
        assertEquals("Матрица", result.get(0).getName());
        assertEquals("боевик", result.get(0).getGenre());
        assertEquals(1999, result.get(0).getReleaseYear());
    }

    @Test
    void eofThrowsNoSuchElement() {
        Scanner scanner = new Scanner("");

        assertThrows(NoSuchElementException.class,
                () -> new ManualStrategy(scanner, 1).create());
    }
}