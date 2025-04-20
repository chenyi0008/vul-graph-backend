package cn.cheery.backend.controller;

import cn.cheery.backend.common.jwt.JwtUtil;
import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.dto.LoginDto;
import cn.cheery.backend.entity.dto.UserInfoDto;
import cn.cheery.backend.entity.dto.UserRequest;
import cn.cheery.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author c
 * @since 2025-04-16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserRequest request) {
        boolean success = userService.register(request);
        return success ? ApiResponse.success("注册成功") : ApiResponse.fail("注册失败");
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 从 authentication 中获取角色
            String role = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("user");  // 默认角色

            LoginDto dto = new LoginDto();
            dto.setRole(role);
            dto.setToken(jwtUtil.generateToken(authentication.getName(), role));
            return ApiResponse.success("登录成功", dto);
        } catch (BadCredentialsException e) {
            return ApiResponse.fail("用户名或密码错误");
        }
    }

    @GetMapping("/get-info")
    public ApiResponse getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoDto userInfoDto = new UserInfoDto(authentication.getName(), roles.get(0));
        return ApiResponse.success(userInfoDto);
    }

}
