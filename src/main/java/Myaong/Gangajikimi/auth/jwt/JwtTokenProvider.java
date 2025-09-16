package Myaong.Gangajikimi.auth.jwt;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final String AUTH_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    @Value("${jwt.access.secretKey}")
    private String secretKey;
    @Value("${jwt.access.expiration}")
    public Long jwtExpiration;
    @Value("${jwt.refresh.expiration}")
    public Long refreshExpiration;
    private final MemberRepository memberRepository;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 사용자 정보를 바탕으로 토큰 생성
    public String generateAccessToken(Authentication authentication){
        Object principal = authentication.getPrincipal();
        Long memberId;
        String role;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            memberId = userDetails.getId();
            role = userDetails.getAuthorities().iterator().next().getAuthority();
        } else {
            // fallback: principal이 String(email)일 때
            String email = principal.toString();
            Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));
            memberId = member.getId();
            role = member.getRole().toString();
        }

        log.info("{} : 토큰 생성 완료", memberId);

        return Jwts.builder()
                // 토큰 제목 설정
                .setSubject(memberId.toString())
                // Payload의 role claim 정보를 입력
                .claim("role", role)
                // 발급 날짜 설정
                .setIssuedAt(new Date())
                // 만료 기한 설정
                .setExpiration(new Date(System.currentTimeMillis()+ jwtExpiration))
                // SecretKey로 서명
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                // Jwt 토큰으로 직렬화
                .compact();
    }

    public String regenerateAccessToken(Member member){

        String email = member.getEmail();

        String role = member.getRole().toString();

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    // 토큰의 유효성 검사
    public void validateJwtToken(String token){

        if (token == null){
            throw new JwtException("토큰이 존재하지 않습니다.");
        }

        Claims claims = parseClaimsFromToken(token);

        Date expirationDate = claims.getExpiration();
        try{
            expirationDate.before(new Date());
        }catch (ExpiredJwtException e){
            // Authentication Filter에서 예외 발생 시 컨트롤러 진입 전이기 때문에 전역 예외 처리기가 작동 X
            log.warn("{} : 토큰 기한 만료", claims.getSubject());
        }
    }

    // Jwt 토큰으로부터 Spring Security의 Authentication 객체를 추출
    public Authentication getAuthenticationFromToken(String token){

        validateJwtToken(token);

        Claims claims = parseClaimsFromToken(token);

        String id = claims.getSubject();      // memberId
        Long memberId = Long.parseLong(id);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member member = optionalMember.orElseThrow(()-> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        CustomUserDetails principal = new CustomUserDetails(member);

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public String getTokenFromRequest(HttpServletRequest request){

        String accessToken = request.getHeader(AUTH_HEADER);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(TOKEN_PREFIX)){
            return accessToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Claims parseClaimsFromToken(String token){
        return  // 파서 객체 생성
                Jwts.parserBuilder()
                        // SecretKey를 통해 Signature 검증
                        .setSigningKey(getSigningKey())
                        .build()
                        // token 파싱, 검증 및 Base64 디코딩
                        .parseClaimsJws(token)
                        .getBody();
    }

    public String generateRefreshToken(Long memberId){

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(memberId.toString())
                .setExpiration(new Date(System.currentTimeMillis()+ refreshExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRefreshToken(String token) {

        Claims claims = parseClaimsFromToken(token);

        Date expirationDate = claims.getExpiration();

        if (expirationDate.before(new Date())) {
            return false;
        }
        return true;
    }

    public Long extractMemberIdFromRefreshToken(String token){

        Claims claims = parseClaimsFromToken(token);

        String userId = claims.getSubject();

        return Long.parseLong(userId);
    }
}