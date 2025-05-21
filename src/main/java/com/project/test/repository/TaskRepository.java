package com.project.test.repository;


import com.project.test.model.dto.TaskResponse;
import com.project.test.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("select e.userUuid as userUUID, e.name as username," +
            "te.taskName as taskName, te.taskDescription as taskDescription," +
            "te.taskCompleted as taskCompleted from UserEntity e, TaskEntity te " +
            "where e.userUuid = te.userUuid and te.taskCompleted = :taskCompleted")
    List<TaskResponse> findTaskByCompleted(Boolean taskCompleted);

    @Query("select e.userUuid as userUUID, e.name as username," +
            "te.taskName as taskName, te.taskDescription as taskDescription," +
            "te.taskCompleted as taskCompleted from UserEntity e, TaskEntity te " +
            "where e.userUuid = te.userUuid")
    List<TaskResponse> findAllTask();

    Optional<TaskEntity> findByUserUuid(UUID id);
}
