package cn.cheery.backend.common.exception;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/16
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
