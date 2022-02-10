package tech.mustpay.test;

import static java.util.Collections.list;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.valueOf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@AllArgsConstructor
public class RestLoggingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
    // Do nothing
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    ContentCachingRequestWrapper requestWrapper =
        new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper responseWrapper =
        new ContentCachingResponseWrapper((HttpServletResponse) response);

    filterChain.doFilter(requestWrapper, responseWrapper);

    logRequest(requestWrapper);

    byte[] body = responseWrapper.getContentAsByteArray();
    responseWrapper.copyBodyToResponse();

    logResponse(body, responseWrapper);
  }

  @Override
  public void destroy() {
    // Do nothing
  }

  private void logRequest(ContentCachingRequestWrapper request) {
    Map<String, List<String>> headers = list(request.getHeaderNames()).stream()
        .collect(toMap(name -> name, (name) -> list(request.getHeaders(name)), (n1, n2) -> n1));

    String bodyAsString = new String(request.getContentAsByteArray(), Charset.defaultCharset());

    log.info(new StringBuilder().append("\n")
        .append("URI          : ").append(request.getRequestURI()).append("\n")
        .append("Method       : ").append(request.getMethod()).append("\n")
        .append("Headers      : ").append(getHeaders(headers)).append("\n")
        .append("Request body : ").append(bodyAsString).append("\n").toString());
  }

  private void logResponse(byte[] body, HttpServletResponse response) {
    Map<String, List<String>> headers = response.getHeaderNames().stream()
        .collect(toMap(name -> name, (name)
            -> new ArrayList<>(response.getHeaders(name)), (n1, n2) -> n1));

    log.info(new StringBuilder().append("\n")
        .append("Status code  : ").append(response.getStatus()).append("\n")
        .append("Status text  : ").append(valueOf(response.getStatus()).getReasonPhrase()).append("\n")
        .append("Headers      : ").append(getHeaders(headers)).append("\n")
        .append("Response body: ").append(getBodyAsString(body)).append("\n").toString());
  }

  private String getBodyAsString(byte[] bodyAsByteArray) {
    return new String(bodyAsByteArray, Charset.defaultCharset());
  }

  private HttpHeaders getHeaders(Map<String, List<String>> headers) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.putAll(headers);

    return HttpHeaders.readOnlyHttpHeaders(httpHeaders);
  }
}
