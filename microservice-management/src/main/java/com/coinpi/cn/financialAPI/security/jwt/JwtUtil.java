package com.coinpi.cn.financialAPI.security.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.coinpi.cn.financialAPI.database.entity.AcessRole;
import com.coinpi.cn.financialAPI.database.entity.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class JwtUtil {
	
    // http://www.allkeysgenerator.com -> used to generate jwt secret (512 bits encryption key)
    private static final String JWT_SECRET = "z$C&F)J@NcRfUjXn2r5u7x!A%D*G-KaPdSgVkYp3s6v9y/B?E(H+MbQeThWmZq4t";
    private static final int EXPIRATION_TIME = 10;//days
    
    
    /**
     * Parses token and gets the claims
     * @param token token to parse
     * @return claims
     */
    public static Claims getClaims(String token) {
        byte[] signingKey = JwtUtil.JWT_SECRET.getBytes();

        token = token.replace("Bearer ", "");

        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    
    
    /**
     * Return the username of the user present in the given token
     * @param token token
     * @return return username
     */
    public static String getLogin(String token) {
        Claims claims = getClaims(token);
        return !isNull(claims) ? claims.getSubject() : null;
    }

    
    
    
    /**
     * Gets the roles that user present in token has
     * @param token token
     * @return return the list of roles
     */
    public static List<GrantedAuthority> getRoles(String token) {
        Claims claims = getClaims(token);
        if (claims == null) 
            return null;
        return ((List<?>) claims.get("rol")).stream().map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());
    }
    
    
    
    /**
     * @return return if token is valid
     */
    public static boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        if (nonNull(claims)) {
            String login = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return login != null && expiration != null && now.before(expiration);
        }
        return false;
    }

    
    
    
    
    
    /**
     * Creates token for a given user
     * The output token has an attribute rol that contains user roles
     * @param user user to be used to create token
     * @return return users token
     */
    public static String createToken(User user) {
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        byte[] signingKey = JWT_SECRET.getBytes();

        int days = EXPIRATION_TIME;
        long time = days * 24 /*hours*/ * 60 /*min*/ * 60 /*sec*/ * 1000  /*milis*/;
        Date expiration = new Date(System.currentTimeMillis() + time);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .claim("rol", roles)
                .compact();
    }

    
    public static String createTokenTest(String userName, List<AcessRole> accessRoles) {
        List<String> roles = accessRoles.stream().map(AcessRole::toGrantedAuthority).map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        byte[] signingKey = JWT_SECRET.getBytes();

        int days = EXPIRATION_TIME;
        long time = days * 24 /*hours*/ * 60 /*min*/ * 60 /*sec*/ * 1000  /*milis*/;
        Date expiration = new Date(System.currentTimeMillis() + time);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setSubject(userName)
                .setExpiration(expiration)
                .claim("rol", roles)
                .compact();
    }
    
    
    
    /**
     * @return return the username of authenticated user
     */
    public static String getAuthLogin() {
        User user = getUser();
        if(user != null)
            return user.getUsername();
        return null;
    }

    
    
    
  
    public static User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() != null)
            return (User) auth.getPrincipal();
        return null;
    }
}