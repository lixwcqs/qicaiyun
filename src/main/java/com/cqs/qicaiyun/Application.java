package com.cqs.qicaiyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 置于项目根目录下
 *
 */
@SpringBootApplication
//@MapperScan(basePackages = "com.cqs.qicaiyun.modules.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
