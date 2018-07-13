package com.netshell.libraries.statemachine.provider;

import java.util.Objects;

public final class State<T> {

    private final T state;

    public State(final T state) {
        this.state = state;
    }

    public T getState() {
        return state;
    }

    @Override
    public String toString() {
        return "State{" +
            "state=" + state +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State<?> state1 = (State<?>) o;
        return Objects.equals(state, state1.state);
    }

    @Override
    public int hashCode() {

        return Objects.hash(state);
    }
}
