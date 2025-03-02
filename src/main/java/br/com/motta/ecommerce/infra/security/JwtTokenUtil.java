package br.com.motta.ecommerce.infra.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    // Gerando a chave secreta segura com 512 bits para o HS512
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long jwtExpirationMs = 86400000; // 24 horas de expiração

    public String generateToken(UsuarioDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", userDetails.getId())
                .claim("role", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret)  // Usando a chave secreta gerada
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token); // Usando a chave secreta para validação
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Trate a exceção conforme necessário
        }
        return false;
    }
}