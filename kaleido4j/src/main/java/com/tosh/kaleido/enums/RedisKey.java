package com.tosh.kaleido.enums;

import lombok.Getter;
@Getter
public enum RedisKey {
    USER_REGISTER("user", "register"),
    USER_FORGET_PWD("user", "forget"),
    CODE_VERIFY("code", "verify"),
    PHONE_RATE("code", "rate"),
    ACCESS_TOKEN("access", "token"),
    DATE_SEQ("date", "seq"),
    ;

    private String module;
    private String func;

    RedisKey(String module, String func) {
        this.module = module;
        this.func = func;
    }
}
