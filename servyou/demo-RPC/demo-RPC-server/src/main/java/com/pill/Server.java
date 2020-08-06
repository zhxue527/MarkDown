package com.pill;

import com.pill.codec.Decoder;
import com.pill.codec.Encoder;
import com.pill.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

@Slf4j
public class Server {
    private ServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public Server(ServerConfig config) {
        this.config = config;

        // net
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);

        // codec
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        // service
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        this.serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
    }

    public void stop() {
        this.net.stop();
    }

    private RequestHandler handler = (receive, os) -> {
        Response resp = new Response();
        try {
            byte[] bytes = IOUtils.readFully(receive, receive.available());
            Request request = decoder.decode(bytes, Request.class);
            log.info("get request: {}", request);

            ServiceInstance instance = serviceManager.lookup(request);
            Object ret = serviceInvoker.invoke(instance, request);
            resp.setData(ret);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            resp.setCode(1);
            resp.setMessage("RpcServer got error: " + e.getClass().getName() + " " + e.getMessage());
        } finally {
            byte[] bytes = encoder.encode(resp);
            try {
                os.write(bytes);
                log.info("response client");
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
    };
}
