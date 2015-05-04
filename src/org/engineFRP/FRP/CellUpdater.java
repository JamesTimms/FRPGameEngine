package org.engineFRP.FRP;

import sodium.*;

/**
 * Created by TekMaTek on 21/03/2015.
 */
public class CellUpdater<A> {

    private Lambda2<Cell<A>, Stream<A>, Cell<A>> resolver;
    private Cell<A> value;
    private Stream<A> stream = new Stream<>();
    private StreamSink<A> direct = new StreamSink<>();

    public CellUpdater(final Lambda2<Cell<A>, Stream<A>, Cell<A>> resolver, final A initValue) {
        this.stream = stream.merge(direct);//Set up direct accessor method.
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

    public CellUpdater<A> updateValue(A a) {
        direct.send(a);
        return this;
    }

    public void changeResolver(Lambda2<Cell<A>, Stream<A>, Cell<A>> newResolver) {
        this.resolver = newResolver;
        this.value = replay(value);
    }

    public Stream<A> updateFrom() {
        return value.updates();
    }

    public A sample() {
        return value.sample();
    }

//    public Listener listener(final Handler<A> action) {
//        return this.value.value().listen(action);
//    }

}