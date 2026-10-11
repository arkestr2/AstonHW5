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
        assertEquals("Матрица;боевик;1999", build("Матрица", "боевик", 1999).toString());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class, () -> build("", "боевик", 1999));
        assertThrows(IllegalArgumentException.class, () -> build("   ", "боевик", 1999));
        assertThrows(IllegalArgumentException.class, () -> build(null, "боевик", 1999));
    }

    @Test
    void rejectsBlankGenre() {
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", "", 1999));
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", "   ", 1999));
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", null, 1999));
    }

    @Test
    void rejectsYearBeforeFirstFilm() {
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", "боевик", 0));
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", "боевик", 1894));
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", "боевик", Integer.MIN_VALUE));
    }

    @Test
    void rejectsFutureYear() {
        assertThrows(IllegalArgumentException.class, () -> build("Матрица", "боевик", CURRENT_YEAR + 1));
    }

    @Test
    void acceptsBoundaryYears() {
        assertEquals(1895, build("Матрица", "боевик", 1895).getReleaseYear());
        assertEquals(CURRENT_YEAR, build("Матрица", "боевик", CURRENT_YEAR).getReleaseYear());
    }

    @Test
    void equalMoviesAreEqual() {
        Movie first = build("Матрица", "боевик", 1999);
        Movie second = build("Матрица", "боевик", 1999);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentMoviesAreNotEqual() {
        assertNotEquals(
                build("Матрица", "боевик", 1999),
                build("Матрица", "боевик", 2000)
        );

        assertNotEquals(
                build("Титаник", "боевик", 1999),
                build("Матрица", "боевик", 1999)
        );

        assertNotEquals(
                build("Матрица", "комедия", 1999),
                build("Матрица", "боевик", 1999)
        );
    }

    private static Movie build(String name, String genre, int year) {
        return new Movie.Builder()
                .name(name)
                .genre(genre)
                .releaseYear(year)
                .build();
    }
}