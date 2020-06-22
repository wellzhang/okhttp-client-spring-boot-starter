package com.wellzhang.okhttp;

import static java.util.regex.Pattern.compile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.wellzhang.okhttp.exceptions.OkHttpClientException;
import com.wellzhang.okhttp.interceptors.DynamicTimeoutInterceptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 21:22
 */
public class OkHttpClientHelper {


  private OkHttpClient okHttpClient;

  /**
   * 初始化okHttpClientHelper
   */
  public void initOkHttpClientHelper(OkHttpClient okHttpClient, OkHttpClientConfig okHttpClientConfig) {
    if(okHttpClientConfig == null){
      okHttpClientConfig = new OkHttpClientConfig();
    }
    if(okHttpClient == null) {
      ConnectionPool connectionPool = new ConnectionPool(okHttpClientConfig.getMaxIdleConnections(),
          okHttpClientConfig.getKeepAliveDuration(), TimeUnit.MINUTES);
      Dispatcher dispatcher = new Dispatcher();
      dispatcher.setMaxRequests(okHttpClientConfig.getMaxTotalConnections());
      dispatcher.setMaxRequestsPerHost(okHttpClientConfig.getMaxConnectionsPerRoute());
      this.okHttpClient = new OkHttpClient.Builder()
          .connectionPool(connectionPool)
          .connectTimeout(okHttpClientConfig.getConnectTimeout(), TimeUnit.SECONDS)
          .readTimeout(okHttpClientConfig.getReadTimeout(), TimeUnit.SECONDS)
          .writeTimeout(okHttpClientConfig.getWriteTimeout(), TimeUnit.SECONDS)
          .retryOnConnectionFailure(true)
          .dispatcher(dispatcher)
          .addInterceptor(new DynamicTimeoutInterceptor())
          .build();
    }else {
      this.okHttpClient = okHttpClient.newBuilder().addInterceptor(new DynamicTimeoutInterceptor()).build();
    }
  }

  /**
   * get请求
   * @param url 请求链接
   * @param params 参数
   * @param headers 请求头
   * @return OkHttpResponse 结果
   * @throws JsonProcessingException json解析异常
   */
  public OkHttpResponse get(String url, Map<String, Object> params, Map<String, String> headers) throws JsonProcessingException {
    if (null != params && !params.isEmpty()) {
      if (url.contains("?")) {
        url = url.concat(parseUrlData(params));
      } else {
        url = url.concat("?" + parseUrlData(params));
      }
    }
    Request.Builder builder = new Request.Builder().url(url);

    if (headers != null && headers.size() > 0) {
      for (String name : headers.keySet()) {
        builder.addHeader(name, headers.get(name));
      }
    }
    Request request = builder.get().build();

    return excute(url, request);
  }

  /**
   * post json请求
   * @param url 请求链接
   * @param params 入参
   * @param headers 请求头
   * @return 结果
   * @throws JsonProcessingException 异常
   */
  public OkHttpResponse postJSON(String url, Map<String, Object> params, Map<String, String> headers) throws JsonProcessingException {
    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    RequestBody body = RequestBody.create(mediaType, OkHttpClientContext.getInstance().getObjectMapper().writeValueAsString(params));
    //请求
    Request.Builder builder = new Request.Builder().url(url).post(body);

    if (headers != null && headers.size() > 0) {
      for (String name : headers.keySet()) {
        builder.addHeader(name, headers.get(name));
      }
    }

    Request request = builder.build();
    return excute(url, request);
  }

  /**
   * post form请求
   * @param url 请求连接
   * @param params 参数
   * @param headers 请求头
   * @return 结果
   * @throws JsonProcessingException 异常
   */
  public OkHttpResponse postForm(String url, Map<String, Object> params, Map<String, String> headers) throws JsonProcessingException {
    //请求
    Request.Builder builder = new Request.Builder().url(url);

    if (headers != null && headers.size() > 0) {
      for (String name : headers.keySet()) {
        builder.addHeader(name, headers.get(name));
      }
    }
    FormBody.Builder formBuilder = new FormBody.Builder();
    if (null != params) {
      for (Map.Entry<String, Object> entry : params.entrySet()) {
        if (entry.getValue() != null) {
          formBuilder.add(entry.getKey(), parseJsonString(entry.getValue()));
        }
      }
    }
    Request request = builder.post(formBuilder.build()).build();

    return excute(url, request);
  }

  /**
   * httpclient执行
   * @param url 链接
   * @param request 请求
   * @return 请求response
   */
  private OkHttpResponse excute(String url, Request request) {
    //宙斯埋点
    OkHttpResponse okHttpResponse;
    Pattern urlPattern = compile("htt\\w{1,2}://([^?]+).*");
    String urlName = urlPattern.matcher(url).replaceAll("$1");

    if (okHttpClient == null) {
      throw new OkHttpClientException("okhttpclient is not initialized");
    }

    Call call = okHttpClient.newCall(request);
    try {
      okhttp3.Response response = call.execute();
      okHttpResponse = new OkHttpResponse(response);

      return okHttpResponse;
    } catch (IOException e) {
      throw new OkHttpClientException("请求io异常: IOException: " + e.getMessage());
    }
  }

  /**
   * 将参数转换为 &key=value 格式拼接
   *
   * @param params 参数
   * @return string 结果
   */
  private String parseUrlData(Map<String, Object> params) throws JsonProcessingException {
    StringBuilder sb = new StringBuilder();
    for (String key : params.keySet()) {
      if(params.get(key) != null){
        sb.append("&").append(key).append("=");
        sb.append(parseJsonString(params.get(key)));
      }
    }
    return sb.substring(1);
  }

  /**
   * 对象转string
   * @param object 对象
   * @return string结果
   * @throws JsonProcessingException 解析异常
   */
  private String parseJsonString(Object object) throws JsonProcessingException {
    if(object instanceof String){
      return String.valueOf(object);
    }
    return OkHttpClientContext.getInstance().getObjectMapper().writeValueAsString(object);
  }

  private ObjectMapper newObjectMapper(){
    ObjectMapper objectMapper = new ObjectMapper();
    // 忽略未知属性
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    // 忽略object空值
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    // 忽略map空值
    objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    return objectMapper;
  }

}
