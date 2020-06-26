package com.wellzhang.spring.okhttp.controller;

import com.wellzhang.spring.okhttp.service.TestService;
import com.wellzhang.spring.okhttp.vo.request.TestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/25 10:31
 */
@RestController
public class TestController {

  public static final int TIMEOUT = 2 * 1000;

  @Autowired
  private TestService testService;

  @RequestMapping(value = "post", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
  public Object post( @RequestBody TestRequest input){
      return "hello " + input.getMsg();
  }

  @RequestMapping(value = "get", method = RequestMethod.GET)
  public Object get(@RequestParam("user") String user){
    return "hello " + user;
  }

  @RequestMapping(value = "sleep", method = RequestMethod.GET)
  public Object sleep(){
    try {
      Thread.sleep(TIMEOUT);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "sleep 2s " ;
  }


  @RequestMapping(value = "/test/post", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
  public Object testPost( @RequestBody TestRequest input){
    return testService.post(input);
  }

  @RequestMapping(value = "/test/get", method = RequestMethod.GET)
  public Object testGet(@RequestParam("user") String user){
    return testService.get(user);
  }

  @RequestMapping(value = "/test/sleep", method = RequestMethod.GET)
  public Object testSleep(){
    return testService.sleep();
  }

  @RequestMapping(value = "/test/timeout", method = RequestMethod.GET)
  public Object testTimeout(){
    return testService.timeout();
  }

}
