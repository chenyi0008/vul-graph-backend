package cn.cheery.backend.common.handler;

import cn.cheery.backend.common.exception.BusinessException;
import cn.cheery.backend.common.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Date 2025/4/16
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义业务异常
    @ExceptionHandler(BusinessException.class)
    public ApiResponse handleBusinessException(BusinessException ex) {
        return ApiResponse.fail(ex.getMessage());
    }

    // 处理其他未知异常
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception ex) {
        ex.printStackTrace(); // 打印日志
        return ApiResponse.fail(500, "系统内部错误");
    }

}
