package org.engineFRP.FRP;

import sodium.*;

/**
 * Created by TekMaTek on 21/03/2015.
 */
public class CellUpdater<A> {

    private Lambda2<Cell<A>, Stream<A>, Cell<A>> resolver;
    //TODO: Consider using Optionals here to avoid having to init the Cell.
    private Cell<A> value;
    private Stream<A> stream = new Stream<>();

    public CellUpdater(final Lambda2<Cell<A>, Stream<A>, Cell<A>> resolver, final A initValue) {
        this.resolver = resolver;
        value = replay(new Cell<>(initValue));
    }

    private Cell<A> replay(final Cell<A> cell) {
        return resolver.apply(cell, stream);
    }

    public CellUpdater<A> merge(final Stream<A> otherStream) {
        this.stream = stream.merge(otherStream);
        this.value = replay(value);
        return this;
    }

    public void changeResolver(Lambda2<Cell<A>, Stream<A>, Cell<A>> newResolver) {
        this.resolver = newResolver;
        this.value = replay(value);
    }

    public A sample() {
        return value.sample();
    }

//    //TODO: need to refactor the entire code base into a push based system like this instead of sample().
//    public Listener listener(final Handler<A> action) {
//        return this.value.value().listen(action);
//    }

}