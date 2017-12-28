package com.alibaba.dubbo.demo;

/**
 * 服务异常基类, 业务异常告诉调用方
 * 
 * @author smartlv
 */
public class BusinessException extends RuntimeException
{
    private static final long serialVersionUID = -7887057238055620806L;
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

    public ErrorCode getCode()
    {
        return errorCode;
    }

    @Override
    public String getMessage()
    {
        return String.format("code : %s ; msg : %s", errorCode.getCode(), errorCode.getMessage());
    }

    @Override
    public synchronized Throwable fillInStackTrace()
    {
        return this;
    }
}
