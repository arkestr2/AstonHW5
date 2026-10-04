package model;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MovieCollection<M> extends AbstractList<M> {
    private final List<M> internalList = new ArrayList<>();

    public MovieCollection() {

    }

    public MovieCollection(M... items) {
        Collections.addAll(internalList, items);
    }

    @Override
    public M get(int index) {
        return internalList.get(index);
    }

    @Override
    public int size() {
        return internalList.size();
    }

    @Override
    public void add(int index, M element) {
        internalList.add(index, element);
    }

    @Override
    public M set(int index, M element) {
        return internalList.set(index, element);
    }

    @Override
    public M remove(int index) {
        return internalList.remove(index);
    }
}
