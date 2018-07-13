package com.netshell.libraries.statemachine.provider;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

final class TransitionLookup<T> {

    private final Set<Transition<T>> transitions;

    TransitionLookup(final Set<Transition<T>> transitions) {
        this.transitions = new HashSet<>(transitions);
    }

    Optional<Transition<T>> lookup(final State<T> fromState, StateEvent event) {
        return transitions.stream()
            .filter(i -> i.getEvent().equals(event))
            .filter(i -> Objects.equals(i.getFromState(), fromState))
            .findAny();
    }

}
