package model;

import java.time.LocalDate;
import java.util.Objects;

public final class Movie {
    private final String name;
    private final String genre;
    private final int releaseYear;

    public Movie(Builder builder) {
        this.name = builder.name;
        this.genre = builder.genre;
        this.releaseYear = builder.releaseYear;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public static class Builder {
        private String name;
        private String genre;
        private int releaseYear;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder releaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Movie build() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Название фильма это обязательное поле");
            }

            if (genre == null || genre.isBlank()) {
                throw new IllegalArgumentException("Жанр фильма это обязательное поле");
            }

            if (releaseYear <= 1895) {
                throw new IllegalArgumentException("Год выпуска фильма не может быть раньше первого в истории фильма");
            } else if (releaseYear > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("Год выпуска фильма не может быть в будущем");
            }

            return new Movie(this);
        }
    }

    @Override
    public String toString() {
        return "%s;%s;%s".formatted(name, genre, releaseYear);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Movie movie) {
            return Objects.equals(name, movie.name)
                    && Objects.equals(genre, movie.genre)
                    && releaseYear == movie.releaseYear;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, releaseYear);
    }
}
