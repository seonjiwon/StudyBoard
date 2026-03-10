package dev.jpa.studyboard.domain.comment;

import dev.jpa.studyboard.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TransactionTemplate transactionTemplate;

    public int saveComment(CommentDto commentDto, int postId, User user) {
        return transactionTemplate.execute(em -> {
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
            return comment.getId();
        });
    }

    public Comment getById(int id) {
        return transactionTemplate.execute(em -> commentRepository.findById(em, id));
    }
}
