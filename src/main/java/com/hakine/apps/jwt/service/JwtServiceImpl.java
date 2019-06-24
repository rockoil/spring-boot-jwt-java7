package com.hakine.apps.jwt.service;

import com.hakine.apps.jwt.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;


import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 토큰 서비스 객체
 * @author junghak(rockoil)
 */
@Service
public class JwtServiceImpl implements JwtService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String secretKey = "com.hakine.apps";

    // token에 사용할 byte를 만들기 위해
    // 서버측에 저장된 salt를 byte형태로 변환
    private byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);

    // jjwt 토큰의 암호화 방식을 지정
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // byte로 된 salt를 이용하여 SHA256형태로 알고리즘 형태로 KEY를 생성
    private final Key KEY= new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    // 토큰 생성
    @Override
    public <T> String create(String key, T data, String subject) {

        String jwt = Jwts.builder()
                // 헤더에 JWT임을 명시
                .setHeaderParam("type", "JWT")
                // 같은 토큰이 생성되는것을 방지하기 위해서 처리
                .setHeaderParam("issueDate", System.currentTimeMillis())
                // PAYLOAD에 입력할 부분
                .setSubject(subject)
                // 키와 데이터를 값을 입력
                .claim(key, data)
                // 토큰 만료 시간을 익일 현재 시간으로 설정
                .setExpiration(new Date(System.currentTimeMillis() + 1 *(1000 * 60 * 60 * 24)))
                // 토큰 사인처리
                .signWith(signatureAlgorithm, KEY)
                .compact();

        return jwt;
    }

    // PAYLOAD 에서 값을 추출함.
    @Override
    public Map<String, Object> get(String key) {

        Map<String, Object> ret = null;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String jwt = request.getHeader("X-Authorization");

        try {

            // 토큰을 파싱처리
            Claims claims = Jwts.parser().setSigningKey(KEY)
                    .parseClaimsJws(jwt).getBody();

            // PAYLOAD에 저장되어 있는 키와 값을 가져옴
            ret = (LinkedHashMap<String, Object>)claims.get(key);

        } catch(Exception e) {
            throw new UnauthorizedException();
        }

        return ret;
    }

    // 토큰 validate
    @Override
    public boolean validate(String jwt) throws Exception {

        try {

            // 토큰을 파싱처리
            // 정상적일 경우 true를 반환하지만
            // 그외에 오류 상황에서는 false를 반환
            Claims claims = Jwts.parser().setSigningKey(KEY)
                    .parseClaimsJws(jwt).getBody();

            logger.info("expireTime : " + claims.getExpiration());

            return true;

        } catch(ExpiredJwtException e) {
            logger.info("Expired Token...");
            return false;
        } catch(JwtException e) {
            logger.info("The token is not pure...");
            return false;
        } catch (Exception e) {
            logger.info("Broken Token");
            return false;
        }

    }


}
