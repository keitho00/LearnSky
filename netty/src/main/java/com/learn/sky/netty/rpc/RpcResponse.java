package com.learn.sky.netty.rpc;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: JiuBuKong
 * @Date: 2019/3/19 下午2:27
 */
@Data
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = 7734429165733576843L;
    private Object value;
    private Exception exception;
    private long requestId;
    private long processTime;
    private int timeout;
    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息
    private byte rpcProtocolVersion;
}
