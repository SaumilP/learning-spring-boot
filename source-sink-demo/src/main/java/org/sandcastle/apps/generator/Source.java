package org.sandcastle.apps.generator;

import org.sandcastle.apps.model.Record;

import java.util.stream.Stream;

public interface Source {
    Stream<Record> generate();
}
