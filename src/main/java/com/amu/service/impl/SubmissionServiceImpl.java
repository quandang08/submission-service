package com.amu.service.impl;

import com.amu.dto.TaskDto;
import com.amu.entities.Submission;
import com.amu.entities.SubmissionStatus;
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
        if(task != null) {
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionDate(LocalDateTime.now());
            return submissionRepository.save(submission);
        }
        throw new Exception("Task not found with id " + taskId);
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
    public Submission acceptOrDeclineSubmission(Long id, SubmissionStatus status) throws Exception {
        Submission submission = getTaskSubmissionById(id);

        submission.setStatus(status);

        if (status == SubmissionStatus.ACCEPTED) {
            taskService.completeTask(submission.getTaskId());
        }

        return submissionRepository.save(submission);
    }
}
