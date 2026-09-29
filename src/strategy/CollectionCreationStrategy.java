package strategy;

import model.Movie;

import java.util.ArrayList;

public interface CollectionCreationStrategy {
    ArrayList<Movie> create();
}
