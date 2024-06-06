package org.sandcastle.apps;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.sandcastle.apps.broker.receiver.DefaultMessageReceiver;
import org.sandcastle.apps.broker.receiver.MessageReceiver;
import org.sandcastle.apps.broker.receiver.StreamsMessageReceiver;
import org.sandcastle.apps.broker.sender.DefaultMessageSender;
import org.sandcastle.apps.broker.sender.MessageSender;
import org.sandcastle.apps.broker.sender.StreamsMessageSender;
import org.sandcastle.apps.generator.Source;
import org.sandcastle.apps.generator.SourceImpl;

public class AppInjectorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Source.class).to(SourceImpl.class);
        bind(DefaultMessageSender.class).annotatedWith(Names.named("AmqpMessageSender")).to(DefaultMessageSender.class);
        bind(StreamsMessageSender.class).annotatedWith(Names.named("StreamsMessageSender")).to(StreamsMessageSender.class);
    }

    @Provides
    @Named("AmqpMessageSender")
    MessageSender amqpMessageSender() {
        return new DefaultMessageSender();
    }

    @Provides
    @Named("StreamsMessageSender")
    MessageSender streamsMessageSender() {
        return new StreamsMessageSender();
    }

    @Provides
    @Named("StreamsMessageReceiver")
    MessageReceiver streamsMessageReceiver() {
        return new StreamsMessageReceiver();
    }

    @Provides
    @Named("AmqpMessageReceiver")
    MessageReceiver amqpMessageReceiver() {
        return new DefaultMessageReceiver();
    }
}
