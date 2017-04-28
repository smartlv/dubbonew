package com.alibaba.dubbo.config;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * 作者: smartlv 日期：2017/4/27.
 */
public class Test
{
    public static void main(String[] args)
    {
        // 可以修改里边代码，打出来stirng，看动态编译前的java
        ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    }
}
