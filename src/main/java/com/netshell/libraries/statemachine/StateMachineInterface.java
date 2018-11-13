package com.netshell.libraries.statemachine;

public interface StateMachineInterface<T> {

    T getState();

    void setState(T state);

}
