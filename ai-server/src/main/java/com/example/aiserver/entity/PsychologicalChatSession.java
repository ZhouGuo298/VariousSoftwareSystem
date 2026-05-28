package com.example.aiserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("psychological_chat_session")
public class PsychologicalChatSession {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String userNickname;
    private String sessionTitle;
    private String lastMessageContent;
    private Integer messageCount;
    private LocalDateTime startedAt;
    private LocalDateTime lastMessageTime;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @TableLogic
    private Integer deleted;
}
