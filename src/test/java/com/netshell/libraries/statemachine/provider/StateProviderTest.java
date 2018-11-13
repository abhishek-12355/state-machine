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
        return provider.nextState(state != null ? State.from(state) : null, event);
    }

    private final static class SPMetadata implements StateProviderMetadata<String> {
        @Override
        public Set<State<String>> getStates() {
            return new HashSet<>(Arrays.asList(
                    State.from("init"),
                    State.from("ack"),
                    State.from("inp"),
                    State.from("complete"),
                    State.from("failed")
            ));
        }

        @Override
        public Set<Transition<String>> getTransitions() {
            final Set<Transition<String>> set = new HashSet<>();
            set.add(new Transition<>(null, State.from("init"), StateEvent.INIT));
            set.add(new Transition<>(State.from("init"), State.from("ack"), StateEvent.SUCCESS));
            set.add(new Transition<>(State.from("ack"), State.from("inp"), StateEvent.SUCCESS));
            set.add(new Transition<>(State.from("ack"), State.from("complete"), StateEvent.COMPLETE));
            set.add(new Transition<>(State.from("ack"), State.from("failed"), StateEvent.FAIL));
            return set;
        }

        @Override
        public Class<?> getProvidingClass() {
            return StateProviderTest.class;
        }
    }
}