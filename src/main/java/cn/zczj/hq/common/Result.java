package cn.zczj.hq.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;

    // 省略构造方法、getter 和 setter

    // 静态方法：成功返回结果
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }
    // 静态方法：失败返回结果
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

}
