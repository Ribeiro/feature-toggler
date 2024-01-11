package br.tec.gtech.utilities.toggler.api.domain;

import java.util.*;


public enum Operation {
 
    IN("IN") {
        @Override
        public boolean isEnabled(Object firstArgument, Object secondArgument) {
            return ((Collection<String>) secondArgument).contains(firstArgument);
        }
    };

    private static final Map<String, Operation> BY_LABEL = new HashMap<>();
    
    static {
        for (Operation e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public final String label;

    private Operation(String label) {
        this.label = label;
    }

    public abstract boolean isEnabled(Object firstArgument, Object secondArgument);

    public static Operation valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
