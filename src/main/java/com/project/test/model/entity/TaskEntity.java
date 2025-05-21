package com.project.test.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tbl_task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 50)
    private int taskId;
    @Column(name = "user_uuid")
    private UUID userUuid;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_description")
    private String taskDescription;
    @Column(name = "task_completed")
    private Boolean taskCompleted;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false)
    private Date createdDate;
    @Column(name = "created_by", updatable = false)
    private UUID createdBy;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", insertable = false)
    private Date updateDate;
    @Column(name = "updated_by")
    private UUID updatedBy;
}
