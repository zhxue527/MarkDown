package com.pill;

import com.pill.codec.Decoder;
import com.pill.codec.Encoder;
import com.pill.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

public class Client {
    private ClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public Client() {
        this(new ClientConfig());
    }

    public Client(ClientConfig config) {
        this.config = config;

        this.encoder = ReflectionUtils.newInstance(this.config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(this.config.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(this.config.getSelectorClass());

        this.selector.init(this.config.getServers(), this.config.getConnectCount(), this.config.getTransportClass());
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{clazz}, new RemoteInvoker(clazz, encoder, decoder, selector));
    }
}
