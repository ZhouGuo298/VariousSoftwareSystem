package com.example.aiserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("emotion_diary")
public class EmotionDiary {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String username;
    private String nickname;
    private LocalDate diaryDate;
    private Integer moodScore;
    private String dominantEmotion;
    private Integer sleepQuality;
    private Integer stressLevel;
    private String emotionTriggers;
    private String diaryContent;
    private String aiEmotionAnalysis;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @TableLogic
    private Integer deleted;
}
