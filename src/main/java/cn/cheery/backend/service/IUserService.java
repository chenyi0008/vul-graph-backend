package cn.cheery.backend.service;

import cn.cheery.backend.entity.User;
import cn.cheery.backend.entity.dto.UserRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author c
 * @since 2025-04-16
 */
public interface IUserService extends IService<User> {

    boolean register(UserRequest request);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
