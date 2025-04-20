package cn.cheery.backend.entity.dto;

import lombok.Data;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/20
 */
@Data
public class LoginDto {
    private String token;
    private String role;
}
