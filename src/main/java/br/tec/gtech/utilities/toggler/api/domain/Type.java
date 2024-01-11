package br.tec.gtech.utilities.toggler.api.domain;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Type {
    RELEASE("RELEASE"), CONTEXT("CONTEXT");

    private static final String INVALID_FEATURE_FLAG_TYPE_MESSAGE = "Invalid FeatureFlag Type";
    private static final String INVALID_ARGUMENT_MESSAGE = "text must not be not null and not empty";
    private String text;

    Type(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Type fromText(String text) {
        if(StringUtils.isEmpty(text)){
            throw new IllegalArgumentException(INVALID_ARGUMENT_MESSAGE);
        }

        String upperCasedText = text.toUpperCase();

        for (Type r : Type.values()) {
            if (r.getText().equals(upperCasedText)) {
                return r;
            }
        }
        
        throw new IllegalArgumentException(INVALID_FEATURE_FLAG_TYPE_MESSAGE);
    }

    @Override
    public String toString() {
        return text;
    }
}