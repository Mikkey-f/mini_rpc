package github.mikkeyf.serviceImpl;

import github.mikkeyf.HelloService;

/**
 * @Author: Mikkeyf
 * @CreateTime: 2025-03-01  20:33
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
