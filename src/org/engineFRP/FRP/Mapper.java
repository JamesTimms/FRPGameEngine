package org.engineFRP.FRP;

import java.util.function.Function;

/**
 * Created by TekMaTek on 03/04/2015.
 */
public class Mapper<T> {

    T value;

    public Mapper(T value) {
        this.value = value;
    }

    public <U> Mapper<U> map(Function<? super T, ? extends U> mapper) {
        U newValue = mapper.apply(value);
        return new Mapper<>(newValue);
    }

    public <U> U baseMap(Function<? super T, ? extends U> mapper) {
        return mapper.apply(value);
    }

}