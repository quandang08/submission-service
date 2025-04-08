package com.amu.dto;

import com.amu.entities.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private TaskStatus status;
    private Long assignedUserId;
    private List<String> tags;
    private LocalDateTime deadline;
    private LocalDateTime createAt;
}
