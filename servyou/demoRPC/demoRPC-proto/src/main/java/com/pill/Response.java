package com.pill;

import lombok.Data;

/**
 * @PackageName: com.pill
 * @ClassName: Response
 * @Description: 表示RPC返回
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
public class Response {
    /***
     * 服务返回编码：0-成功，非0失败
     */
    private int code = 0;
    /***
     * 具体的错误信息
     */
    private String message = "OK";
    /***
     * 返回的数据
     */
    private Object data;
}
