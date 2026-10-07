package strategy;

import model.MovieCollection;

public class MovieCollectionCreator {

    private CollectionCreationStrategy strategy;

    public MovieCollectionCreator(CollectionCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(CollectionCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public MovieCollection create() {
        return strategy.create();
    }
}
