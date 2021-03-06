package com.anjuke.aps.server.zmq;

import com.anjuke.aps.ApsConfig;
import com.anjuke.aps.RequestHandler;
import com.anjuke.aps.message.MessageFilter;
import com.anjuke.aps.server.ApsServerStatusListener;
import com.anjuke.aps.server.DefaultMessageHandler;
import com.anjuke.aps.server.processor.DefaultRequestProcessor;

public class ApsZMQServerMain {
    public static void main(String[] args) throws InterruptedException {
        ApsConfig config = ApsConfig.getInstance();
        DefaultRequestProcessor processor = new DefaultRequestProcessor();
        for (RequestHandler handler : config.getRequestHandler()) {
            processor.addHandler(handler);
        }

        DefaultMessageHandler messageHandler = new DefaultMessageHandler();

        for (MessageFilter filter : config.getMessageFilter()) {
            messageHandler.addFilter(filter);
        }

        messageHandler.setProcessor(processor);
        final ZMQServer server = new ZMQServer();
        server.setPort(config.getPort());
        for (ApsServerStatusListener listener : config
                .getServerStatusListener()) {
            server.addServerStatusListener(listener);
        }
        server.setMessageHandler(messageHandler);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.stop();
            }
        });


        server.start();
        server.join();

    }
}
