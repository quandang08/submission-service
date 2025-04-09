package com.amu.service.impl;

import com.amu.dto.TaskDto;
import com.amu.dto.UserDto;
import com.amu.entities.Submission;
import com.amu.entities.SubmissionStatus;
import com.amu.entities.TaskStatus;
import com.amu.repositories.SubmissionRepository;
import com.amu.service.SubmissionService;
import com.amu.service.clients.TaskClient;
import com.amu.service.clients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskClient taskService;

    @Autowired
    private UserClient userService;

    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDto task = taskService.getTaskById(jwt, taskId);
        if (task == null) {
            throw new Exception("Task not found with id " + taskId);
        }

        Submission submission = new Submission();
        submission.setTaskId(taskId);
        submission.setUserId(userId);
        submission.setGithubLink(githubLink);
        submission.setSubmissionDate(LocalDateTime.now());

        return submissionRepository.save(submission);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(()
                -> new Exception("Submission not found with id " + submissionId));
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getSubmissionsByTaskId(Long taskId) throws Exception {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptOrDeclineSubmission(Long id, SubmissionStatus status, String jwt) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        TaskDto task = taskService.getTaskById(jwt, submission.getTaskId());

        if (SubmissionStatus.ACCEPTED.equals(status)) {
            if (!task.getStatus().equals(TaskStatus.DONE)) {
                taskService.updateTaskStatus(task.getId(), TaskStatus.DONE.name(), jwt);
            }
        } else if (SubmissionStatus.REJECTED.equals(status)) {
            taskService.updateTaskStatus(task.getId(), TaskStatus.ASSIGNED.name(), jwt);
        }

        return submissionRepository.save(submission);
    }

}
