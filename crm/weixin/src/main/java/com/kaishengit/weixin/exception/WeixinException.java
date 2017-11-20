package com.kaishengit.weixin.exception;

/**
 * 微信异常
 */
public class WeixinException extends RuntimeException {

    public WeixinException(){}
    public WeixinException(String message) {
        super(message);
    }



}
