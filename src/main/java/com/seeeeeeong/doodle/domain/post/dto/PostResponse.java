package com.seeeeeeong.doodle.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponse {

    private final Long postId;
    private final String title;
    private final String body;
    private final UserResponse user;

    @QueryProjection
    public PostResponse(Long postId, String title, String body, User user) {
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.user = UserResponse.of(user);
    }

    public static class Page extends PageImpl<PostResponse> {
        public Page(List<PostResponse> content,
                    Pageable pageable, long total) {
            super(content, pageable, total);
        }

    }
}
