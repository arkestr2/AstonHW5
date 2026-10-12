package model;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieCollectionTest {

    private static final Comparator<Movie> BY_NAME = Comparator.comparing(Movie::getName);
    private static final Comparator<Movie> BY_GENRE = Comparator.comparing(Movie::getGenre);
    private static final Comparator<Movie> BY_YEAR = Comparator.comparingInt(Movie::getReleaseYear);

    private static final Movie MATRIX = build("Матрица", "Боевик", 1999);
    private static final Movie TITANIC = build("Титаник", "Мелодрама", 1997);
    private static final Movie INTERSTELLAR = build("Интерстеллар", "Фантастика", 2014);
    private static final Movie INCEPTION = build("Начало", "Фантастика", 2010);

    @Test
    void sortOrdersByName() {
        MovieCollection collection = new MovieCollection(MATRIX, TITANIC, INTERSTELLAR, INCEPTION);

        collection.sort(BY_NAME);

        assertEquals(List.of(INTERSTELLAR, MATRIX, INCEPTION, TITANIC), collection);
    }

    @Test
    void sortOrdersByGenre() {
        MovieCollection collection = new MovieCollection(MATRIX, TITANIC, INTERSTELLAR, INCEPTION);

        collection.sort(BY_GENRE);

        assertEquals(List.of(MATRIX, TITANIC, INTERSTELLAR, INCEPTION), collection);
    }

    @Test
    void sortOrdersByYear() {
        MovieCollection collection = new MovieCollection(MATRIX, TITANIC, INTERSTELLAR, INCEPTION);

        collection.sort(BY_YEAR);

        assertEquals(List.of(TITANIC, MATRIX, INCEPTION, INTERSTELLAR), collection);
    }

    @Test
    void sortKeepsSizeAndElements() {
        MovieCollection collection = new MovieCollection(MATRIX, TITANIC, INTERSTELLAR, INCEPTION);

        collection.sort(BY_YEAR);

        assertEquals(4, collection.size());
        assertTrue(collection.containsAll(List.of(MATRIX, TITANIC, INTERSTELLAR, INCEPTION)));
    }

    @Test
    void sortIsStableForEqualYears() {
        Movie movieA = build("А", "Жанр", 2000);
        Movie movieB = build("Б", "Жанр", 2000);
        MovieCollection collection = new MovieCollection(movieA, MATRIX, movieB);

        collection.sort(BY_YEAR);

        assertEquals(List.of(MATRIX, movieA, movieB), collection);
    }

    @Test
    void sortOnEmptyDoesNotThrow() {
        MovieCollection collection = new MovieCollection();

        assertDoesNotThrow(() -> collection.sort(BY_YEAR));
        assertTrue(collection.isEmpty());
    }

    @Test
    void sortOnSingleElementDoesNotThrow() {
        MovieCollection collection = new MovieCollection(MATRIX);

        assertDoesNotThrow(() -> collection.sort(BY_YEAR));
        assertEquals(List.of(MATRIX), collection);
    }

    @Test
    void oddYearMoviesStayInPlace() {
        MovieCollection collection = new MovieCollection(MATRIX, INTERSTELLAR, TITANIC, INCEPTION);

        collection.sortByOnlyEvenYear();

        assertEquals(MATRIX, collection.get(0));
        assertEquals(TITANIC, collection.get(2));
    }

    @Test
    void evenYearMoviesSortedAtTheirPositions() {
        MovieCollection collection = new MovieCollection(MATRIX, INTERSTELLAR, INCEPTION, TITANIC);

        collection.sortByOnlyEvenYear();

        assertEquals(INCEPTION, collection.get(1));
        assertEquals(INTERSTELLAR, collection.get(2));
    }

    @Test
    void countOccurrencesAsyncCountsDuplicates() {
        MovieCollection collection = new MovieCollection(MATRIX, TITANIC, MATRIX, MATRIX);

        assertEquals(3, collection.countOccurrencesAsync(MATRIX).join());
        assertEquals(1, collection.countOccurrencesAsync(TITANIC).join());
    }

    @Test
    void countOccurrencesAsyncReturnsZeroForAbsentElement() {
        MovieCollection collection = new MovieCollection(MATRIX, TITANIC);

        assertEquals(0, collection.countOccurrencesAsync(INTERSTELLAR).join());
    }

    @Test
    void countOccurrencesAsyncOnEmptyCollection() {
        MovieCollection collection = new MovieCollection();

        assertEquals(0, collection.countOccurrencesAsync(MATRIX).join());
    }

    @Test
    void countOccurrencesAsyncSplitsCollectionsAboveChunkSize() {
        MovieCollection collection = new MovieCollection();
        for (int i = 0; i < 10; i++) {
            collection.add(i % 3 == 0 ? MATRIX : TITANIC);
        }

        assertEquals(4, collection.countOccurrencesAsync(MATRIX).join());
        assertEquals(6, collection.countOccurrencesAsync(TITANIC).join());
    }

    private static Movie build(String name, String genre, int year) {
        return new Movie.Builder()
                .name(name)
                .genre(genre)
                .releaseYear(year)
                .build();
    }
}