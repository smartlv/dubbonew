package com.alibaba.dubbo.demo;

/**
 * 用户业务异常
 */
public class UserException extends BusinessException
{
    private static final long serialVersionUID = -4679221528879969571L;

    public UserException(final ErrorCode errorCode)
    {
        super(errorCode);
    }
}
