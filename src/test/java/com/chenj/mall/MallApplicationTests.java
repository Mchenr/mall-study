package com.chenj.mall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test1(){
        String name = "张三";
        System.out.println(name.substring(0, 1));
    }

}
