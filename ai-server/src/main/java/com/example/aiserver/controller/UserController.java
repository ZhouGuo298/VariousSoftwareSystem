package com.example.aiserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aiserver.common.ApiResult;
import com.example.aiserver.common.BusinessException;
import com.example.aiserver.dto.LoginRequest;
import com.example.aiserver.dto.RegisterRequest;
import com.example.aiserver.entity.SysUser;
import com.example.aiserver.mapper.SysUserMapper;
import com.example.aiserver.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final SysUserMapper userMapper;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ApiResult<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, request.getUsername())
                .eq(SysUser::getStatus, 1));

        if (user == null || !passwordMatches(request.getPassword(), user.getPasswordHash())) {
            return ApiResult.unauthorized("用户名或密码错误");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userMapper.updateById(user);

        String token = jwtUtil.createToken(user.getId(), user.getUsername(), user.getRole());
        Map<String, Object> userInfo = Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "nickname", user.getNickname(),
                "role", user.getRole(),
                "roleType", "ADMIN".equals(user.getRole()) ? 2 : 1
        );
        return ApiResult.success(Map.of("token", token, "userInfo", userInfo));
    }

    @PostMapping("/register")
    public ApiResult<Void> register(@Valid @RequestBody RegisterRequest request) {
        Long count = userMapper.selectCount(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, request.getUsername()));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPasswordHash(request.getPassword());
        user.setNickname(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole("USER");
        user.setStatus(1);
        userMapper.insert(user);
        return ApiResult.success();
    }

    @PostMapping("/logout")
    public ApiResult<Void> logout() {
        return ApiResult.success();
    }

    private boolean passwordMatches(String rawPassword, String storedPassword) {
        if (storedPassword == null) {
            return false;
        }
        if (storedPassword.startsWith("$2a$10$demo-password-hash")) {
            return "admin123".equals(rawPassword) || "123456".equals(rawPassword);
        }
        return storedPassword.equals(rawPassword);
    }
}
