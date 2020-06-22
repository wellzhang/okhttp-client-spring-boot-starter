package com.wellzhang.okhttp.metadate;

import com.wellzhang.okhttp.OkHttpClientContext;
import com.wellzhang.okhttp.annotation.OkBeforeRequest;
import com.wellzhang.okhttp.annotation.OkBeforeResponse;
import com.wellzhang.okhttp.annotation.OkHttpMapping;
import com.wellzhang.okhttp.annotation.OkHttpParam;
import com.wellzhang.okhttp.annotation.OkHttpTimeout;
import com.wellzhang.okhttp.enums.MediaType;
import com.wellzhang.okhttp.enums.RequestMethod;
import com.wellzhang.okhttp.exceptions.OkHttpClientException;
import com.wellzhang.okhttp.utils.ClassUtils;
import com.wellzhang.okhttp.processor.RequestProcessor;
import com.wellzhang.okhttp.processor.ResponseProcessor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 21:05
 */
public class OkHttpRequestMetadata {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private String path;

  private String desc;

  private MediaType mediaType;

  private RequestMethod requestMethod;

  private OkHttpTimeoutMetadata okHttpTimeoutMetadata;

  private List<RequestProcessor> requestProcessors = new ArrayList<>();

  private List<ResponseProcessor> responseProcessors = new ArrayList<>();

  private Map<String, String> headerMap = new HashMap<>();

  private Map<Integer, OkHttpParamMetadata> okHttpParamMetadataMap = new HashMap<>();

  private final String PATH_FLAG = "/";

  public OkHttpRequestMetadata(Method method) {
    OkHttpMapping classMapping = method.getDeclaringClass().getAnnotation(OkHttpMapping.class);
    OkHttpMapping methodMapping = method.getAnnotation(OkHttpMapping.class);

    if (methodMapping != null) {
      this.requestMethod = methodMapping.method();
      this.desc = methodMapping.desc();
      this.mediaType = methodMapping.produce();
      String headersString = methodMapping.headers();
      if(StringUtils.isNotBlank(headersString)) {
        try {
          headerMap = OkHttpClientContext.getInstance().getObjectMapper()
              .convertValue(headersString, Map.class);
        }catch (Exception e){
          logger.warn("headers error:{}.",e.getMessage());
        }
      }

      StringBuilder pathStringBuffer = new StringBuilder();
      if (classMapping != null) {
        String classMappingPath = classMapping.path();
        if (StringUtils.isNotEmpty(classMappingPath)) {
          pathStringBuffer.append(classMappingPath);
        }
      }
      String methodMappingPath = methodMapping.path();
      if (StringUtils.isNotEmpty(methodMappingPath)) {
        if (pathStringBuffer.length() > 0 && !pathStringBuffer.toString().endsWith(PATH_FLAG)
            && !methodMappingPath.startsWith(PATH_FLAG)) {
          pathStringBuffer.append(PATH_FLAG).append(methodMappingPath);
        } else {
          pathStringBuffer.append(methodMappingPath);
        }
      }
      if (pathStringBuffer.length() > 0) {
        this.path = pathStringBuffer.toString();
      }

      Class<?>[] parameterTypes = method.getParameterTypes();
      Annotation[][] parameterAnnotations = method.getParameterAnnotations();
      for (int i = 0; i < parameterAnnotations.length; i++) {
        Class<?> paramClass = parameterTypes[i];
        if (ClassUtils.isPrimitive(paramClass)) {
          continue;
        }

        Annotation[] annotations = parameterAnnotations[i];
        for (Annotation annotation : annotations) {
          if (annotation instanceof OkHttpParam) {
            OkHttpParam okHttpParam = (OkHttpParam) annotation;
            OkHttpParamMetadata okHttpParamMetadata = new OkHttpParamMetadata(okHttpParam, i);
            okHttpParamMetadataMap.put(i, okHttpParamMetadata);
          }
        }
      }

      OkHttpTimeout classTimeout = method.getDeclaringClass().getAnnotation(OkHttpTimeout.class);
      OkHttpTimeout methodTimeout = method.getAnnotation(OkHttpTimeout.class);
      if(methodTimeout != null && (methodTimeout.connectTimeout() > 0 || methodTimeout.readTimeout() > 0 || methodTimeout.writeTimeout() > 0)){
        OkHttpTimeoutMetadata okHttpTimeoutMetadata = new OkHttpTimeoutMetadata();
        okHttpTimeoutMetadata.setConnectTimeout(methodTimeout.connectTimeout());
        okHttpTimeoutMetadata.setReadTimeout(methodTimeout.readTimeout());
        okHttpTimeoutMetadata.setWriteTimeout(methodTimeout.writeTimeout());
        this.okHttpTimeoutMetadata = okHttpTimeoutMetadata;
      }else if(classTimeout != null && (classTimeout.connectTimeout() > 0 || classTimeout.readTimeout() > 0 || classTimeout.writeTimeout() > 0)){
        OkHttpTimeoutMetadata okHttpTimeoutMetadata = new OkHttpTimeoutMetadata();
        okHttpTimeoutMetadata.setConnectTimeout(classTimeout.connectTimeout());
        okHttpTimeoutMetadata.setReadTimeout(classTimeout.readTimeout());
        okHttpTimeoutMetadata.setWriteTimeout(classTimeout.writeTimeout());
        this.okHttpTimeoutMetadata = okHttpTimeoutMetadata;
      }

      try {
        OkBeforeRequest okBeforeRequestClass = method.getDeclaringClass().getAnnotation(
            OkBeforeRequest.class);
        if (okBeforeRequestClass != null) {
          for (Class<? extends RequestProcessor> requestProcessor : okBeforeRequestClass.value()) {
            requestProcessors.add(requestProcessor.newInstance());
          }
        }

        OkBeforeRequest okBeforeRequestMethod = method.getAnnotation(OkBeforeRequest.class);
        if (okBeforeRequestMethod != null) {
          for (Class<? extends RequestProcessor> requestProcessor : okBeforeRequestMethod.value()) {
            requestProcessors.add(requestProcessor.newInstance());
          }
        }

        OkBeforeResponse okBeforeResponseClass = method.getDeclaringClass().getAnnotation(
            OkBeforeResponse.class);
        if (okBeforeResponseClass != null) {
          for (Class<? extends ResponseProcessor> responseProcessor : okBeforeResponseClass.value()) {
            responseProcessors.add(responseProcessor.newInstance());
          }
        }

        OkBeforeResponse okBeforeResponseMethod = method.getAnnotation(OkBeforeResponse.class);
        if (okBeforeResponseMethod != null) {
          for (Class<? extends ResponseProcessor> responseProcessor : okBeforeResponseMethod.value()) {
            responseProcessors.add(responseProcessor.newInstance());
          }
        }

      } catch (InstantiationException | IllegalAccessException e) {
        logger.error("InstantiationException : {}", e);
        throw new OkHttpClientException("Create requestProcessor exception, please check @OkBeforeRequest in your method : "
            + method,
            e);
      }
    }
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public RequestMethod getRequestMethod() {
    return requestMethod;
  }

  public Map<String, String> getHeaderMap() {
    return headerMap;
  }

  public void setHeaderMap(Map<String, String> headerMap) {
    this.headerMap = headerMap;
  }

  public void setRequestMethod(RequestMethod requestMethod) {
    this.requestMethod = requestMethod;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public List<RequestProcessor> getRequestProcessors() {
    return requestProcessors;
  }

  public Map<Integer, OkHttpParamMetadata> getOkHttpParamMetadataMap() {
    return okHttpParamMetadataMap;
  }

  public List<ResponseProcessor> getResponseProcessors() {
    return responseProcessors;
  }

  public OkHttpTimeoutMetadata getOkHttpTimeoutMetadata() {
    return okHttpTimeoutMetadata;
  }

  public void setOkHttpTimeoutMetadata(
      OkHttpTimeoutMetadata okHttpTimeoutMetadata) {
    this.okHttpTimeoutMetadata = okHttpTimeoutMetadata;
  }
}
