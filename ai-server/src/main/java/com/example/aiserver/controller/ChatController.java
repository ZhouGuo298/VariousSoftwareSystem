package com.example.aiserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aiserver.common.ApiResult;
import com.example.aiserver.entity.PsychologicalChatMessage;
import com.example.aiserver.entity.PsychologicalChatSession;
import com.example.aiserver.mapper.PsychologicalChatMessageMapper;
import com.example.aiserver.mapper.PsychologicalChatSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/psychological-chat")
public class ChatController {
    private final PsychologicalChatSessionMapper sessionMapper;
    private final PsychologicalChatMessageMapper messageMapper;

    @GetMapping("/sessions")
    public ApiResult<Page<PsychologicalChatSession>> sessions(
            @RequestParam(defaultValue = "1") long currentPage,
            @RequestParam(defaultValue = "10") long size
    ) {
        Page<PsychologicalChatSession> page = sessionMapper.selectPage(
                new Page<>(currentPage, size),
                new LambdaQueryWrapper<PsychologicalChatSession>()
                        .orderByDesc(PsychologicalChatSession::getLastMessageTime)
        );
        return ApiResult.success(page);
    }

    @GetMapping("/sessions/{sessionId}/messages")
    public ApiResult<List<PsychologicalChatMessage>> messages(@PathVariable Long sessionId) {
        List<PsychologicalChatMessage> messages = messageMapper.selectList(
                new LambdaQueryWrapper<PsychologicalChatMessage>()
                        .eq(PsychologicalChatMessage::getSessionId, sessionId)
                        .orderByAsc(PsychologicalChatMessage::getCreatedAt)
        );
        return ApiResult.success(messages);
    }
}
