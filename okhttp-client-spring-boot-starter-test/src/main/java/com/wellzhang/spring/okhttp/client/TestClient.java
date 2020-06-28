package com.wellzhang.spring.okhttp.client;

import com.wellzhang.okhttp.annotation.OkHttpClient;
import com.wellzhang.okhttp.annotation.OkHttpHeader;
import com.wellzhang.okhttp.annotation.OkHttpHeaders;
import com.wellzhang.okhttp.annotation.OkHttpMapping;
import com.wellzhang.okhttp.annotation.OkHttpParam;
import com.wellzhang.okhttp.annotation.OkHttpTimeout;
import com.wellzhang.okhttp.enums.RequestMethod;
import com.wellzhang.spring.okhttp.vo.request.TestRequest;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/25 10:53
 */
@OkHttpClient
@OkHttpMapping(path = "http://127.0.0.1:8080")
public interface TestClient {

  @OkHttpMapping(path = "/get")
  String get(@OkHttpParam("user") String user);

  @OkHttpMapping(path = "/post", method = RequestMethod.POST)
  String post(TestRequest request);

  @OkHttpMapping(path = "/sleep")
  String sleep();

  @OkHttpMapping(path = "/sleep")
  @OkHttpHeaders(headers = {@OkHttpHeader(name = "User-Agent", value = "Chrome/83.0.4103.116 Safari/537.36")})
  @OkHttpTimeout(connectTimeout = 300, readTimeout = 300, writeTimeout = 300)
  String timeout();



}
