package com.amu.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long taskId;

    private String githubLink;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status = SubmissionStatus.PENDING;

    private LocalDateTime submissionDate;
}
