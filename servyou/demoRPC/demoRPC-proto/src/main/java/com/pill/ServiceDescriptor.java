package com.pill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @PackageName: com.pill
 * @ClassName: ServiceDescriptor
 * @Description: 表示服务
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;
}
