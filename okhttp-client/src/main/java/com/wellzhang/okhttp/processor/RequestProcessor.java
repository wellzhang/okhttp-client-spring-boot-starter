package com.wellzhang.okhttp.processor;

import com.wellzhang.okhttp.HttpRequest;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:57
 */
public interface RequestProcessor {
  void process(HttpRequest request) throws Exception;
}
