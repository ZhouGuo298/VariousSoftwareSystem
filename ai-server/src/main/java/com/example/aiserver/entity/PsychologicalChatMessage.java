package com.example.aiserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("psychological_chat_message")
public class PsychologicalChatMessage {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long sessionId;
    private Integer senderType;
    private String content;
    private String modelName;
    private Integer promptTokens;
    private Integer completionTokens;
    private LocalDateTime createdAt;
    @TableLogic
    private Integer deleted;
}
