package org.sandcastle.apps.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Greet {
    @JsonProperty
    private final String name;

    @JsonCreator
    public Greet(@JsonProperty String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
