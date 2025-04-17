package cn.cheery.backend.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/16
 */
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "qweqweqweqweqweweweqweqwewqeqweqweqwewqqwewqewqewq"; // 秘钥
    private static final long EXPIRATION = 24000 * 60 * 60; // 24小时

    @Data
    @AllArgsConstructor
    public static class TokenInfo {
        private String username;
        private String role;
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TokenInfo parseTokenToInfo(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return new TokenInfo(
                claims.getSubject(),          // 获取username（subject）
                claims.get("role", String.class) // 获取role声明
        );
    }
}

