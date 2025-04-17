package cn.cheery.backend.service.impl;

import cn.cheery.backend.common.exception.BusinessException;
import cn.cheery.backend.entity.User;
import cn.cheery.backend.entity.dto.UserRequest;
import cn.cheery.backend.mapper.UserMapper;
import cn.cheery.backend.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author c
 * @since 2025-04-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, UserDetailsService {

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean register(UserRequest request) throws BusinessException {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        if (this.count(wrapper) > 0) {
            throw new BusinessException("用户已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(request.getUsername());

        // 这里建议对密码加密处理，比如 BCrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("user"); // 默认角色
        user.setCreateTime(LocalDateTime.now());

        return this.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);

        // 查数据库获取用户信息
        User user = this.getOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }


        // 返回 Spring Security 的 UserDetails 对象
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }

}
