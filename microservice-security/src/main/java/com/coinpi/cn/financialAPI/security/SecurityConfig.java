package com.coinpi.cn.financialAPI.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import com.coinpi.cn.financialAPI.security.jwt.JwtAuthenticationFilter;
import com.coinpi.cn.financialAPI.security.jwt.JwtAuthorizationFilter;
import com.coinpi.cn.financialAPI.security.jwt.handler.AccessDeniedHandler;
import com.coinpi.cn.financialAPI.security.jwt.handler.UnauthorizedHandler;

import lombok.Getter;

@Getter
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    
	@Autowired
	@Qualifier("userService")
	private UserDetailsService userDetailsService;


	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		AuthenticationManager authManager = authenticationManager();

		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and().authorizeRequests()
			.antMatchers(HttpMethod.GET,  "/security/*","/loaderio-ee3e2e35dd64ed6093114831839a2929/","/", JwtAuthenticationFilter.getAuthUrl()).permitAll()
			.antMatchers(HttpMethod.POST, "/security/*").permitAll()
			.antMatchers("/v2/api-docs", "/configuration/**", "/*","/swagger*/**", "/webjars/**").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable()
			.addFilter(new CorsConfig())
			.addFilter(new JwtAuthenticationFilter(authManager))
			.addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler)
			.authenticationEntryPoint(unauthorizedHandler)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());
	}




	/**
	 * @return the encoder
	 */
	public static BCryptPasswordEncoder getEncoder() {
		return encoder;
	}
}
