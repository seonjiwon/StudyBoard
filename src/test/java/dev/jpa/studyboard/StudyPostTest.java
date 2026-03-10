package dev.jpa.studyboard;

import dev.jpa.studyboard.domain.studypost.StudyPost;
import dev.jpa.studyboard.domain.studypost.StudyPostService;
import dev.jpa.studyboard.domain.studypost.dto.StudyPostSaveRequest;
import dev.jpa.studyboard.domain.studypost.dto.StudyPostUpdateRequest;
import dev.jpa.studyboard.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StudyPostTest {

    EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("hello-jpa");

    EntityManager manager = factory.createEntityManager();

    StudyPostService service = new StudyPostService(manager);

    private int createUser(String name) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        User user = User.builder()
                .userName(name)
                .build();

        manager.persist(user);

        tx.commit();

        return user.getUserId();
    }

    @Test
    @DisplayName("게시글 등록")
    void 게시글등록() {
        int userId = createUser("건희");

        StudyPostSaveRequest request = new StudyPostSaveRequest(
                userId,
                "JPA 스터디 모집",
                "같이 공부하실 분",
                "BACKEND"
        );

        service.savePost(request);

        List<StudyPost> posts = service.findAll();
        System.out.println("게시글 목록 = " + posts);
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void 게시글단건조회() {
        int userId = createUser("건희");
        StudyPostSaveRequest request = new StudyPostSaveRequest(
                userId,
                "알고리즘 스터디 모집",
                "백준 같이 풀 사람 구합니다",
                "ALGORITHM"
        );

        service.savePost(request);

        List<StudyPost> posts = service.findAll();
        StudyPost savedPost = posts.get(posts.size() - 1);

        StudyPost foundPost = service.findPost(savedPost.getId());
        System.out.println("조회된 게시글 = " + foundPost);
    }

    @Test
    @DisplayName("전체 게시글 조회")
    void 전체게시글조회() {
        int userId1 = createUser("민정");
        int userId2 = createUser("지원");

        service.savePost(new StudyPostSaveRequest(
                userId1,
                "JPA 스터디",
                "JPA 기본 개념 공부",
                "BACKEND"
        ));

        service.savePost(new StudyPostSaveRequest(
                userId2,
                "CS 스터디",
                "운영체제 같이 공부",
                "CS"
        ));

        List<StudyPost> posts = service.findAll();

        for (StudyPost post : posts) {
            System.out.println(post);
        }
    }

    @Test
    @DisplayName("게시글 수정")
    void 게시글수정() {
        User user = User.builder()
                .userName("건희")
                .build();

        service.savePost(new StudyPostSaveRequest(
                user.getUserId(),
                "수정 전 제목",
                "수정 전 내용",
                "ETC"
        ));

        List<StudyPost> posts = service.findAll();
        StudyPost savedPost = posts.get(posts.size() - 1);

        StudyPostUpdateRequest updateRequest = new StudyPostUpdateRequest(
                "수정 후 제목",
                "수정 후 내용",
                "BACKEND"
        );

        service.updatePost(savedPost.getId(), updateRequest);

        StudyPost updatedPost = service.findPost(savedPost.getId());
        System.out.println("수정된 게시글 = " + updatedPost);
    }

    @Test
    @DisplayName("게시글 삭제")
    void 게시글삭제() {
        int userId = createUser("건희");

        service.savePost(new StudyPostSaveRequest(
                userId,
                "삭제할 게시글",
                "곧 삭제될 내용",
                "ETC"
        ));

        List<StudyPost> posts = service.findAll();
        StudyPost savedPost = posts.get(posts.size() - 1);

        service.deletePost(savedPost.getId());

        StudyPost deletedPost = service.findPost(savedPost.getId());
        System.out.println("삭제 후 조회 결과 = " + deletedPost);
    }
}