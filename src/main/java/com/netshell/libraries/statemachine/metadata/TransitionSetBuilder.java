package com.netshell.libraries.statemachine.metadata;

import com.netshell.libraries.statemachine.provider.State;
import com.netshell.libraries.statemachine.provider.StateEvent;
import com.netshell.libraries.statemachine.provider.Transition;

import java.util.HashSet;
import java.util.Set;

public class TransitionSetBuilder<T> {
    private final Set<Transition<T>> tSet = new HashSet<>();

    TransitionSetBuilder withInitTransition(final T toState) {

        if (tSet.stream().anyMatch(i -> i.getFromState() == null)) {
            throw new IllegalStateException("At most 1 Initial State Transition can be defined");
        }

        tSet.add(new Transition<>(null, new State<>(toState), StateEvent.INIT));
        return this;
    }

    TransitionSetBuilder withTransitionSuccess(final T fromState, final T toState) {
        tSet.add(new Transition<>(new State<>(fromState), new State<>(toState), StateEvent.SUCCESS));
        return this;
    }

    TransitionSetBuilder withTransitionFail(final T fromState, final T toState) {
        tSet.add(new Transition<>(new State<>(fromState), new State<>(toState), StateEvent.FAIL));
        return this;
    }

    TransitionSetBuilder withTransitionComplete(final T fromState, final T toState) {
        tSet.add(new Transition<>(new State<>(fromState), new State<>(toState), StateEvent.COMPLETE));
        return this;
    }

    Set<Transition<T>> build() {
        return tSet;
    }
}
