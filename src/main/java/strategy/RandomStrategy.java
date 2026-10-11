package strategy;

import model.MovieCollection;

import java.util.Collections;
import java.util.stream.Collectors;

public class RandomStrategy implements MovieCollectionCreationStrategy {

    private final MovieCollectionCreationStrategy source;
    private final int size;

    public RandomStrategy(MovieCollectionCreationStrategy source, int size) {
        this.source = source;
        this.size = size;
    }

    @Override
    public MovieCollection create() {
        MovieCollection movies = source.create();
        Collections.shuffle(movies);

        return movies.stream()
                .limit(size)
                .collect(Collectors.toCollection(MovieCollection::new));
    }
}
