package com.scs.post.service;

import com.scs.post.dto.request.CommentRequest;
import com.scs.post.dto.response.PageResponse;
import com.scs.post.dto.request.PostRequest;
import com.scs.post.dto.response.PostResponse;
import com.scs.post.dto.response.SchoolResponse;
import com.scs.post.dto.response.UserProfileResponse;
import com.scs.post.entity.Comment;
import com.scs.post.entity.Post;
import com.scs.post.mapper.PostMapper;
import com.scs.post.repository.PostRepository;
import com.scs.post.repository.httpclient.ProfileClient;
import com.scs.post.repository.httpclient.SchoolClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    DateTimeFormatter dateTimeFormatter;
    PostRepository postRepository;
    PostMapper postMapper;
    ProfileClient profileClient;
    SchoolClient schoolClient;
    CacheService cacheService;

    @PreAuthorize("hasAuthority('CREATE_POST')")
    public PostResponse createPost(PostRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        SchoolResponse school = null;

        try {
            school = schoolClient.getSchool(request.getSchoolId()).getData();
        } catch (Exception e) {
            log.error("Error while getting school", e);
        }

        Post post = Post.builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .schoolId(request.getSchoolId())
                .build();

        post = postRepository.save(post);
        return postMapper.toPostResponse(post);
    }

    public PostResponse getPost(String id) {
        String cacheKey = "post:" + id;

        Optional<Object> cachedPost = cacheService.get(cacheKey);
        if (cachedPost.isPresent()) {
            return (PostResponse) cachedPost.get();
        }

        PostResponse postResponse = postMapper.toPostResponse(postRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Post not found")));

        cacheService.put(cacheKey, postResponse, 10);
        return postResponse;
    }

    public PageResponse<PostResponse> getMyPosts(int page, int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        UserProfileResponse userProfile = null;

        try {
            userProfile = profileClient.getProfile(userId).getData();
        } catch (Exception e) {
            log.error("Error while getting user profile", e);
        }
        Sort sort = Sort.by("createdDate").descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = postRepository.findAllByUserId(userId, pageable);

        String username = userProfile != null ? userProfile.getFirstName() + " " + userProfile.getLastName() : null;
        var postList = pageData.getContent().stream().map(post -> {
            var postResponse = postMapper.toPostResponse(post);
            postResponse.setCreated(dateTimeFormatter.format(post.getCreatedDate()));
            postResponse.setUsername(username);
            return postResponse;
        }).toList();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }

    public PageResponse<PostResponse> getPosts(int page, int size){
        Sort sort = Sort.by("createdDate").descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = postRepository.findAll(pageable);

        var postList = pageData.getContent().stream().map(post -> {
            UserProfileResponse userProfile = profileClient.getProfile(post.getUserId()).getData();
            SchoolResponse school = schoolClient.getSchool(post.getSchoolId()).getData();

            var postResponse = postMapper.toPostResponse(post);
            postResponse.setCreated(dateTimeFormatter.format(post.getCreatedDate()));
            postResponse.setUsername(userProfile.getFirstName() + " " + userProfile.getLastName());
            postResponse.setSchool(school);

            return postResponse;
        }).toList();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(postList)
                .build();
    }

    public PostResponse createComment(String postId, CommentRequest request) {
        var post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Comment comment = Comment.builder()
                .userId(authentication.getName())
                .content(request.getContent())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();

        post.getComments().add(comment);
        postRepository.save(post);
        return postMapper.toPostResponse(post);
    }

    public PostResponse replyComment(String postId, String commentId, CommentRequest request) {
        var post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Comment parentComment = post.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Comment reply = Comment.builder()
                .userId(authentication.getName())
                .content(request.getContent())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();

        parentComment.getReplies().add(reply);
        postRepository.save(post);
        return postMapper.toPostResponse(post);
    }
}
