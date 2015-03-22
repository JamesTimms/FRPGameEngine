package org.engineFRP.FRP;

import sodium.Cell;
import sodium.Lambda2;
import sodium.Stream;

/**
 * Created by TekMaTek on 21/03/2015.
 */
public final class CellUpdater<A, B> {

    private final Lambda2<Cell<B>, Stream<A>, Cell<B>> resolver;
    //TODO: Consider using Optionals here to avoid having to init the Cell.
    private Cell<B> value;
    private Stream<A> stream = new Stream<>();

    public CellUpdater(final Lambda2<Cell<B>, Stream<A>, Cell<B>> resolver, final B initValue) {
        this.resolver = resolver;
        value = replay(new Cell<>(initValue));
    }

    private Cell<B> replay(final Cell<B> cell) {
        return resolver.apply(cell, stream);
    }

    public CellUpdater<A, B> merge(final Stream<A> otherStream) {
        this.stream = stream.merge(otherStream);
        this.value = replay(value);
        return this;
    }

    public B sample() {
        return value.sample();
    }

}