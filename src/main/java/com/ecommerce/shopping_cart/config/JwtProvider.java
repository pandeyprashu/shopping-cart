package com.ecommerce.shopping_cart.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    SecretKey key= Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes());

    @SuppressWarnings("deprecation")
    public String generateToken(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
        String roles=populateAuthorities(authorities);

        return  Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+86400000))
                .claim("email",authentication.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
    }

    @SuppressWarnings("deprecation")
    public String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth=new HashSet<>();
        for(GrantedAuthority authority:authorities){
            auth.add(authority.getAuthority());
        }
        return String.join(",",auth);
    }

}
