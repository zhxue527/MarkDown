package com.pill.codec.Impl;

import com.pill.codec.Decoder;
import com.alibaba.fastjson.JSON;

public class JsonDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
