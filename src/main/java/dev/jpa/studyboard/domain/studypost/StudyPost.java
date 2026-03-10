package dev.jpa.studyboard.domain.studypost;

import dev.jpa.studyboard.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudyPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    private String category;

    private LocalDateTime postCreatedAt;

    public void update(String title, String content, String category){
        this.title = title;
        this.content = content;
        this.category = category;
        this.postCreatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "StudyPost{" +
                "postId=" + postId +
                ", userId=" + user.getUserId() +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", createdAt=" + postCreatedAt +
                '}';
    }
}