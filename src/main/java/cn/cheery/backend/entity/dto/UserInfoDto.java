package cn.cheery.backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/16
 */
@Data
@AllArgsConstructor
public class UserInfoDto {
    String username;
    String role;
}
