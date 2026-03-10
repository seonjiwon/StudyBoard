package dev.jpa.studyboard.domain.comment;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("부모 댓글 없이 댓글 저장")
    void saveComment_withoutParent() throws Exception{
        // given
        CommentDto dto = CommentDto.builder()
                                   .parentId(-1)
                                   .content("테스트 댓글")
                                   .build();

        // when
        commentService.saveComment(dto, 1, null);

        // then
        Comment result = commentService.getById(1);
        assertNotNull(result);
        assertEquals(result.getContent(), dto.content());
    }

    @Test
    @DisplayName("부모 댓글 있이 댓글 저저아")
    void saveComment_withParent() throws Exception{
        // given
        CommentDto parentDto = CommentDto.builder()
                                   .parentId(-1)
                                   .content("부모 댓글")
                                   .build();

        int parentId = commentService.saveComment(parentDto, 1, null);

        CommentDto childDto = CommentDto.builder()
                                   .parentId(parentId)
                                   .content("대댓글")
                                   .build();

        // when
        int childId = commentService.saveComment(childDto, 1, null);


        // then
        Comment child = commentService.getById(childId);
        assertNotNull(child);
        assertEquals(parentId, child.getParentComment().getId());
        assertEquals("대댓글", child.getContent());
    }
}