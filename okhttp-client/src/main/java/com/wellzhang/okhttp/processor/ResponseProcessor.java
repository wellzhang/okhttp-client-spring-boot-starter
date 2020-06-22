package com.wellzhang.okhttp.processor;

import com.wellzhang.okhttp.HttpResponse;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:58
 */
public interface ResponseProcessor {

  void process(HttpResponse response) throws Exception;
}
