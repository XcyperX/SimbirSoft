package com.simbir_soft.service.commands.proxy;

import com.simbir_soft.model.Message;
import com.simbir_soft.service.MessageService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    public static MessageService newInstance(MessageService t, InvocationHandler handler) {
        return (MessageService) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), handler);
    }
}
