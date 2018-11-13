# state-machine

## Usage

You can use ```StateProviderFactory``` class to retrieve an instance of StateProvider.

```java
StateProvider<?> provider = StateProviderFactory.getStateProvider(SomeClass.class);
```

After retrieving the provider you can simply use ```nextState()``` function to get the state based on some event.
```java
provider.nextState(currentState, event);
```
