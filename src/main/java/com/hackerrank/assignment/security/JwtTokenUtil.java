package com.hackerrank.assignment.security;

import com.hackerrank.assignment.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -32424243248L;
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;


    private Map<String, UserDTO> userDTOMap = new HashMap<>();

    @Value("${jwt.secret}")
    private String secret;


    @PostConstruct
    public void init() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setUserName("ahmed");
        userDTO.setPassword("password");
        userDTOMap.put(userDTO.getUserName(), userDTO);
        userDTO = new UserDTO();
        userDTO.setRole("ROLE_USER");
        userDTO.setUserName("mukhtiar");
        userDTO.setPassword("password");
        userDTOMap.put(userDTO.getUserName(), userDTO);
        userDTO = new UserDTO();
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setUserName("admin");
        userDTO.setPassword("password");
        userDTOMap.put(userDTO.getUserName(), userDTO);

    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("userName", user.getUserName());
        return doGenerateToken(claims, user.getUserName());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDTO user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUserName()) && !isTokenExpired(token));
    }

    public UserDTO getUserDTOByUserName(String userName) {
        return userDTOMap.get(userName);
    }


}