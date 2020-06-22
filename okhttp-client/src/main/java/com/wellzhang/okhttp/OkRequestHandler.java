package com.wellzhang.okhttp;

import com.fasterxml.jackson.databind.JavaType;
import com.wellzhang.okhttp.enums.MediaType;
import com.wellzhang.okhttp.enums.RequestMethod;
import com.wellzhang.okhttp.exceptions.OkHttpClientException;
import com.wellzhang.okhttp.utils.ClassUtils;
import com.wellzhang.okhttp.interceptors.RequestTimeoutHolder;
import com.wellzhang.okhttp.metadate.OkHttpParamMetadata;
import com.wellzhang.okhttp.metadate.OkHttpRequestMetadata;
import com.wellzhang.okhttp.processor.RequestProcessor;
import com.wellzhang.okhttp.processor.ResponseProcessor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:32
 */
public class OkRequestHandler {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private Method method;

  private Object[] args;

  private OkHttpRequestMetadata okHttpRequestMetadata;

  public OkRequestHandler(Method method, Object[] args, OkHttpRequestMetadata okHttpRequestMetadata) {
    this.args = args;
    this.method = method;
    this.okHttpRequestMetadata = okHttpRequestMetadata;
  }

  public Object hanlder() {
    try {
      String path = okHttpRequestMetadata.getPath();
      String desc = okHttpRequestMetadata.getDesc();
      RequestMethod requestMethod = okHttpRequestMetadata.getRequestMethod();
      if(okHttpRequestMetadata.getOkHttpTimeoutMetadata() != null){
        RequestTimeoutHolder.set(okHttpRequestMetadata.getOkHttpTimeoutMetadata());
      }
      Map<Integer, OkHttpParamMetadata> httpParamMetadataMap = okHttpRequestMetadata.getOkHttpParamMetadataMap();
      Map<String, Object> paramObject = new TreeMap<>();
      for (int i = 0; i < args.length; i++) {
        if (ClassUtils.isPrimitive(args[i].getClass())) {
          Map<String, Object> jsonMap = OkHttpClientContext.getInstance().getObjectMapper().convertValue(args[i], Map.class);
          paramObject.putAll(jsonMap);
        } else {
          OkHttpParamMetadata okHttpParamMetadata = httpParamMetadataMap.get(i);
          if (okHttpParamMetadata != null) {
            paramObject.put(okHttpParamMetadata.getName(), args[i] == null ? okHttpParamMetadata.getDefaultValue() : args[i]);
          }
        }
      }
      OkHttpRequest okHttpRequest = new OkHttpRequest(path, paramObject);
      okHttpRequest.setDesc(desc);
      List<RequestProcessor> processors = okHttpRequestMetadata.getRequestProcessors();
      if (processors != null) {
        for (RequestProcessor processor : processors) {
          processor.process(okHttpRequest);
        }
      }
      OkHttpResponse okHttpResponse = null;
      if (requestMethod.equals(RequestMethod.POST)) {
        if (okHttpRequestMetadata.getMediaType().equals(MediaType.APPLICATION_JSON)) {
          okHttpResponse = OkHttpClientContext.getInstance().getOkHttpClientHelper().
              postJSON(okHttpRequest.getUri(), okHttpRequest.getParameters(), okHttpRequestMetadata.getHeaderMap());
        }
        if (okHttpRequestMetadata.getMediaType().equals(MediaType.APPLICATION_URLENCODED)) {
          okHttpResponse = OkHttpClientContext.getInstance().getOkHttpClientHelper().
              postForm(okHttpRequest.getUri(), okHttpRequest.getParameters(), okHttpRequestMetadata.getHeaderMap());
        }
      }

      if (requestMethod.equals(RequestMethod.GET)) {
        okHttpResponse = OkHttpClientContext.getInstance().getOkHttpClientHelper().
            get(okHttpRequest.getUri(), okHttpRequest.getParameters(), okHttpRequestMetadata.getHeaderMap());
      }

      if(okHttpResponse == null){
        throw new Exception("okHttpResonse为空，检测okRequestHanlder");
      }
      okHttpResponse.setReturnType(method.getGenericReturnType());

      List<ResponseProcessor> responseProcessors = okHttpRequestMetadata.getResponseProcessors();
      if (responseProcessors != null) {
        for (ResponseProcessor processor : responseProcessors) {
          processor.process(okHttpResponse);
          if (okHttpResponse.getResultObject() != null) {
            return okHttpResponse.getResultObject();
          }
        }
      }

      String result = okHttpResponse.getResultString();
      if (StringUtils.isEmpty(result)) {
        return null;
      }

      if (Void.TYPE == method.getReturnType()) {
        return null;
      }

      if (String.class == method.getReturnType()) {
        return result;
      }

      JavaType javaType = OkHttpClientContext.getInstance().getObjectMapper().constructType(method.getGenericReturnType());

      return OkHttpClientContext.getInstance().getObjectMapper().readValue(result, javaType);
    } catch (Exception e) {
      logger.error("exception : {}", e);
      throw new OkHttpClientException("method hanlder exception ：" + e.getMessage());
    } finally {
      RequestTimeoutHolder.remove();
    }
  }
}
