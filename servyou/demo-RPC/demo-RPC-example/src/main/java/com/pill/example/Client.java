package com.pill.example;

import com.pill.RpcClient;
import com.pill.example.Impl.CalcServiceImpl;

public class Client {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient();
        CalcService service = rpcClient.getProxy(CalcService.class);

        int r1 = service.add(1, 2);
        int r2 = service.minus(1, 2);

        System.out.println(r1);
        System.out.println(r2);
    }
}
