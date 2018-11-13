package com.netshell.libraries.statemachine.provider;

import com.netshell.libraries.statemachine.metadata.StateProviderMetadata;
import com.netshell.libraries.utilities.common.CommonUtils;

import java.util.Iterator;

public final class StateProviderFactory {

    private static final String STATE_PROVIDER = "StateProvider";

    static {
        final Iterator<StateProviderMetadata> iterator = CommonUtils.loadList(StateProviderMetadata.class);
        while (iterator.hasNext()) {
            final StateProviderMetadata<?> next = iterator.next();
            final StateProvider<?> stateProvider = new StateProvider<>(next);
            CommonUtils.getManager(STATE_PROVIDER).registerSingleton(next.getProvidingClass().getName(), () -> stateProvider);
        }
    }

    private StateProviderFactory() {
        // Ignore
    }

    @SuppressWarnings("unchecked")
    public static <T> StateProvider<T> getStateProvider(final Class<T> tClass) {
        return CommonUtils.getManager(STATE_PROVIDER)
            .getSingleton(tClass.getName(), StateProvider.class)
            .orElseThrow(() -> new IllegalArgumentException("No Provider Found for class: " + tClass));
    }

}
