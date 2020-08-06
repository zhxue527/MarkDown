package com.pill;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网路请求的Handler
 */
public interface RequestHandler {
    void onRequest(InputStream receive, OutputStream os);
}
