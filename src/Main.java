void main() {
    Movie movie = new Movie.Builder()
            .name("Молчание Ягнят")
            .genre("Хоррор")
            .releaseYear(1991)
            .build();

    System.out.println(movie.getName());
}