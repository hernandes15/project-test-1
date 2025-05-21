package com.project.test.model.dto;


public interface TaskResponse {
    String getUserUUID();
    String getUsername();
    String getTaskName();
    String getTaskDescription();
    Boolean getTaskCompleted();
}
