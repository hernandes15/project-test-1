package com.project.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.test.config.security.JwtTokenProvider;
import com.project.test.model.entity.TaskEntity;
import com.project.test.repository.TaskRepository;
import com.project.test.repository.UserRepository;
import com.project.test.service.impl.TaskServiceImpl;
import com.project.test.util.BaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private UserRepository repoUser;

    @Autowired
    private TaskServiceImpl service;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private BaseHelper helper;

    @GetMapping("/find-all")
    public ResponseEntity<?>  findAll() throws Exception {
        return taskService.findTask(null);
    }

    @GetMapping("/find-task/{completed}")
    public ResponseEntity<?>  findByCompleted(@PathVariable Boolean completed) throws Exception {
        return taskService.findTask(completed);
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTask(HttpServletRequest request, @RequestBody TaskEntity param) throws Exception {
        return taskService.save(param, request);
    }

    @PutMapping(path = "/mark/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> markTask(HttpServletRequest request, @PathVariable UUID id, @RequestBody TaskEntity param) throws Exception {
        return taskService.mark(id, param, request);
    }

    @PostMapping(path = "/sum-even", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sum(HttpServletRequest request, @RequestBody int[] numbers) throws Exception {
        int sum = 0;
        for (int num : numbers) {
            if (num % 2 == 0) {
                sum += num;
            }
        }
        return "Sum of even number is : " + sum;
    }

    @PostMapping(path = "/rest-template", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> callApi(HttpServletRequest request, @RequestBody String url) throws Exception {
        return taskService.callApi(url);
    }

}
