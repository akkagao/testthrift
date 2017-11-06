package com.crazywolf.service;

import com.crazywolf.testthrift.HelloWorldService;
import org.apache.thrift.TException;

/**
 * package: com.crazywolf
 *
 * @author : gaojianwen
 * @date : 2017-11-06
 */
public class HelloServiceImpl implements HelloWorldService.Iface {
    /**
     * @param username
     * @return
     * @throws TException
     */
    @Override
    public String sayHello(String username) throws TException {
        System.out.println("接收到参数" + username);
        return "hello " + username;
    }
}
