package dev.jpa.studyboard.domain.comment;

import dev.jpa.studyboard.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final JpaTransactionExecutor jpaTransactionExecutor;

    public int saveComment(CommentDto commentDto, int postId, User user) {
        return jpaTransactionExecutor.execute(em -> {
            Comment comment;

            if (commentDto.parentId() != -1) {
                Comment parent = commentRepository.findById(em, commentDto.parentId());
                comment = Comment.builder()
                                 .content(commentDto.content())
                                 .parentComment(parent)
                                 .user(user)
                                 .build();
            } else {
                comment = Comment.builder()
                                 .content(commentDto.content())
                                 .user(user)
                                 .build();
            }
            commentRepository.save(em, comment);
            return comment.getId();
        });
    }

    public Comment getById(int id) {
        return jpaTransactionExecutor.execute(em -> commentRepository.findById(em, id));
    }
}
