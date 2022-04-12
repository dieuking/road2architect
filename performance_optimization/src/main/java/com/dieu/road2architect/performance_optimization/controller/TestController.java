package com.dieu.road2architect.performance_optimization.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Program Name: road2architect
 * <p>
 * Description: 测试控制器
 * <p>
 * Created by wangcan on 2022/4/3
 *
 * @author wangcan
 * @version 1.0
 */

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/index")
    public String index() {
        return "performance optimization module!!!";
    }
}
