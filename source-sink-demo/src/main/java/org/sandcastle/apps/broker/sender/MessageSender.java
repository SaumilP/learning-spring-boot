package org.sandcastle.apps.broker.sender;

import org.sandcastle.apps.model.Record;

import java.util.List;

public interface MessageSender {
    default void send(Record record) {}
    default void sendAll(List<Record> records) {}
}
