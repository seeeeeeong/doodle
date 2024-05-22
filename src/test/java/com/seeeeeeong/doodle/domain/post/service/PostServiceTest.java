package com.seeeeeeong.doodle.domain.post.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.domain.comment.domain.Comment;
import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import com.seeeeeeong.doodle.domain.comment.repository.CommentRepository;
import com.seeeeeeong.doodle.domain.like.domain.Like;
import com.seeeeeeong.doodle.domain.like.repository.LikeRepository;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.post.repository.PostRepository;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LikeRepository likeRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void createPost() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.save(post)).thenReturn(post);

        // then
        assertDoesNotThrow(() -> postService.createPost(user.getUserId(), title, body));
    }

    @Test
    void createPost_user_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());


        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.createPost(user.getUserId(), title, body));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void list() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;
        List<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse(1L, title, body, user));


        // when
        when(postRepository.getPosts(Pageable.ofSize(size).withPage(page), direction)).thenReturn(new PageImpl<>(posts));

        // then
        assertDoesNotThrow(() -> postService.list(size, page, direction));
    }

    @Test
    void myPosts() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;
        List<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse(1L, title, body, user));


        // when
        when(postRepository.myPosts(user.getUserId(), Pageable.ofSize(size).withPage(page), direction)).thenReturn(new PageImpl<>(posts));

        // then
        assertDoesNotThrow(() -> postService.myPosts(user.getUserId(), size, page, direction));
    }

    @Test
    void updatePost() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));

        // then
        assertDoesNotThrow(() -> postService.updatePost(user.getUserId(), post.getPostId(), title, body));
    }

    @Test
    void updatePost_post_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.updatePost(user.getUserId(), post.getPostId(), title, body));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void deletePost() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        doNothing().when(postRepository).delete(post);

        // then
        assertDoesNotThrow(() -> postService.updatePost(user.getUserId(), post.getPostId(), title, body));
    }

    @Test
    void deletePost_post_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());
        doNothing().when(postRepository).delete(post);

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.deletePost(user.getUserId(), post.getPostId()));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());

    }

    @Test
    void like() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());
        when(likeRepository.save(Like.of(user, post))).thenReturn(Like.of(user, post));

        // then
        assertDoesNotThrow(() -> postService.like(user.getUserId(), post.getPostId()));
    }

    @Test
    void like_user_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());
        when(likeRepository.save(Like.of(user, post))).thenReturn(Like.of(user, post));

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.like(user.getUserId(), post.getPostId()));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void like_post_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());
        when(likeRepository.save(Like.of(user, post))).thenReturn(Like.of(user, post));

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.like(user.getUserId(), post.getPostId()));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void like_already_liked_post() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(Like.of(user, post)));
        when(likeRepository.save(Like.of(user, post))).thenReturn(Like.of(user, post));

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.like(user.getUserId(), post.getPostId()));

        assertEquals(ErrorCode.ALREADY_LIKED_POST, exception.getErrorCode());
    }

    @Test
    void getLikes() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(likeRepository.countByPost(post)).thenReturn(1);

        // then
        assertDoesNotThrow(() -> postService.getLikes(post.getPostId()));
    }

    @Test
    void getLikes_post_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());
        when(likeRepository.countByPost(post)).thenReturn(1);

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.getLikes(post.getPostId()));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void comment() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);
        Comment comment = Comment.of("comment", user, post);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);

        // then
        assertDoesNotThrow(() -> postService.comment(user.getUserId(), post.getPostId(), "comment"));
    }

    @Test
    void comment_user_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);
        Comment comment = Comment.of("comment", user, post);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.comment(user.getUserId(), post.getPostId(), "comment"));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void comment_post_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        Post post = Post.of(user, title, body);
        Comment comment = Comment.of("comment", user, post);

        // when
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());
        when(commentRepository.save(comment)).thenReturn(comment);

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.comment(user.getUserId(), post.getPostId(), "comment"));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void getComments() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;

        Post post = Post.of(user, title, body);

        Comment comment = Comment.of("comment", user, post);

        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(comment.getCommentId(), comment.getComment(), user.getUserId(), user.getUserName(), post.getPostId(), LocalDateTime.now(), null, null));

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(commentRepository.getComments(post.getPostId(), Pageable.ofSize(size).withPage(page), direction)).thenReturn(new PageImpl<>(comments));

        // then
        assertDoesNotThrow(() -> postService.getComments(post.getPostId(), size, page, direction));
    }

    @Test
    void getComment_post_not_found() {
        // given
        User user = User.create("userName", "password");

        String title = "title";
        String body = "body";

        int size = 20;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;

        Post post = Post.of(user, title, body);

        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(1L, "comment", user.getUserId(), user.getUserName(), post.getPostId(), LocalDateTime.now(), null, null));

        // when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.empty());
        when(commentRepository.getComments(post.getPostId(), Pageable.ofSize(size).withPage(page), direction)).thenReturn(new PageImpl<>(comments));

        // then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> postService.getComments(post.getPostId(), size, page, direction));

        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }
}
