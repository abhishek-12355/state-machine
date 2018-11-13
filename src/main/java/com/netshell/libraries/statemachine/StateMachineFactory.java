package com.netshell.libraries.statemachine;

import com.netshell.libraries.singleton.SingletonManager;
import com.netshell.libraries.statemachine.provider.StateProvider;
import com.netshell.libraries.statemachine.provider.StateProviderFactory;
import com.netshell.libraries.utilities.common.CommonUtils;

import java.lang.reflect.ParameterizedType;
import java.util.function.Consumer;

public class StateMachineFactory {

    private static final String STATE_MACHINE = "StateMachine";

    @SuppressWarnings("unchecked")
    public static <I, T extends StateMachineInterface<I>> StateMachine<I, T> getStateMachine(T tObject) {
        final String singletonName = composeSingletonName(StateMachine.class, tObject.getClass());
        final SingletonManager manager = CommonUtils.getManager(STATE_MACHINE);
        if (!manager.isRegistered(singletonName)) {
            synchronized (STATE_MACHINE) {
                if (!manager.isRegistered(singletonName)) {
                    manager.registerSingleton(singletonName, createStateMachine(tObject));
                }
            }
        }
        return manager.getSingleton(singletonName, StateMachine.class)
                .orElseThrow(() -> new IllegalStateException("No StateMachine Can be created"));
    }

    public static <I, T extends StateMachineInterface<I>> void registerStateMachine(Class<T> tClass, Consumer<StateMachine.StateMachineUpdateParaketers> stateConsumer) {
        final StateMachineBuilder<I, T> builder = new StateMachineBuilder<>();
        builder.withStateConsumer(stateConsumer == null ? p -> {
        } : stateConsumer);
        CommonUtils.getManager(STATE_MACHINE)
                .registerSingleton(composeSingletonName(StateMachineBuilder.class, tClass), builder);
    }

    @SuppressWarnings("unchecked")
    private static <I, T extends StateMachineInterface<I>> StateMachine<I, T> createStateMachine(T tObject) {
        final StateMachineBuilder<I, T> stateMachine = CommonUtils.getManager(STATE_MACHINE)
                .getSingleton(composeSingletonName(StateMachineBuilder.class, tObject.getClass()), StateMachineBuilder.class)
                .orElseThrow(() -> new IllegalArgumentException("State Machine not found"));

        return stateMachine.build(tObject);
    }

    private static String composeSingletonName(Class<?> sClass, Class<?> tClass) {
        return sClass.getName() + ":" + tClass.getName();
    }

    private static class StateMachineBuilder<I, T extends StateMachineInterface<I>> {
        private Consumer<StateMachine.StateMachineUpdateParaketers> consumer;

        StateMachine<I, T> build(T tObject) {
            final ParameterizedType type = (ParameterizedType) tObject.getClass().getGenericSuperclass();
            final StateProvider<I> provider = StateProviderFactory.getStateProvider((Class<I>) type.getActualTypeArguments()[0]);
            return new StateMachine<>(tObject, provider, consumer);
        }

        StateMachineBuilder withStateConsumer(Consumer<StateMachine.StateMachineUpdateParaketers> consumer) {
            this.consumer = consumer;
            return this;
        }
    }

}
