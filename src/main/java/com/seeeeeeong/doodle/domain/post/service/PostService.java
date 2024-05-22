package com.seeeeeeong.doodle.domain.post.service;

import com.seeeeeeong.doodle.common.exception.BusinessException;
import com.seeeeeeong.doodle.common.exception.ErrorCode;
import com.seeeeeeong.doodle.domain.comment.domain.Comment;
import com.seeeeeeong.doodle.domain.comment.dto.CommentResponse;
import com.seeeeeeong.doodle.domain.comment.repository.CommentRepository;
import com.seeeeeeong.doodle.domain.like.domain.Like;
import com.seeeeeeong.doodle.domain.like.repository.LikeRepository;
import com.seeeeeeong.doodle.domain.post.domain.Post;
import com.seeeeeeong.doodle.domain.post.dto.CreatePostResponse;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.post.repository.PostRepository;
import com.seeeeeeong.doodle.domain.user.domain.User;
import com.seeeeeeong.doodle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

        private final UserRepository userRepository;
        private final PostRepository postRepository;
        private final LikeRepository likeRepository;
        private final CommentRepository commentRepository;

        @Transactional
        public CreatePostResponse createPost(Long userId, String title, String body) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

                Post post = Post.of(user, title, body);
                postRepository.save(post);
                return new CreatePostResponse(post.getPostId());
        }

        public Page<PostResponse> list(int size, int page, Direction direction) {
                return postRepository.getPosts(Pageable.ofSize(size).withPage(page), direction);
        }

        public Page<PostResponse> myPosts(Long userId, int size, int page, Direction direction) {
                return postRepository.myPosts(userId, Pageable.ofSize(size).withPage(page), direction);
        }

        @Transactional
        public void updatePost(Long userId, Long postId, String title, String body) {
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

                if(!Objects.equals(post.getUser().getUserId(), userId)) {
                        throw new BusinessException(ErrorCode.INVALID_PERMISSION);
                }

                post.updatePost(title, body);
        }

        @Transactional
        public void deletePost(Long userId, Long postId) {
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

                if(!Objects.equals(post.getUser().getUserId(), userId)) {
                        throw new BusinessException(ErrorCode.INVALID_PERMISSION);
                }

                likeRepository.deleteAllByPost(post);
                commentRepository.deleteAllByPost(post);
                postRepository.delete(post);
        }

        @Transactional
        public void like(Long userId, Long postId) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

                likeRepository.findByUserAndPost(user, post)
                        .ifPresent(it -> {
                                throw new BusinessException(ErrorCode.ALREADY_LIKED_POST);
                        });

                likeRepository.save(Like.of(user, post));
        }

        public Integer getLikes(Long postId) {
                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

                return likeRepository.countByPost(post);
        }

        @Transactional
        public void comment(Long userId, Long postId, String comment) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

                Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

                commentRepository.save(Comment.of(comment, user, post));
        }

        public Page<CommentResponse> getComments(Long postId, int size, int page, Direction direction) {

                postRepository.findById(postId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

                return commentRepository.getComments(postId, Pageable.ofSize(size).withPage(page), direction);
        }
}
