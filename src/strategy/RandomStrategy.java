package strategy;

import model.MovieCollection;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomStrategy implements MovieCollectionCreationStrategy {

    private final MovieCollectionCreationStrategy source;

    public RandomStrategy(MovieCollectionCreationStrategy source) {
        this.source = source;
    }

    @Override
    public MovieCollection create() {
        MovieCollection movies = source.create();
        Collections.shuffle(movies);

        Random random = new Random();
        return movies.stream()
                .limit(random.nextInt(3, 7))
                .collect(Collectors.toCollection(MovieCollection::new));
    }
}
