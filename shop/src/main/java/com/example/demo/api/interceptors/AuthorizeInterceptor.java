package com.example.demo.api.interceptors;

import com.example.demo.exceptions.NotAllowedException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.BaseUser;
import com.example.demo.service.UserManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserManagement userManagement;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.trace("Authentication " + authentication);
        if (authentication != null) {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                return checkAuthorization(request, currentUserName, response);
            } else {
                log.trace(
                        "AnonymousUser requesting method: "
                                + request.getMethod()
                                + " on "
                                + request.getRequestURI());
                if (isLogin(request)) {
                    return true;
                } else if (alwaysAllowedPath(request)) {
                    return true;
                } else {
                    return sendError(request, response);
                }
            }
        } else {
            if (request.getMethod().equalsIgnoreCase("post")
                    && request.getRequestURI().equalsIgnoreCase("/error")) return true;
            log.warn(
                    "AnonymousUser requesting method: "
                            + request.getMethod()
                            + " on "
                            + request.getRequestURI());
            return sendError(request, response);
        }
    }

    private boolean sendError(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "AnonymousUser requesting method: "
                        + request.getMethod()
                        + " on "
                        + request.getRequestURI());
        return false;
    }

    private boolean isLogin(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("get") && request.getRequestURI().equals("/");
    }

    private boolean checkAuthorization(
            HttpServletRequest request,
            String currentUserName,
            HttpServletResponse response)
            throws NotFoundException, NotAllowedException, IOException {

        log.trace("Current User: " + currentUserName);
        log.trace(request.getMethod() + " on URI: " + request.getRequestURI());
        log.trace("UserManagement: " + userManagement);
        BaseUser baseUser;
        try {
            baseUser = userManagement.queryByName(currentUserName);
            return true;
        } catch (NotFoundException e) {
            log.trace("User not found for name: " + currentUserName);
        }

        return sendError(request, response);
    }

    // TODO realize this configurable
    private boolean alwaysAllowedPath(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("*");
    }
}
