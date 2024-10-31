package com.ecommerce.shopping_cart.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @SuppressWarnings("deprecation")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt=request.getHeader("Authorization");
        // Bearer JWT TOKEN
        if(jwt!=null){
            jwt=jwt.substring(7);
            try{

                SecretKey key= Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes());
                //Validation JWT
                Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> authorityList= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,authorityList);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch(Exception e){
                throw new BadCredentialsException("Invalid Token");
            }
        }

        filterChain.doFilter(request,response);
    }
}
