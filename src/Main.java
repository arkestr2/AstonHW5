import model.MovieCollection;
import strategy.MovieCollectionCreationStrategy;
import strategy.FileStrategy;
import strategy.MovieCollectionCreator;
import strategy.RandomStrategy;

void main() {
    MovieCollectionCreationStrategy strategy = new RandomStrategy(new FileStrategy("internal_movies.txt"));
    MovieCollectionCreator creator = new MovieCollectionCreator(strategy);

    MovieCollection movieCollection = creator.create();
    movieCollection.stream().forEach(System.out::println);

//    MovieCollectionCreationStrategy strategy = new FileStrategy("user_movies.txt");
//    MovieCollectionCreator creator = new MovieCollectionCreator(strategy);
//
//    MovieCollection movieCollection = creator.create();
//    movieCollection.stream().forEach(System.out::println);

//    Movie movie1 = new Movie.Builder()
//            .name("A")
//            .genre("Хоррор")
//            .releaseYear(1991)
//            .build();
//
//    Movie movie2 = new Movie.Builder()
//            .name("B")
//            .genre("Комедия")
//            .releaseYear(2002)
//            .build();
//
//    Movie movie3 = new Movie.Builder()
//            .name("C")
//            .genre("Драма")
//            .releaseYear(1968)
//            .build();
//
//    MovieCollection movieCollection = Stream.of(movie1, movie2, movie3)
//            .collect(Collectors.toCollection(MovieCollection::new));
//
//    System.out.println("----------------Unsorted----------------");
//    movieCollection.stream().forEach(System.out::println);
//
//    System.out.println("----------------Sorted by only even year----------------");
//    movieCollection.sortByOnlyEvenYear();
//    movieCollection.stream().forEach(System.out::println);
//
//    System.out.println("----------------Sorted by year----------------");
//    movieCollection.sort(Comparator.comparingInt(Movie::getReleaseYear));
//    movieCollection.stream().forEach(System.out::println);
//
//    System.out.println("----------------Sorted by name----------------");
//    movieCollection.sort(Comparator.comparing(Movie::getName));
//    movieCollection.stream().forEach(System.out::println);
//
//    System.out.println("----------------Sorted by genre----------------");
//    movieCollection.sort(Comparator.comparing(Movie::getGenre));
//    movieCollection.stream().forEach(System.out::println);
}