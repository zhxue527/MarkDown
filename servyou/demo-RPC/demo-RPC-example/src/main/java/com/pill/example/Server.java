package com.pill.example;

import com.pill.RpcServer;
import com.pill.example.Impl.CalcServiceImpl;

public class Server {
    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(CalcService.class, new CalcServiceImpl());
        rpcServer.start();
    }
}
