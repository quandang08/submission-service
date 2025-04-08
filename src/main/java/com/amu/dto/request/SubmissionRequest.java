package com.amu.dto.request;

import lombok.Data;

@Data
public class SubmissionRequest {
    private Long taskId;
    private String githubLink;
}
