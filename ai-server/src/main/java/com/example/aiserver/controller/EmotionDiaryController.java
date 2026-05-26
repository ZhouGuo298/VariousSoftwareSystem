package com.example.aiserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aiserver.common.ApiResult;
import com.example.aiserver.entity.EmotionDiary;
import com.example.aiserver.mapper.EmotionDiaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emotion-diary")
public class EmotionDiaryController {
    private final EmotionDiaryMapper emotionDiaryMapper;

    @GetMapping("/admin/page")
    public ApiResult<Page<EmotionDiary>> adminPage(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer moodScore
    ) {
        Page<EmotionDiary> page = emotionDiaryMapper.selectPage(
                new Page<>(current, size),
                new LambdaQueryWrapper<EmotionDiary>()
                        .eq(userId != null, EmotionDiary::getUserId, userId)
                        .eq(moodScore != null, EmotionDiary::getMoodScore, moodScore)
                        .orderByDesc(EmotionDiary::getCreatedAt)
        );
        return ApiResult.success(page);
    }

    @DeleteMapping("/admin/{id}")
    public ApiResult<Void> delete(@PathVariable Long id) {
        emotionDiaryMapper.deleteById(id);
        return ApiResult.success();
    }
}
