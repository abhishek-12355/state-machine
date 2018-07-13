package com.netshell.libraries.statemachine.metadata;

import com.netshell.libraries.statemachine.provider.State;
import com.netshell.libraries.statemachine.provider.Transition;

import java.util.Set;

public interface StateProviderMetadata<T> {
    Set<State<T>> getStates();

    Set<Transition<T>> getTransitions();

    Class<?> getProvidingClass();
}
