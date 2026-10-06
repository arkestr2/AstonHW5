package strategy;

import model.Movie;
import model.MovieCollection;

public interface CollectionCreationStrategy {
    MovieCollection create();
}
