package dev.jpa.studyboard.domain.comment;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {
    public Comment findById(EntityManager em, int id) {
        return em.find(Comment.class, id);
    }

    public void save(EntityManager em, Comment comment) {
        em.persist(comment);
    }
}
