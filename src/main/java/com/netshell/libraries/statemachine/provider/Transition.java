package com.netshell.libraries.statemachine.provider;

import java.util.Objects;

public final class Transition<T> {

    private final State<T> fromState;
    private final State<T> toState;
    private final StateEvent event;

    public Transition(final State<T> fromState, final State<T> toState, final StateEvent event) {
        this.fromState = fromState;
        this.toState = toState;
        this.event = event;
    }

    public State<T> getFromState() {
        return fromState;
    }

    public State<T> getToState() {
        return toState;
    }

    public StateEvent getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "Transition{" +
            "fromState=" + fromState +
            ", toState=" + toState +
            ", event=" + event +
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
        Transition<?> that = (Transition<?>) o;
        return Objects.equals(fromState, that.fromState) &&
            Objects.equals(toState, that.toState) &&
            event == that.event;
    }

    @Override
    public int hashCode() {

        return Objects.hash(fromState, toState, event);
    }
}
