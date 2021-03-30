package com.coinpi.cn.financialAPI.security.jwt;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.model.LoginModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String AUTH_URL = "/api/login";

    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(getAuthUrl());
    }

    
    
    /**
     * Makes an authentication attempt
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
        	LoginModel login = new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);
//        	String requestStr = new String(request.getInputStream().readAllBytes());
//            JSONObject j = new JSONObject(requestStr);
//            String username = j.getString("username");
//            String password = j.getString("password");
        	
        	String username = login.getUsername();
        	String password = login.getPassword();

            if(username.isEmpty() || password.isEmpty()) 
                throw new BadCredentialsException("Missing username/password");

            Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
            
            return authenticationManager.authenticate(auth);//calls userService loadUserByUsername
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    
    
    /**
     * This method is called if authentication attempt has success
     */
    @Override
    @Transactional
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
    	 
    	String jwtToken = JwtUtil.createToken((User) authentication.getPrincipal());
     	JSONObject json = new JSONObject();
     	json.put("Acess token", jwtToken);
        
        ServletUtil.write(response, HttpStatus.OK, json.toString());
    }

    
    
    
    /**
     * This method is called if authentication attempt has not success
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException error) throws IOException, ServletException {
        String json = ServletUtil.getJson("error", "Invalid username/password");
        ServletUtil.write(response, HttpStatus.UNAUTHORIZED, json);
    }

    
    
    
	/**
	 * @return the authUrl
	 */
	public static String getAuthUrl() {
		return AUTH_URL;
	}
}