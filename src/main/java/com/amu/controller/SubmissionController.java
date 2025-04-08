package com.amu.controller;

import com.amu.dto.UserDto;
import com.amu.dto.request.SubmissionRequest;
import com.amu.entities.Submission;
import com.amu.entities.SubmissionStatus;
import com.amu.service.SubmissionService;
import com.amu.service.clients.TaskClient;
import com.amu.service.clients.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserClient userService;

    @Autowired
    private TaskClient taskService;

    private UserDto getUserOrThrow(String jwt) throws Exception {
        UserDto user = userService.getUserProfile(jwt);
        if (user == null) throw new Exception("Cannot fetch user profile from User Service.");
        return user;
    }

    @PostMapping
    public ResponseEntity<Submission> submitTask(
            @RequestBody SubmissionRequest request,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDto user = getUserOrThrow(jwt);
        Submission submission = submissionService.submitTask(
                request.getTaskId(),
                request.getGithubLink(),
                user.getId(),
                jwt
        );
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        getUserOrThrow(jwt); // Optional: check quyền nếu cần
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Submission>> getAllSubmissions(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDto user = getUserOrThrow(jwt);
        // Optional: check quyền
        // if (!user.getRole().equals("ADMIN")) throw new Exception("Access Denied");
        List<Submission> submissions = submissionService.getAllSubmissions();
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getTaskSubmissionsById(
            @PathVariable Long taskId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        getUserOrThrow(jwt);
        List<Submission> submissions = submissionService.getSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(
            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        getUserOrThrow(jwt);
        SubmissionStatus submissionStatus = SubmissionStatus.valueOf(status.toUpperCase());
        Submission updated = submissionService.acceptOrDeclineSubmission(id, submissionStatus);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
