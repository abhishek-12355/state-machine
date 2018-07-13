package com.netshell.libraries.statemachine.provider;

import com.netshell.libraries.statemachine.metadata.StateProviderMetadata;

import java.util.HashSet;
import java.util.Set;

public final class StateProvider<T> {
    private final Set<State<T>> states;
    private final TransitionLookup<T> transitionLookup;

    StateProvider(final StateProviderMetadata<T> metadata) {
        states = new HashSet<>(metadata.getStates());

        final boolean isValidState = metadata.getTransitions().stream()
            .filter(i -> i.getFromState() != null)
            .noneMatch(i -> !states.contains(i.getFromState()) || !states.contains(i.getToState()));

        if (!isValidState) {
            throw new IllegalStateException("Transition States are not valid");
        }

        transitionLookup = new TransitionLookup<>(metadata.getTransitions());
    }

    public State<T> nextState(final State<T> currentState, final StateEvent event) {
        validate(currentState);

        final Transition<T> transition = transitionLookup.lookup(currentState, event)
            .orElseThrow(() -> new IllegalArgumentException("no transition present for input state and event"));

        return transition.getToState();
    }

    private void validate(final State<T> currentState) {
        if (currentState != null && !states.contains(currentState)) {
            throw new IllegalArgumentException("supplied current state is not valid: " + currentState);
        }
    }

}
