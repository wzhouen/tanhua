package com.xuwen.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testJwt(){
        String secret = "xuwen";
        Map<String,Object> claims = new HashMap<>();
        claims.put("mobile","123456");
        claims.put("id",3);

        String jwt = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret).compact();
        System.out.println(jwt);

        Map<String, Object> body = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        System.out.println(body);
    }
}
