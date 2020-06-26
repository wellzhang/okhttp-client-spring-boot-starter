package com.wellzhang.spring.okhttp.service;

import com.wellzhang.spring.okhttp.client.TestClient;
import com.wellzhang.spring.okhttp.vo.request.TestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/25 10:53
 */
@Service
public class TestService {

  @Autowired
  private TestClient testClient;

  public String get(String user){
    return testClient.get(user);
  }

  public String post(TestRequest request){
    return testClient.post(request);
  }

  public String sleep(){
    return testClient.sleep();
  }

  public String timeout(){
    try {
      return testClient.timeout();
    }catch (Exception e){
      return e.getMessage();
    }
  }

}
