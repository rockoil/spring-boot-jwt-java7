package com.hakine.apps.jwt.domain;

import java.io.Serializable;

/**
 * 토큰 정보 VO
 * @author junghak(rockoil)
 */
public class TokenInfo implements Serializable {

    private static final long serialVersionUID = -348214165109537240L;

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
