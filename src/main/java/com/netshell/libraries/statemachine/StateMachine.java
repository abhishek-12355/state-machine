package com.netshell.libraries.statemachine;

import com.netshell.libraries.statemachine.provider.State;
import com.netshell.libraries.statemachine.provider.StateEvent;
import com.netshell.libraries.statemachine.provider.StateProvider;

import java.util.function.Consumer;

public final class StateMachine<I, T extends StateMachineInterface<I>> {

    private final T tObject;
    private final StateProvider<I> stateProvider;
    private final Consumer<StateMachineUpdateParaketers> consumer;

    StateMachine(T tObject, StateProvider<I> stateProvider, Consumer<StateMachineUpdateParaketers> consumer) {
        this.tObject = tObject;
        this.stateProvider = stateProvider;
        this.consumer = consumer;
    }

    public void init() {
        applyNextState(StateEvent.INIT);
    }

    public void applyNextState(StateEvent event) {
        final State<I> originalState = getCurrentState();
        final State<I> nextState = stateProvider.nextState(originalState, event);
        tObject.setState(nextState.getState());
        try {
            consumer.accept(new StateMachineUpdateParaketers(tObject, event, originalState, nextState));
        } catch (Exception e) {
            tObject.setState(originalState.getState());
            throw e;
        }
    }

    public State<I> getCurrentState() {
        return State.from(tObject.getState());
    }

    public T gettObject() {
        return tObject;
    }

    public final class StateMachineUpdateParaketers {
        private final T tObject;
        private final StateEvent event;
        private final State<I> originalState;
        private final State<I> newState;

        StateMachineUpdateParaketers(T tObject, StateEvent event, State<I> originalState, State<I> newState) {
            this.tObject = tObject;
            this.event = event;
            this.originalState = originalState;
            this.newState = newState;
        }

        public T gettObject() {
            return tObject;
        }

        public StateEvent getEvent() {
            return event;
        }

        public State<I> getOriginalState() {
            return originalState;
        }

        public State<I> getNewState() {
            return newState;
        }
    }
}
