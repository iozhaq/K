package com.kaishengit.web.result;

/**
 * Ajax请求的返回结果，一般在控制器中使用
 * @author fankay
 */
public class AjaxResult {

    public static final String STATE_SUCCESS = "success";
    public static final String STATE_ERROR = "error";

    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(STATE_SUCCESS);
        return ajaxResult;
    }
    public static AjaxResult successWithData(Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(STATE_SUCCESS);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResult error(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(STATE_ERROR);
        ajaxResult.setMessage(message);
        return ajaxResult;
    }

    private String state;
    private String message;
    private Object data;

    public AjaxResult(){}

    public AjaxResult(String state) {
        this.state = state;
    }

    public AjaxResult(String state,Object data) {
        this.state = state;
        this.data = data;
    }

    public AjaxResult(String state,String message) {
        this.state = state;
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
