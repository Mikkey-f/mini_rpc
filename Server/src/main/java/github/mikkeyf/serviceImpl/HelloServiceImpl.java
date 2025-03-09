package github.mikkeyf.serviceImpl;

import github.mikkeyf.Hello;
import github.mikkeyf.HelloService;
import github.mikkeyf.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-01  20:33
 */
@Slf4j
@RpcService(version = "test1", group = "version1")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }

    static {
        System.out.printf("HelloServiceImpl loaded!\n");
    }
}
