package com.alibaba.dubbo.demo;

import java.io.Serializable;

/**
 * 业务异常错误码定义类
 * 
 * @author smartlv
 */
public class ErrorCode implements Serializable
{
    private static final long serialVersionUID = 1982607259137204522L;

    private final int code;
    private String message;

    public ErrorCode(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ErrorCode [code=" + code + ", message=" + message + "]";
    }

}
