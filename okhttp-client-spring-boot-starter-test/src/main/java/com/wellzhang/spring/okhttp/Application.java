package com.wellzhang.spring.okhttp;

import com.wellzhang.okhttp.spring.autoconfigure.EnableOkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/25 10:29
 */
@SpringBootApplication
@EnableOkHttpClient(scanPackages = "com.wellzhang.spring.okhttp.client")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
