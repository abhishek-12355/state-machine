# state-machine

## Usage

### StateProvider and ServiceLoader

StateMahcine uses StateProviders to generate states of an instance. All StateProviders are initialized via service loaders. 

### Register StateMachine

First step is to register the type of StateMachine
```java
Consumer<StateMachineUpdateParameters> consumer = p->{}; // event consumer
StateMachineFactory.registerStateMachine(stateObject.class, consumer)
``` 

### Retrieve StateMachine instance

Next step is to retrieve StateMachine instance against the stateObject
```java
StateMachineInterface stateObject = new StateObject();
StateMachine<StateObject> stateMachine = StateMachineFactory.getStateMachine(stateObject)
stateMachine.init(); // set the first state. delegates to stateMachine.applyNextState(StateEvent.INIT);
stateMachine.applyNextState(StateEvent.SUCCESS); // move to next state
stateMachine.applyNextState(StateEvent.FAIL); // mark the object on failed terminal state.
stateMachine.applyNextState(StateEvent.COMPLETE); // mark the object in complete terminal state.
```