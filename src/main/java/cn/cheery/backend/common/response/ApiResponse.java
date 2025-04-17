package cn.cheery.backend.common.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private Integer code;
    private String msg;
    private Object data;

    public static ApiResponse success() {
        return new ApiResponse(1, "success", null);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(1, "success", data);
    }

    public static ApiResponse success(String message) {
        return new ApiResponse(1, message, null);
    }

    public static  ApiResponse success(String msg, Object data) {
        return new ApiResponse(1, msg, data);
    }

    public static  ApiResponse fail(String msg) {
        return new ApiResponse(0, msg, null);
    }

    public static  ApiResponse fail(Integer code, String msg) {
        return new ApiResponse(code, msg, null);
    }
}