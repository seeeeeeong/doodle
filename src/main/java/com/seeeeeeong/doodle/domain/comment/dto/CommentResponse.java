package com.seeeeeeong.doodle.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.core.annotations.QueryProjection;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String comment;
    private Long userId;
    private String userName;
    private Long postId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime deletedAt;

    @QueryProjection
    public CommentResponse(Long commentId, String comment, Long userId,
                           String userName, Long postId, LocalDateTime createdAt,
                           LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.commentId = commentId;
        this.comment = comment;
        this.userId = userId;
        this.userName = userName;
        this.postId = postId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static class Page extends PageImpl<PostResponse> {
        public Page(List<PostResponse> content,
                    Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }
}