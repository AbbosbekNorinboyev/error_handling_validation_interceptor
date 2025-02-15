package uz.pdp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Log
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String contentType = request.getContentType();
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringJoiner stringJoiner = new StringJoiner(",", "{", "}");
        parameterMap.forEach((k, v) -> {
            stringJoiner.add(k + ": "+Arrays.toString(v));
        });
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        Iterator<String> iterator = headerNames.asIterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            String headerValues = request.getHeader(headerName);
            headers.put(headerName, headerValues);
        }
        StringBuilder stringBuilder = new StringBuilder();
        String logMessage = stringBuilder.append("{ path: ").append(requestURI).append(", ")
                .append("method: ").append(method).append(", ")
                .append("content type: ").append(contentType).append(", ")
                .append("parameters: ").append(stringJoiner).append(", ")
                .append("headers: ").append(headers).append(" }").toString();
        log.info(logMessage);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("messageFromInterceptor", "Spring interceptors are good");
    }
}
