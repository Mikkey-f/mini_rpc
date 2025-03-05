package github.mikkeyf.serviceImpl;

import github.mikkeyf.Hello;
import github.mikkeyf.HelloService;
import io.protostuff.Rpc;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-01  20:33
 */
@Slf4j
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
