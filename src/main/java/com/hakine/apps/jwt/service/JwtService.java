package com.hakine.apps.jwt.service;

import java.util.Map;

/**
 * 토큰 서비스 인터페이스
 * @author junghak(rockoil)
 */
public interface JwtService {

    /**
     * 토큰 생성 함수
     * @param key
     * @param data
     * @param subject
     * @param <T>
     * @return
     */
    <T> String create(String key, T data, String subject);

    /**
     * 토큰의 PAYLOAD(Body) 부분을 가져오는 함수
     * @param key
     * @return
     */
    Map<String, Object> get(String key);

    /**
     * 토큰 validate 함수
     * @param jwt
     * @return
     */
    boolean validate(String jwt) throws Exception;
}
