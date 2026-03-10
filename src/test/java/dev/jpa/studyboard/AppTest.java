package dev.jpa.studyboard;

import dev.jpa.studyboard.domain.comment.Comment;
import dev.jpa.studyboard.domain.comment.CommentService;
import dev.jpa.studyboard.domain.comment.JpaTransactionExecutor;
import dev.jpa.studyboard.domain.studypost.StudyPost;
import dev.jpa.studyboard.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class AppTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JpaTransactionExecutor jpaTransactionExecutor;

    @Test
    @DisplayName("1+N 문제 확인 - Comment 조회 시 User 각각 SELECT")
    void verifyNPlus1Problem() {
        // given - 서로 다른 User 3명이 같은 Post에 댓글 작성
        jpaTransactionExecutor.execute(em -> {
            User user1 = new User("유저1");
            User user2 = new User("유저2");
            User user3 = new User("유저3");
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

            StudyPost post = StudyPost.builder()
                                      .title("테스트 게시글")
                                      .content("내용")
                                      .category("자유")
                                      .build();
            em.persist(post);

            em.persist(Comment.builder().content("댓글1").post(post).user(user1).build());
            em.persist(Comment.builder().content("댓글2").post(post).user(user2).build());
            em.persist(Comment.builder().content("댓글3").post(post).user(user3).build());
            return null;
        });

        // when - 새 영속성 컨텍스트에서 댓글 목록 조회
        System.out.println("========== 1+N 쿼리 시작 ==========");
        jpaTransactionExecutor.execute(em -> {
            List<Comment> comments = em.createQuery(
                                           "select c from Comment c where c.post.id = :postId", Comment.class
                                       ).setParameter("postId", 1)
                                       .getResultList();

            for (Comment c : comments) {
                System.out.println(c.getContent());  // User 접근 없음
            }
            return null;
        });
        System.out.println("========== 1+N 쿼리 끝 ==========");
    }
}
