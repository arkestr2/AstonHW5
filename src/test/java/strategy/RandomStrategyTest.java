package strategy;

import model.Movie;
import model.MovieCollection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyTest {

    private static final List<Movie> POOL_MOVIES = List.of(
            buildMovie("Фильм 1", "жанр", 1991), buildMovie("Фильм 2", "жанр",1992),
            buildMovie("Фильм 3", "жанр",1993), buildMovie("Фильм 4", "жанр",1994),
            buildMovie("Фильм 5", "жанр", 1995), buildMovie("Фильм 6", "жанр",1996),
            buildMovie("Фильм 7", "жанр",1997), buildMovie("Фильм 8", "жанр",1998));

    private static final MovieCollectionCreationStrategy POOL =
            () -> new MovieCollection(POOL_MOVIES.toArray(Movie[]::new));

    @Test
    void returnsRequestedSize() {
        MovieCollection result = new RandomStrategy(POOL, 5).create();

        assertEquals(5, result.size());
    }

    @Test
    void resultIsSubsetOfSource() {
        MovieCollection result = new RandomStrategy(POOL, 5).create();

        assertTrue(POOL_MOVIES.containsAll(result),
                "все элементы должны приходить из пула");
    }

    @Test
    void sizeLargerThanPoolReturnsWholePool() {
        MovieCollection result = new RandomStrategy(POOL, 50).create();

        assertEquals(POOL_MOVIES.size(), result.size());
        assertTrue(POOL_MOVIES.containsAll(result));
    }

    private static Movie buildMovie(String name, String genre, int year) {
        return new Movie.Builder()
                .name(name)
                .genre(genre)
                .releaseYear(year)
                .build();
    }
}