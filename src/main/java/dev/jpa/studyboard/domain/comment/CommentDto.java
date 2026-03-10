package dev.jpa.studyboard.domain.comment;

import lombok.Builder;

@Builder
public record CommentDto (
    int id,
    int parentId,
    String content
) {
}
