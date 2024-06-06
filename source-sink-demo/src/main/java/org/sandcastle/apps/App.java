package org.sandcastle.apps;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.sandcastle.apps.broker.DefaultStreamCreator;
import org.sandcastle.apps.broker.receiver.DefaultMessageReceiver;
import org.sandcastle.apps.broker.receiver.MessageReceiver;
import org.sandcastle.apps.broker.receiver.StreamsMessageReceiver;

public class App {
    
    public static void main(String[] args) {
        final boolean useAmqpClient = false;
        Injector injector = Guice.createInjector(new AppInjectorModule());

        if(!useAmqpClient) {
            DefaultStreamCreator streamCreator = injector.getInstance(DefaultStreamCreator.class);
            streamCreator.createStream();
        }

        // Generate new messages
        DefaultProcessor processor = injector.getInstance(DefaultProcessor.class);
        processor.configure(useAmqpClient).start();

        // Receive messages
        Class<? extends MessageReceiver> msgReceiverClazz = useAmqpClient ? DefaultMessageReceiver.class : StreamsMessageReceiver.class;
        MessageReceiver exporter = injector.getInstance(msgReceiverClazz);
        exporter.listen();

        System.exit(0);
    }
    
}
