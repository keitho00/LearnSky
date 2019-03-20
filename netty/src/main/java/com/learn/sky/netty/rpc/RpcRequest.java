package com.learn.sky.netty.rpc;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: JiuBuKong
 * @Date: 2019/3/19 下午2:26
 */
@Data
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 548084156838880762L;
    private String interfaceName;
    private String methodName;
    private String parametersDesc;
    private Object[] arguments;
    private Map<String, String> attachments;
    private int retries = 0;
    private long requestId;
    private byte rpcProtocolVersion;
}