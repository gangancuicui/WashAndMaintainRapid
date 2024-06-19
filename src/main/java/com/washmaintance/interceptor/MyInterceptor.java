package com.washmaintance.interceptor;


import com.alibaba.fastjson.JSONObject;

import com.washmaintance.anno.LoginInterceptor;
import com.washmaintance.anno.ManagerInterceptor;
import com.washmaintance.pojo.Result;
import com.washmaintance.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.IOException;


@Component
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if(handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // Set CORS headers
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Max-Age", "86400");
        resp.setHeader("Access-Control-Allow-Headers", "*");

        // Handle OPTIONS preflight request
        if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {
            resp.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        // Check for custom annotations
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginInterceptor loginIntercept = handlerMethod.getMethod().getAnnotation(LoginInterceptor.class);
        ManagerInterceptor managerIntercept = handlerMethod.getMethod().getAnnotation(ManagerInterceptor.class);

        // If method does not require interception, allow request
        if (loginIntercept == null && managerIntercept == null) {
            System.out.println("访问的方法无需拦截");
            return true;
        }

        // Check for tokens
        String token = req.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            token = req.getHeader("wxToken");
        }

        if (!StringUtils.hasLength(token)) {
            log.info("token为空");
            return unauthorizedResponse(resp);
        }

        try {
            Claims claims = JwtUtils.parseJWT(token);
            if (token.equals(req.getHeader("wxToken"))) {
                req.setAttribute("openid", claims.get("openid"));
            }
        } catch (Exception e) {
            log.info("令牌解析失败");
            return unauthorizedResponse(resp);
        }

        return true;
    }

    private boolean unauthorizedResponse(HttpServletResponse resp) throws IOException {
        Result r = Result.error("NOT_LOGIN");
        String json = JSONObject.toJSONString(r);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}


