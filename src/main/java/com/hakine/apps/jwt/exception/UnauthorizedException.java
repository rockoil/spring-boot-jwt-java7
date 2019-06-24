package com.hakine.apps.jwt.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -465446307386867380L;

    public UnauthorizedException() {
        super("해당 권한이 유효하지 않습니다. 토큰을 재생성해주세요.");
    }
}
