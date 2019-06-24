package com.hakine.apps.jwt.controller;

import com.hakine.apps.jwt.domain.TokenInfo;
import com.hakine.apps.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 토큰 관련 컨트롤러
 * @author junghak(rockoil)
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/token")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    /**
     * 토큰 생성 함수
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create")
    public String createJwt() throws Exception {

        // 여러정보를 임의의 모델을 생성하여
        // 여러 정보를 주입
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId("testId");
        tokenInfo.setName("TestName");

        // 해당 정보를 토큰에 추가하여 나중에 필요할경우 추출(MAP 데이터도 상관없음)
        // 토큰 생성
        String token = jwtService.create("tokenInfo", tokenInfo, "TestToken");

        return token;
    }

    /**
     * 토큰 인증 함수
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/auth")
    public boolean authToken(HttpServletRequest request) throws Exception {

        // 헤더에서 토큰을 가져옴.
        String token = request.getHeader("X-Authorization");

        // 실제적으로 토큰을 검증
        return jwtService.validate(token);
    }

    /**
     * 토큰에 있는 정보를 추출
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get")
    public Map getKey() throws Exception {

        // 토큰에 있는 추가적인 정보를 추출
        Map ret = jwtService.get("tokenInfo");

        return ret;
    }

}
