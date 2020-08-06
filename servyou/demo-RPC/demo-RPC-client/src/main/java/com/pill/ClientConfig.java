package com.pill;

import com.pill.Impl.HttpTransportClient;
import com.pill.Impl.RandomTransportSelector;
import com.pill.codec.Decoder;
import com.pill.codec.Encoder;
import com.pill.codec.Impl.JsonDecoder;
import com.pill.codec.Impl.JsonEncoder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ClientConfig {
    private Class<? extends TransportClient> transportClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Collections.singletonList(new Peer("127.0.0.1", 3000));
}
