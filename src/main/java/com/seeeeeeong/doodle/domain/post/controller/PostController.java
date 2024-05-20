package com.seeeeeeong.doodle.domain.post.controller;

import com.seeeeeeong.doodle.common.resolver.AuthUser;
import com.seeeeeeong.doodle.common.response.SuccessResponse;
import com.seeeeeeong.doodle.common.security.jwt.JwtTokenInfo;
import com.seeeeeeong.doodle.domain.post.dto.CreatePostRequest;
import com.seeeeeeong.doodle.domain.post.dto.CreatePostResponse;
import com.seeeeeeong.doodle.domain.post.dto.PostResponse;
import com.seeeeeeong.doodle.domain.post.dto.UpdatePostRequest;
import com.seeeeeeong.doodle.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreatePostResponse>> createPost(@AuthUser JwtTokenInfo tokenInfo,
                                                                          @RequestBody CreatePostRequest request) {
        return SuccessResponse.of(postService.createPost(tokenInfo.getUserId(), request.getTitle(), request.getBody())).asHttp(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<Page<PostResponse>>> list(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                    @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                    @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(postService.list(size, page, direction)).asHttp(HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<SuccessResponse<Page<PostResponse>>> myPosts(@AuthUser JwtTokenInfo jwtTokenInfo,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                       @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction direction) {
        return SuccessResponse.of(postService.myPosts(jwtTokenInfo.getUserId(), size, page, direction)).asHttp(HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@AuthUser JwtTokenInfo jwtTokenInfo,
                                           @PathVariable Long postId,
                                           @RequestBody UpdatePostRequest request) {
        postService.updatePost(jwtTokenInfo.getUserId(), postId, request.getTitle(), request.getBody());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@AuthUser JwtTokenInfo jwtTokenInfo,
                                           @PathVariable Long postId) {
        postService.deletePost(jwtTokenInfo.getUserId(), postId);
        return ResponseEntity.noContent().build();
    }
}
