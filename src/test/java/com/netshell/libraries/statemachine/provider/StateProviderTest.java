package com.netshell.libraries.statemachine.provider;

import com.netshell.libraries.statemachine.metadata.StateProviderMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StateProviderTest {

    private StateProvider<String> provider;

    @BeforeEach
    void setUp() {
        provider = new StateProvider<>(new SPMetadata());
    }

    @Test
    void nextState() {
        assertEquals("init", getActual(null, StateEvent.INIT).getState());
        assertEquals("inp", getActual("ack", StateEvent.SUCCESS).getState());
        assertEquals("failed", getActual("ack", StateEvent.FAIL).getState());
        assertEquals("complete", getActual("ack", StateEvent.COMPLETE).getState());
    }

    @Test
    void nextState_NoTransitions() {
        assertThrows(IllegalArgumentException.class, () -> getActual("failed", StateEvent.SUCCESS));
    }

    @Test
    void nextState_InvalidState() {
        assertThrows(IllegalArgumentException.class, () -> getActual("Invalid", StateEvent.SUCCESS));
    }

    private State getActual(String state, StateEvent event) {
        return provider.nextState(state != null ? new State<>(state) : null, event);
    }

    private final static class SPMetadata implements StateProviderMetadata<String> {
        @Override
        public Set<State<String>> getStates() {
            return new HashSet<>(Arrays.asList(
                new State<>("init"),
                new State<>("ack"),
                new State<>("inp"),
                new State<>("complete"),
                new State<>("failed")
            ));
        }

        @Override
        public Set<Transition<String>> getTransitions() {
            final Set<Transition<String>> set = new HashSet<>();
            set.add(new Transition<>(null, new State<>("init"), StateEvent.INIT));
            set.add(new Transition<>(new State<>("init"), new State<>("ack"), StateEvent.SUCCESS));
            set.add(new Transition<>(new State<>("ack"), new State<>("inp"), StateEvent.SUCCESS));
            set.add(new Transition<>(new State<>("ack"), new State<>("complete"), StateEvent.COMPLETE));
            set.add(new Transition<>(new State<>("ack"), new State<>("failed"), StateEvent.FAIL));
            return set;
        }

        @Override
        public Class<?> getProvidingClass() {
            return StateProviderTest.class;
        }
    }
}