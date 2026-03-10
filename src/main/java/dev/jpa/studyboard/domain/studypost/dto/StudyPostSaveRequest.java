package dev.jpa.studyboard.domain.studypost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyPostSaveRequest {
    private int userId;
    private String title;
    private String content;
    private String category;
}