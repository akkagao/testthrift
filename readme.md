# thrift 安装

## brew install

```shell
sudo brew install thrift
```

## 编写接口协议文件

在testthrift/api/src/main/resources/thrift 目录下编写thrift接口协议文件

## 编译thrift协议文件为java客户端

命令行进入api文件 执行 `sh compile.sh` 命令

ps：如果compile.sh 没有可执行权限，执行 `chrome +x compile.sh` 赋予可执行权限

## 在service 项目中编写接口实现类

```java
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
```

## 编写服务启动类

```java
package com.crazywolf;

import com.crazywolf.service.HelloServiceImpl;
import com.crazywolf.testthrift.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * package: com.crazywolf
 *
 * @author : gaojianwen
 * @date : 2017-11-06
 */
public class HelloServiceServer {
    public static void main(String[] args) {
        try {
            System.out.println("服务端开启....");
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloServiceImpl());
            // 简单的单线程服务模型
            TServerSocket serverTransport = new TServerSocket(9898);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
        }catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}

```

## 在client项目中编写服务调用方

```java
package com.crazywolf;

import com.crazywolf.testthrift.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * package: com.crazywolf
 *
 * @author : gaojianwen
 * @date : 2017-11-06
 */
public class ClientApp {
    public static void main(String[] args) {
        System.out.println("客户端启动....");
        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 9898, 30000);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            transport.open();
            String result = client.sayHello("CrazyWolf ");
            System.out.println("返回结果" + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}

```

