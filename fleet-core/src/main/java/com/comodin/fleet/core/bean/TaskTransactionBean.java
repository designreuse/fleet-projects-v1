package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Task transaction bean.
 */
@Table(name = "t_task_transaction")
public class TaskTransactionBean {
    /**
     * 任务交易记录，ID
     */
    @Id
    @Column(name = "transaction_id")
    private Integer id;

    /**
     * 任务交易记录，任务ID；字段类型：INT(11)；值：NOT NULL【关联task表中的task_id】；默认值：无
     */
    @Column(name = "transaction_task_id")
    private Integer taskId;

    /**
     * 任务交易记录，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     */
    @Column(name = "transaction_operator_id")
    private Integer operatorId;

    /**
     * 任务交易记录，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     */
    @Column(name = "transaction_operator_name")
    private String operatorName;

    /**
     * 任务交易记录，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait_Assignment、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 settlement、fail、success】，默认值：无
     */
    @Column(name = "transaction_operate_type")
    private String operateType;

    /**
     * 任务交易记录，操作的内容，字段类型：VARCHAR(150)；值：NOT NULL【根据任务操作类型不同，所产生的】；默认值：无
     */
    @Column(name = "transaction_operate_comment")
    private String operateComment;

    /**
     * 任务交易记录，操作的时间，字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：无
     */
    @Column(name = "transaction_operate_time")
    private Date operateTime;

    /**
     * 任务交易记录，责任人，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    @Column(name = "transaction_task_ownev_id")
    private Integer taskOwnevId;

    /**
     * 任务交易记录，责任人，name；字段类型：VARCHAR(100) 值：NOT NULL；默认值：无
     */
    @Column(name = "transaction_task_ownev_name")
    private String taskOwnevName;

    /**
     * 任务交易记录，责任人的时间，字段类型：DATETIME；值：NOT NULL【一般情况下与操作的时间一致，其他情况：在责任人不是当前操作人的情况下，更新操作记录时间表 】，默认值：无
     */
    @Column(name = "transaction_task_oeate_time")
    private Date taskOeateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "transaction_delete_flag")
    private String deleteFlag;

    /**
     * 获取任务交易记录，ID
     *
     * @return transaction_id - 任务交易记录，ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置任务交易记录，ID
     *
     * @param id 任务交易记录，ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务交易记录，任务ID；字段类型：INT(11)；值：NOT NULL【关联task表中的task_id】；默认值：无
     *
     * @return transaction_task_id - 任务交易记录，任务ID；字段类型：INT(11)；值：NOT NULL【关联task表中的task_id】；默认值：无
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * 设置任务交易记录，任务ID；字段类型：INT(11)；值：NOT NULL【关联task表中的task_id】；默认值：无
     *
     * @param taskId 任务交易记录，任务ID；字段类型：INT(11)；值：NOT NULL【关联task表中的task_id】；默认值：无
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * 获取任务交易记录，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     *
     * @return transaction_operator_id - 任务交易记录，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * 设置任务交易记录，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     *
     * @param operatorId 任务交易记录，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 获取任务交易记录，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     *
     * @return transaction_operator_name - 任务交易记录，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置任务交易记录，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     *
     * @param operatorName 任务交易记录，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取任务交易记录，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait_Assignment、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 settlement、fail、success】，默认值：无
     *
     * @return transaction_operate_type - 任务交易记录，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait_Assignment、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 settlement、fail、success】，默认值：无
     */
    public String getOperateType() {
        return operateType;
    }

    /**
     * 设置任务交易记录，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait_Assignment、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 settlement、fail、success】，默认值：无
     *
     * @param operateType 任务交易记录，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait_Assignment、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 settlement、fail、success】，默认值：无
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    /**
     * 获取任务交易记录，操作的内容，字段类型：VARCHAR(150)；值：NOT NULL【根据任务操作类型不同，所产生的】；默认值：无
     *
     * @return transaction_operate_comment - 任务交易记录，操作的内容，字段类型：VARCHAR(150)；值：NOT NULL【根据任务操作类型不同，所产生的】；默认值：无
     */
    public String getOperateComment() {
        return operateComment;
    }

    /**
     * 设置任务交易记录，操作的内容，字段类型：VARCHAR(150)；值：NOT NULL【根据任务操作类型不同，所产生的】；默认值：无
     *
     * @param operateComment 任务交易记录，操作的内容，字段类型：VARCHAR(150)；值：NOT NULL【根据任务操作类型不同，所产生的】；默认值：无
     */
    public void setOperateComment(String operateComment) {
        this.operateComment = operateComment;
    }

    /**
     * 获取任务交易记录，操作的时间，字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：无
     *
     * @return transaction_operate_time - 任务交易记录，操作的时间，字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：无
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * 设置任务交易记录，操作的时间，字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：无
     *
     * @param operateTime 任务交易记录，操作的时间，字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：无
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 获取任务交易记录，责任人，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     *
     * @return transaction_task_ownev_id - 任务交易记录，责任人，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    public Integer getTaskOwnevId() {
        return taskOwnevId;
    }

    /**
     * 设置任务交易记录，责任人，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     *
     * @param taskOwnevId 任务交易记录，责任人，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    public void setTaskOwnevId(Integer taskOwnevId) {
        this.taskOwnevId = taskOwnevId;
    }

    /**
     * 获取任务交易记录，责任人，name；字段类型：VARCHAR(100) 值：NOT NULL；默认值：无
     *
     * @return transaction_task_ownev_name - 任务交易记录，责任人，name；字段类型：VARCHAR(100) 值：NOT NULL；默认值：无
     */
    public String getTaskOwnevName() {
        return taskOwnevName;
    }

    /**
     * 设置任务交易记录，责任人，name；字段类型：VARCHAR(100) 值：NOT NULL；默认值：无
     *
     * @param taskOwnevName 任务交易记录，责任人，name；字段类型：VARCHAR(100) 值：NOT NULL；默认值：无
     */
    public void setTaskOwnevName(String taskOwnevName) {
        this.taskOwnevName = taskOwnevName;
    }

    /**
     * 获取任务交易记录，责任人的时间，字段类型：DATETIME；值：NOT NULL【一般情况下与操作的时间一致，其他情况：在责任人不是当前操作人的情况下，更新操作记录时间表 】，默认值：无
     *
     * @return transaction_task_oeate_time - 任务交易记录，责任人的时间，字段类型：DATETIME；值：NOT NULL【一般情况下与操作的时间一致，其他情况：在责任人不是当前操作人的情况下，更新操作记录时间表 】，默认值：无
     */
    public Date getTaskOeateTime() {
        return taskOeateTime;
    }

    /**
     * 设置任务交易记录，责任人的时间，字段类型：DATETIME；值：NOT NULL【一般情况下与操作的时间一致，其他情况：在责任人不是当前操作人的情况下，更新操作记录时间表 】，默认值：无
     *
     * @param taskOeateTime 任务交易记录，责任人的时间，字段类型：DATETIME；值：NOT NULL【一般情况下与操作的时间一致，其他情况：在责任人不是当前操作人的情况下，更新操作记录时间表 】，默认值：无
     */
    public void setTaskOeateTime(Date taskOeateTime) {
        this.taskOeateTime = taskOeateTime;
    }

    /**
     * 获取逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @return transaction_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @param deleteFlag 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}