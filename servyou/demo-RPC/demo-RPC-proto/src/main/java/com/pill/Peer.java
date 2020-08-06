package com.pill;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @PackageName: com.pill
 * @ClassName: Peer
 * @Description: 表示网络传输的端点
 * @Author: SKY
 * @Data:: 2020/08/06
 */
@Data
@AllArgsConstructor
public class Peer {
    private String host;
    private int port;
}
