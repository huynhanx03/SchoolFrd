package com.scs.post.dto.response;

import com.scs.post.entity.Comment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CommentResponse {
    String id;
    String userId;
    String content;
    Instant createdDate;
    Instant modifiedDate;
    List<Comment> replies;
}
