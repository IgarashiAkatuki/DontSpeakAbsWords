package com.project.common.utils;

import com.mysql.cj.util.StringUtils;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.expiration}")
    private long expiration;

    private final String CLAIM_KEY_CREATED = "created";

    private final String CLAIM_KEY_USERNAME = "sub";

    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    public String generateToken(Map<String, Object> claim){
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        HashMap<String, Object> map = new HashMap<>();
        map.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        map.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(map);
    }

    public Claims getClaims(String token){
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            claims = e.getClaims();
        }catch (Exception e){
            e.printStackTrace();
        }

        return claims;
    }
    public String getUserNameFromToken(String token){
        String username = null;
        try {
            Claims claims = getClaims(token);
            username = claims.getSubject();
        }catch (Exception e){
            JwtException jwtException = new JwtException("无法解析token或token已过期");
            jwtException.initCause(e);
            jwtException.printStackTrace();
        }
        return username;
    }

    public boolean validToken(String token, UserDetails userDetails){
        if (userDetails == null || token == null){
            return false;
        }
        String username = getUserNameFromToken(token);
        String s = userDetails.getUsername();
        if (s != null && username != null){
            return username.equals(s);
        }
        return false;
    }

    private Date getExpirationDateFromToken(String token){
        Claims claims = getClaims(token);
        System.out.println(claims.getExpiration().toString());
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){
        Date expiredTime = getExpirationDateFromToken(token);
        return expiredTime.before(new Date());
    }

    public String refreshToken(String oldToken){
        String newToken = null;
        try{
            if (StringUtils.isNullOrEmpty(oldToken)){
                return null;
            }
            Date expiredTime = getExpirationDateFromToken(tokenHead);
            Claims claims = getClaims(oldToken);

            if (claims == null || expiredTime == null){
                return null;
            }

            if (expiredTime.before(new Date()) && (new Date().getTime() - expiredTime.getTime()) > 30*60){
                return oldToken;
            }
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }catch (Exception e){
            e.printStackTrace();
        }
        return newToken;
    }
}

