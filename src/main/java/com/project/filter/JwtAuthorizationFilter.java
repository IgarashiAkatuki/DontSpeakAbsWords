package com.project.filter;

import com.mysql.cj.util.StringUtils;
import com.project.common.utils.JwtUtils;
import com.project.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.expiration}")
    private long expiration;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() != null) {
            String rawToken = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (tokenHeader.equals(cookie.getName())) {
                    rawToken = cookie.getValue();
                    break;
                }
            }
            if (rawToken != null) {
                String username = jwtUtils.getUserNameFromToken(rawToken);
                if (!StringUtils.isNullOrEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (userDetails != null && username.equals(userDetails.getUsername())){
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        token.setDetails(request);
                        SecurityContextHolder.getContext().setAuthentication(token);

                        String refreshedToken = jwtUtils.refreshToken(rawToken);
                        Cookie cookie = new Cookie(tokenHeader,refreshedToken);
                        cookie.setPath("/");
                        cookie.setMaxAge((int) expiration);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
