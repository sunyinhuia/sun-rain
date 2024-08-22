package top.rain.basic;

import lombok.Data;

@Data
public class R<T> {

    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = -1;

    private int code;

    private T data;

    private String msg;

    private String path;

    private long timestamp;

    private String errorMsg;

    private R() {
        this.msg = "ok";
        this.timestamp = System.currentTimeMillis();
        this.errorMsg = "";
        this.timestamp = System.currentTimeMillis();
    }

    public R(int code, T data, String msg) {
        this.msg = "ok";
        this.timestamp = System.currentTimeMillis();
        this.errorMsg = "";
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public R(int code, T data, String msg, String errorMsg) {
        this(code, data, msg);
        this.errorMsg = errorMsg;
    }

    public static <E> R<E> success(E data) {
        return new R(SUCCESS_CODE, data, "ok");
    }

    public static <E> R<E> success(E data, String msg) {
        return new R(SUCCESS_CODE, data, msg);
    }

    public static <E> R<E> fail(String msg) {
        return new R(FAIL_CODE, (Object)null, msg != null && !msg.isEmpty() ? msg : "系统繁忙，请稍候再试");
    }

    public static <E> R<E> fail(int code, String msg) {
        return new R(code, (Object)null, msg != null && !msg.isEmpty() ? msg : "系统繁忙，请稍候再试");
    }

    public static <E> R<E> result(int code, E data, String msg) {
        return new R(code, data, msg);
    }

    public static <E> R<E> result(int code, E data, String msg, String errorMsg) {
        return new R(code, data, msg, errorMsg);
    }
}
