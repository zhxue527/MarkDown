package com.pill;

import com.pill.Impl.HttpTransportServer;
import com.pill.codec.Decoder;
import com.pill.codec.Encoder;
import com.pill.codec.Impl.JsonDecoder;
import com.pill.codec.Impl.JsonEncoder;
import lombok.Data;

/**
 * server配置
 */
@Data
public class ServerConfig {
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private int port = 3000;
}
