package com.project.test.service.impl;

import com.project.test.enums.Message;
import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.dto.TaskResponse;
import com.project.test.model.entity.TaskEntity;
import com.project.test.model.entity.UserEntity;
import com.project.test.repository.TaskRepository;
import com.project.test.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TaskServiceImpl {

    @Autowired
    private RestTemplate template;

    @Autowired
    private TaskRepository repo;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private RestTemplate restTemplate;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<GlobalResponse> findTask(Boolean completed){
        GlobalResponse response = null;
        List<TaskResponse> resp = null;
        try {
            if(completed == null)
                resp = repo.findAllTask();
            else
                resp = repo.findTaskByCompleted(completed);

            if(resp.size() == 0)
                response = new GlobalResponse(404, Message.NOTFOUND.getValue(), null);
            else
                response = new GlobalResponse(200, Message.SUCCESS.getValue(), resp);

        }catch (Exception e){
            log.error("Error find task in " + this.getClass().getName() + " : " , e);
            response = new GlobalResponse(500, Message.ERROR.getValue(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, response.getErrorCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<GlobalResponse> save (TaskEntity request, HttpServletRequest servlet){
        GlobalResponse response = null;
        try {
            UserEntity userEntity = userUtil.getUser(servlet);
            request.setUserUuid(userEntity.getUserUuid());
            request.setCreatedBy(userEntity.getUserUuid());

            TaskEntity entity = repo.save(request);
            response = new GlobalResponse(201, Message.CREATED.getValue(), entity);
        }catch (Exception e){
            log.error("Error save task in " + this.getClass().getName() + " : " , e);
            response = new GlobalResponse(500, Message.ERROR.getValue(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<GlobalResponse> mark (UUID id, TaskEntity request, HttpServletRequest servlet){
        GlobalResponse response = null;
        try {
            UserEntity userEntity = userUtil.getUser(servlet);

            Optional<TaskEntity> task = repo.findByUserUuid(id);
            if(task.isPresent()){
                TaskEntity entity = task.get();
                entity.setUserUuid(userEntity.getUserUuid());
                entity.setTaskCompleted(request.getTaskCompleted());
                entity.setUpdatedBy(userEntity.getUserUuid());
                entity = repo.save(entity);
                response = new GlobalResponse(200, Message.UPDATED.getValue(), entity);
            }else{
                response = new GlobalResponse(404, Message.NOTFOUND.getValue(), null);
            }
        }catch (Exception e){
            log.error("Error mark task in " + this.getClass().getName() + " : " , e);
            response = new GlobalResponse(500, Message.ERROR.getValue(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, response.getErrorCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<GlobalResponse> callApi(String url){
        GlobalResponse response = null;
        try {
            ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
            response = new GlobalResponse(200, Message.SUCCESS.getValue(), resp.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Client error: " + e.getStatusCode() + " - " + e.getMessage());
            response = new GlobalResponse(401, Message.UNAUTHORIZED.getValue(), null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (HttpServerErrorException e) {
            log.error("Server error: " + e.getStatusCode() + " - " + e.getMessage());
            response = new GlobalResponse(503, Message.UNAVAILABLE.getValue(), null);
            return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (Exception e){
            log.error("Error find task in " + this.getClass().getName() + " : " , e);
            response = new GlobalResponse(500, Message.ERROR.getValue(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
