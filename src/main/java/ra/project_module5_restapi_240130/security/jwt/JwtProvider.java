package ra.project_module5_restapi_240130.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j

public class JwtProvider {
    @Value("${ra.jwt.secret}")
    //Lưu ý lấy value từ Org ko phải từ lombok lấy ra giá trị secret JapanNagoya
    private String JWT_SECRET;//JapanNagoya
    @Value("${ra.jwt.expired.access.token}")
    private long JWT_ACCESS_TOKEN;
    @Value("${ra.jwt.expired.refresh.token}")
    private long JWT_REFRESH_TOKEN;

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())//Nội dung sinh token
                //getUsername lấy từ trong database
                .setIssuedAt(new Date())//Ngày hiệu lực
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
            //Nếu ko có lỗi thì cho thực hiện

        } catch (ExpiredJwtException e) {
            log.error("Failed -> Expired Token Message {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Failed -> Unsupported Token Message {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Failed -> Invalid Format Token Message {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Failed -> Claims Empty Token Message {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody().getSubject();
    }


}
