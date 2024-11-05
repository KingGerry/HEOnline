package com.hy.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class RoleTasks {
    public RoleTasks(Integer _taskId, Integer _taskProcess){
        this.taskId = _taskId;
        this.taskProcess = _taskProcess;
    }
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer roleId;

    /**
     *
     */
    private Integer taskId;

    /**
     *
     */
    private Integer taskProcess;

    /**
     *
     */
    private Date completedTime;
}

