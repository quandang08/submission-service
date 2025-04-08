package com.amu.service.clients;

import com.amu.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "TASK-SERVICE", url = "http://localhost/5002")
public interface TaskClient {

    @GetMapping("/api/tasks/{id}")
    public TaskDto getTaskById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception ;

    @PatchMapping("/api/tasks/{id}/complete")
    public TaskDto completeTask(
            @PathVariable Long id
    ) throws Exception;
}
