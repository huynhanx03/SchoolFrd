package com.scs.post.mapper;

import com.scs.post.dto.response.PostResponse;
import com.scs.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
