package com.pill;

import lombok.Data;

/**
 * @PackageName: com.pill
 * @ClassName: Request
 * @Description:
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
public class Request {
    private ServiceDescriptor service;
    private Object[] parameters;
}
