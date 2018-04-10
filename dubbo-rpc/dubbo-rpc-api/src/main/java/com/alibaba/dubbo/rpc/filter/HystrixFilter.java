package com.alibaba.dubbo.rpc.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.filter.hystrix.DubboCommand;
import com.alibaba.dubbo.rpc.filter.hystrix.config.SetterFactory;
import com.netflix.hystrix.HystrixCommand;

//类似brave zipkin链路追踪，熔断器也是通过dubbo过滤器嵌入
@Activate(group = Constants.CONSUMER, before = "future")
public class HystrixFilter implements Filter
{
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException
    {
        URL url = invoker.getUrl();
        // 方法名
        String methodName = invocation.getMethodName();
        // 接口名
        String interfaceName = invoker.getInterface().getName();

        // 获取相关熔断配置，增減了緩存
        HystrixCommand.Setter setter = SetterFactory.create(interfaceName, methodName, url);

        // 获取降级方法
        String fallback = url.getMethodParameter(methodName, "fallback");

        DubboCommand command = new DubboCommand(setter, invoker, invocation, fallback);
        Result result = command.execute();
        return result;
    }

}
