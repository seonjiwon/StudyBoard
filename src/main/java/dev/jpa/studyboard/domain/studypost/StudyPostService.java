package dev.jpa.studyboard.domain.studypost;

import dev.jpa.studyboard.domain.studypost.dto.StudyPostSaveRequest;
import dev.jpa.studyboard.domain.studypost.dto.StudyPostUpdateRequest;
import dev.jpa.studyboard.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;

public class StudyPostService {

    private final EntityManager manager;

    public StudyPostService(EntityManager manager){
        this.manager = manager;
    }

    public void savePost(StudyPostSaveRequest request) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        User user = manager.find(User.class, request.getUserId());

        StudyPost post = StudyPost.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .postCreatedAt(LocalDateTime.now())
                .build();

        manager.persist(post);
        tx.commit();
    }

    public StudyPost findPost(int postId) {
        return manager.find(StudyPost.class, postId);
    }

    public List<StudyPost> findAll() {
        return manager.createQuery(
                "select p from StudyPost p", StudyPost.class
        ).getResultList();
    }

    public void updatePost(int postId, StudyPostUpdateRequest request) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        StudyPost post = manager.find(StudyPost.class, postId);

        if (post != null) {
            post.update(
                    request.getTitle(),
                    request.getContent(),
                    request.getCategory()
            );
        }

        tx.commit();
    }

    public void deletePost(int postId) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        StudyPost post = manager.find(StudyPost.class, postId);

        if (post != null) {
            manager.remove(post);
        }

        tx.commit();
    }
}