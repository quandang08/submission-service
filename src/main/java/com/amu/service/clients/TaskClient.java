package com.amu.service.clients;

import com.amu.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "TASK-SERVICE", url = "http://localhost:5002")
public interface TaskClient {

    @GetMapping("/api/tasks/{id}")
    public TaskDto getTaskById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception ;

    @PutMapping("/api/tasks/{id}/complete")
    public TaskDto completeTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception;

    @PutMapping("/api/tasks/{id}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestHeader("Authorization") String jwt
    );
}
