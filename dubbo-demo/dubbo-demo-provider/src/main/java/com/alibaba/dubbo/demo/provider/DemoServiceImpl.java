/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.demo.provider;

import com.alibaba.dubbo.demo.BusinessException;
import com.alibaba.dubbo.demo.DemoService;
import com.alibaba.dubbo.demo.ErrorCode;
import com.alibaba.dubbo.demo.UserException;
import com.alibaba.dubbo.rpc.RpcContext;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DemoServiceImpl implements DemoService
{

    public String sayHello(String name) throws BusinessException
    {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name
                + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());

        if (name.isEmpty())
        {
            throw new UserException(new ErrorCode(1, "错误 jvm is shit"));

        }

        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public Map sayHello2(String name) throws BusinessException
    {
        return Maps.newHashMap();
    }

}
