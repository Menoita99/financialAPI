package com.coinpi.cn.financialAPI.security.jwt;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.coinpi.cn.financialAPI.database.entity.User;
import com.coinpi.cn.financialAPI.model.UserDetailsModel;
import com.coinpi.cn.financialAPI.service.ServiceSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    
    private UserDetailsService userDetailsService;
    
    private ServiceSecurity serviceSecurity;

    
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,UserDetailsService userDetailsService, ServiceSecurity serviceSecurity) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.serviceSecurity = serviceSecurity;
    }

    
    
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");//read Authorization param from HTTP request

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {   
            ResponseEntity<String> responseEnt = serviceSecurity.tokenValidation(token);
            
            if(responseEnt.getStatusCode() != HttpStatus.OK)
            	throw new AccessDeniedException("Invalid Token");
            
            System.out.println(responseEnt.getBody().toString());
            
            
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject obj = new JSONObject(responseEnt.getBody());
            obj.remove("enabled");
            obj.remove("accountNonExpired");
            obj.remove("credentialsNonExpired");
            obj.remove("accountNonLocked");
            obj.remove("authorities");
            obj.remove("username");
			UserDetails userDetails = objectMapper.readValue(obj.toString(), User.class);
			System.out.println(userDetails.getUsername());
			userDetails.getAuthorities().forEach(r -> System.out.println(r.getAuthority()));
            
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //Saves Authentication into Spring context
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
            System.out.println("ENTREI NO FILTRO DO SERVICE CRL");

        } catch (RuntimeException ex) {
            logger.error("Authentication error: " + ex.getMessage(),ex);
            ex.printStackTrace();
            throw ex;
        }
    }
}