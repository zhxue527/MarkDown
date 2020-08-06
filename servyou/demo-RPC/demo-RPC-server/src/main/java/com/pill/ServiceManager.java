package com.pill;

import com.pill.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance instance = new ServiceInstance(bean, method);
            ServiceDescriptor sd = ServiceDescriptor.from(interfaceClass, method);

            services.put(sd, instance);
            log.info("register service: {} {}", sd.getClazz(), sd.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor sd = request.getService();
        return services.get(sd);
    }
}
