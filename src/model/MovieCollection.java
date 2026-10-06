package model;

import java.util.*;

public final class MovieCollection extends AbstractList<Movie> {
    private final List<Movie> internalList = new ArrayList<>();

    public MovieCollection(Movie... items) {
        Collections.addAll(internalList, items);
    }

    @Override
    public Movie get(int index) {
        return internalList.get(index);
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public void add(int index, Movie element) {
        internalList.add(index, element);
    }

    @Override
    public Movie set(int index, Movie element) {
        return internalList.set(index, element);
    }

    @Override
    public Movie remove(int index) {
        return internalList.remove(index);
    }

    @Override
    public void sort(Comparator<? super Movie> c) {
        mergeSort(0, internalList.size() - 1, c);
    }

    public void sortByOnlyEvenYear() {
        MovieCollection toSort = new MovieCollection();
        MovieCollection toKeep = new MovieCollection();

        for (Movie movie : internalList) {
            if (movie.getReleaseYear() % 2 == 0) {
                toSort.add(movie);
            } else {
                toKeep.add(movie);
            }
        }

        toSort.sort(Comparator.comparingInt(Movie::getReleaseYear));

        int keepPointer = 0;
        int sortPointer = 0;
        for (int i = 0; i < internalList.size(); i++) {
            if (keepPointer < toKeep.size() && internalList.get(i) == toKeep.get(keepPointer)) {
                keepPointer++;
            } else if (sortPointer < toSort.size()) {
                internalList.set(i, toSort.get(sortPointer++));
            }
        }
    }

    private void mergeSort(int left, int right, Comparator<? super Movie> c) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(left, mid, c);
        mergeSort(mid + 1, right, c);
        merge(left, mid, right, c);
    }

    private void merge(int left, int mid, int right, Comparator<? super Movie> c) {
        List<Movie> leftHalf = new ArrayList<>(internalList.subList(left, mid + 1));
        List<Movie> rightHalf = new ArrayList<>(internalList.subList(mid + 1, right + 1));

        int leftPointer = 0;
        int rightPointer = 0;
        for (int k = left; k <= right; k++) {
            if (leftPointer == leftHalf.size()) {
                internalList.set(k, rightHalf.get(rightPointer++));
            } else if (rightPointer == rightHalf.size()) {
                internalList.set(k, leftHalf.get(leftPointer++));
            } else if (c.compare(leftHalf.get(leftPointer), rightHalf.get(rightPointer)) <= 0) {
                internalList.set(k, leftHalf.get(leftPointer++));
            } else {
                internalList.set(k, rightHalf.get(rightPointer++));
            }
        }
    }
}

