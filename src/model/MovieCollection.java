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

    private void mergeSort(int left, int right, Comparator<? super M> c) {
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

