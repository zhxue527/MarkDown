package com.pill.codec.Impl;

import com.pill.codec.Encoder;
import com.alibaba.fastjson.JSON;

public class JsonEncoder implements Encoder {
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
