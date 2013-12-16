package com.anjuke.aps.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.anjuke.aps.message.protocol.ProtocolFactory;
import com.anjuke.aps.message.serializer.MessagePackSerializer;

public class SimpleApsClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {

    }

    public static void main(String[] args) throws TimeoutException,
            InterruptedException, ExecutionException {
        ApsClient client;

        DefaultClientRequestProcessor processor = new DefaultClientRequestProcessor(
                new MessagePackSerializer(), "tcp://127.0.0.1:8964");

        processor.init();

        client = new SimpleApsClient(
                ProtocolFactory.getProtocol(ProtocolFactory.APS_12_VERSION),
                processor);

        // client=new SimpleApsClient("tcp://192.168.1.62:1123", new
        // MessagePackSerializer());
        System.out.println(client.asyncRequest(".ping", 1000).get(1000,TimeUnit.MINUTES));

        // DefaultApsClient defClient=new DefaultApsClient(new
        // MessagePackSerializer(),"tcp://127.0.0.1:8964");
        // defClient.init();
        // for(int i=0;i<100;i++){
        // Future<Object> f= defClient.asyncRequest(".ping", 1000);
        // }
        // defClient.destroy();

        processor.destroy();
    }
}
