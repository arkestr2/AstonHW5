package strategy;

import model.MovieCollection;

public class MovieCollectionCreator {

    private MovieCollectionCreationStrategy strategy;

    public MovieCollectionCreator() {}

    public MovieCollectionCreator(MovieCollectionCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(MovieCollectionCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public MovieCollection create() {
        return strategy.create();
    }
}
