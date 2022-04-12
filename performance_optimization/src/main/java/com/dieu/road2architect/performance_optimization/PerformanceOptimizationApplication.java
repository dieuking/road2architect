package com.dieu.road2architect.performance_optimization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author dieu 2022年4月3日10点41分
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PerformanceOptimizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceOptimizationApplication.class, args);
    }

}
