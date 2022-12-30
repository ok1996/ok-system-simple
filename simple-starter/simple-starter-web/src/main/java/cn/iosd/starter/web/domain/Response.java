package cn.iosd.starter.web.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * @author ok1996
 */
@Schema(description = "响应信息主体")
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 失败
     */
    public static final int FAIL = 500;

    @Schema(description = "状态码")
    private int code;

    @Schema(description = "响应信息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    public static <T> Response<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> Response<T> ok(T data) {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> Response<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> Response<T> fail() {
        return restResult(null, FAIL, null);
    }

    public static <T> Response<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> Response<T> fail(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> Response<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> Response<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> Response<T> restResult(T data, int code, String msg) {
        Response<T> apiResult = new Response<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Boolean isError(Response<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(Response<T> ret) {
        return Response.SUCCESS == ret.getCode();
    }
}
