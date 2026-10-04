import model.Movie;
import model.MovieCollection;

void main() {
    Movie movie = new Movie.Builder()
            .name("Молчание Ягнят")
            .genre("Хоррор")
            .releaseYear(1991)
            .build();

    MovieCollection<Movie> movieCollection = new MovieCollection<Movie>();
    movieCollection.add(movie);

    System.out.println(movieCollection.get(0).getName());
}