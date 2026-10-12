package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private static final int CURRENT_YEAR = LocalDate.now().getYear();

    @Test
    void builderCarriesFieldsToGetters() {
        Movie movie = new Movie.Builder()
                .name("Матрица")
                .genre("боевик")
                .releaseYear(1999)
                .build();

        assertEquals("Матрица", movie.getName());
        assertEquals("боевик", movie.getGenre());
        assertEquals(1999, movie.getReleaseYear());
    }

    @Test
    void toStringUsesFileFormat() {
        assertEquals("Матрица;боевик;1999", buildMovie("Матрица", "боевик", 1999).toString());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> buildMovie("", "боевик", 1999));
        assertThrows(IllegalArgumentException.class, () -> buildMovie("   ", "боевик", 1999));
        assertThrows(IllegalArgumentException.class, () -> buildMovie(null, "боевик", 1999));
    }

    @Test
    void rejectsBlankGenre() {
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", "", 1999));
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", "   ", 1999));
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", null, 1999));
    }

    @Test
    void rejectsYearBeforeFirstFilm() {
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", "боевик", 0));
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", "боевик", 1894));
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", "боевик", Integer.MIN_VALUE));
    }

    @Test
    void rejectsFutureYear() {
        assertThrows(IllegalArgumentException.class, () -> buildMovie("Матрица", "боевик", CURRENT_YEAR + 1));
    }

    @Test
    void acceptsBoundaryYears() {
        assertEquals(1895, buildMovie("Матрица", "боевик", 1895).getReleaseYear());
        assertEquals(CURRENT_YEAR, buildMovie("Матрица", "боевик", CURRENT_YEAR).getReleaseYear());
    }

    @Test
    void equalMoviesAreEqual() {
        Movie first = buildMovie("Матрица", "боевик", 1999);
        Movie second = buildMovie("Матрица", "боевик", 1999);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentMoviesAreNotEqual() {
        assertNotEquals(
                buildMovie("Матрица", "боевик", 1999),
                buildMovie("Матрица", "боевик", 2000)
        );

        assertNotEquals(
                buildMovie("Титаник", "боевик", 1999),
                buildMovie("Матрица", "боевик", 1999)
        );

        assertNotEquals(
                buildMovie("Матрица", "комедия", 1999),
                buildMovie("Матрица", "боевик", 1999)
        );
    }

    private static Movie buildMovie(String name, String genre, int year) {
        return new Movie.Builder()
                .name(name)
                .genre(genre)
                .releaseYear(year)
                .build();
    }
}