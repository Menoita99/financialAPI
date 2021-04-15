package com.coinpi.cn.financialAPI.security.jwt.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.coinpi.cn.financialAPI.security.jwt.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(UnauthorizedHandler.class);

    /**
     * This method is called if user is not authorized
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    	logger.warn("UnauthorizedHandler,path:"+request.getServletPath()+" exception: " + authException);
        String json = ServletUtil.getJson("error", "Not authorized");
        ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
    }
}