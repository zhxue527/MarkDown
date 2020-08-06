package com.pill.example.Impl;

import com.pill.example.CalcService;

public class CalcServiceImpl implements CalcService {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int minus(int a, int b) {
        return a-b;
    }
}
