package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Task bean.
 */
@Table(name = "t_task")
public class TaskBean {
    /**
     * 任务，id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 任务，名称；字段类型：varchar(20)；值：NOT NULL【推荐：客户名简称+分支机构的名】；默认值：无
     */
    @Column(name = "task_name")
    private String name;

    /**
     * 任务，类型；字段类型：VARCHAR(20)；值：NOT NULL【以 收钱 Receive_Money，送钱 Give_Money，ATM维护 Maintain_ATM】；默认值：无
     */
    @Column(name = "task_type")
    private String type;

    /**
     * 任务，执行日期；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_execution_date")
    private Date executionDate;

    /**
     * 任务，状态【即t_task_tranaction.transaction_operate_type，对应】；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】；默认值：无
     */
    @Column(name = "task_status")
    private String status;

    /**
     * 客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_id")
    private Integer clientId;

    /**
     * 客户，姓名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_name")
    private String clientName;

    /**
     * 客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    @Column(name = "task_client_branch_id")
    private Integer clientBranchId;

    /**
     * 客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_name")
    private String clientBranchName;

    /**
     * 客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     */
    @Column(name = "task_client_branch_type")
    private String clientBranchType;

    /**
     * 客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_contact")
    private String clientBranchContact;

    /**
     * 客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_phone")
    private String clientBranchPhone;

    /**
     * 客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_address")
    private String clientBranchAddress;

    /**
     * 客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_latitude")
    private String clientBranchLatitude;

    /**
     * 客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_longitude")
    private String clientBranchLongitude;

    /**
     * 客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_window_start_time")
    private Date clientBranchWindowStartTime;

    /**
     * 客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_window_end_time")
    private Date clientBranchWindowEndTime;

    /**
     * 客户，分支机构，窗口，时长；字段类型：TIME；值：NOT NULL；默认值：无
     */
    @Column(name = "task_client_branch_window_duration")
    private Date clientBranchWindowDuration;

    /**
     * 任务，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     */
    @Column(name = "task_operator_id")
    private Integer operatorId;

    /**
     * 任务，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     */
    @Column(name = "task_operator_name")
    private String operatorName;

    /**
     * 任务，操作的时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：空；默认值：now() 当前服务器系统时间
     */
    @Column(name = "task_operate_date_time")
    private Date operateDateTime;

    /**
     * 任务，责任人，ID；字段类型：INT(11)；值：空；默认值：无
     */
    @Column(name = "task_ownev_id")
    private Integer ownevId;

    /**
     * 任务，责任人，name；字段类型：VARCHAR(100) 值：空；默认值：无
     */
    @Column(name = "task_ownev_name")
    private String ownevName;

    /**
     * 任务，分配的司机，ID；字段类型：INT(11)；值：空；默认值：无
     */
    @Column(name = "task_driver_id")
    private Integer driverId;

    /**
     * 任务，分配的司机，name；字段类型：VARCHAR(100) 值：空；默认值：无
     */
    @Column(name = "task_driver_name")
    private String driverName;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "task_create_date_time")
    private Date createDateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "task_delete_flag")
    private String deleteFlag;

    /**
     * 获取任务，id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @return task_id - 任务，id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置任务，id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @param id 任务，id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取任务，名称；字段类型：varchar(20)；值：NOT NULL【推荐：客户名简称+分支机构的名】；默认值：无
     *
     * @return task_name - 任务，名称；字段类型：varchar(20)；值：NOT NULL【推荐：客户名简称+分支机构的名】；默认值：无
     */
    public String getName() {
        return name;
    }

    /**
     * 设置任务，名称；字段类型：varchar(20)；值：NOT NULL【推荐：客户名简称+分支机构的名】；默认值：无
     *
     * @param name 任务，名称；字段类型：varchar(20)；值：NOT NULL【推荐：客户名简称+分支机构的名】；默认值：无
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取任务，类型；字段类型：VARCHAR(20)；值：NOT NULL【以 收钱 Receive_Money，送钱 Give_Money，ATM维护 Maintain_ATM】；默认值：无
     *
     * @return task_type - 任务，类型；字段类型：VARCHAR(20)；值：NOT NULL【以 收钱 Receive_Money，送钱 Give_Money，ATM维护 Maintain_ATM】；默认值：无
     */
    public String getType() {
        return type;
    }

    /**
     * 设置任务，类型；字段类型：VARCHAR(20)；值：NOT NULL【以 收钱 Receive_Money，送钱 Give_Money，ATM维护 Maintain_ATM】；默认值：无
     *
     * @param type 任务，类型；字段类型：VARCHAR(20)；值：NOT NULL【以 收钱 Receive_Money，送钱 Give_Money，ATM维护 Maintain_ATM】；默认值：无
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取任务，执行日期；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     *
     * @return task_execution_date - 任务，执行日期；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     */
    public Date getExecutionDate() {
        return executionDate;
    }

    /**
     * 设置任务，执行日期；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     *
     * @param executionDate 任务，执行日期；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     */
    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * 获取任务，状态【即t_task_tranaction.transaction_operate_type，对应】；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】；默认值：无
     *
     * @return task_status - 任务，状态【即t_task_tranaction.transaction_operate_type，对应】；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】；默认值：无
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置任务，状态【即t_task_tranaction.transaction_operate_type，对应】；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】；默认值：无
     *
     * @param status 任务，状态【即t_task_tranaction.transaction_operate_type，对应】；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】；默认值：无
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     *
     * @return task_client_id - 客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * 设置客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     *
     * @param clientId 客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取客户，姓名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return task_client_name - 客户，姓名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 设置客户，姓名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param clientName 客户，姓名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 获取客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     *
     * @return task_client_branch_id - 客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    public Integer getClientBranchId() {
        return clientBranchId;
    }

    /**
     * 设置客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     *
     * @param clientBranchId 客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    public void setClientBranchId(Integer clientBranchId) {
        this.clientBranchId = clientBranchId;
    }

    /**
     * 获取客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_name - 客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getClientBranchName() {
        return clientBranchName;
    }

    /**
     * 设置客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param clientBranchName 客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setClientBranchName(String clientBranchName) {
        this.clientBranchName = clientBranchName;
    }

    /**
     * 获取客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     *
     * @return task_client_branch_type - 客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     */
    public String getClientBranchType() {
        return clientBranchType;
    }

    /**
     * 设置客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     *
     * @param clientBranchType 客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     */
    public void setClientBranchType(String clientBranchType) {
        this.clientBranchType = clientBranchType;
    }

    /**
     * 获取客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_contact - 客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getClientBranchContact() {
        return clientBranchContact;
    }

    /**
     * 设置客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param clientBranchContact 客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setClientBranchContact(String clientBranchContact) {
        this.clientBranchContact = clientBranchContact;
    }

    /**
     * 获取客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_phone - 客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public String getClientBranchPhone() {
        return clientBranchPhone;
    }

    /**
     * 设置客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @param clientBranchPhone 客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public void setClientBranchPhone(String clientBranchPhone) {
        this.clientBranchPhone = clientBranchPhone;
    }

    /**
     * 获取客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_address - 客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    public String getClientBranchAddress() {
        return clientBranchAddress;
    }

    /**
     * 设置客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     *
     * @param clientBranchAddress 客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    public void setClientBranchAddress(String clientBranchAddress) {
        this.clientBranchAddress = clientBranchAddress;
    }

    /**
     * 获取客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_latitude - 客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public String getClientBranchLatitude() {
        return clientBranchLatitude;
    }

    /**
     * 设置客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @param clientBranchLatitude 客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public void setClientBranchLatitude(String clientBranchLatitude) {
        this.clientBranchLatitude = clientBranchLatitude;
    }

    /**
     * 获取客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_longitude - 客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public String getClientBranchLongitude() {
        return clientBranchLongitude;
    }

    /**
     * 设置客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @param clientBranchLongitude 客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public void setClientBranchLongitude(String clientBranchLongitude) {
        this.clientBranchLongitude = clientBranchLongitude;
    }

    /**
     * 获取客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_window_start_time - 客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public Date getClientBranchWindowStartTime() {
        return clientBranchWindowStartTime;
    }

    /**
     * 设置客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @param clientBranchWindowStartTime 客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public void setClientBranchWindowStartTime(Date clientBranchWindowStartTime) {
        this.clientBranchWindowStartTime = clientBranchWindowStartTime;
    }

    /**
     * 获取客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_window_end_time - 客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public Date getClientBranchWindowEndTime() {
        return clientBranchWindowEndTime;
    }

    /**
     * 设置客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @param clientBranchWindowEndTime 客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public void setClientBranchWindowEndTime(Date clientBranchWindowEndTime) {
        this.clientBranchWindowEndTime = clientBranchWindowEndTime;
    }

    /**
     * 获取客户，分支机构，窗口，时长；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @return task_client_branch_window_duration - 客户，分支机构，窗口，时长；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public Date getClientBranchWindowDuration() {
        return clientBranchWindowDuration;
    }

    /**
     * 设置客户，分支机构，窗口，时长；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @param clientBranchWindowDuration 客户，分支机构，窗口，时长；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public void setClientBranchWindowDuration(Date clientBranchWindowDuration) {
        this.clientBranchWindowDuration = clientBranchWindowDuration;
    }

    /**
     * 获取任务，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     *
     * @return task_operator_id - 任务，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * 设置任务，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     *
     * @param operatorId 任务，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 获取任务，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     *
     * @return task_operator_name - 任务，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置任务，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     *
     * @param operatorName 任务，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取任务，操作的时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：空；默认值：now() 当前服务器系统时间
     *
     * @return task_operate_date_time - 任务，操作的时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：空；默认值：now() 当前服务器系统时间
     */
    public Date getOperateDateTime() {
        return operateDateTime;
    }

    /**
     * 设置任务，操作的时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：空；默认值：now() 当前服务器系统时间
     *
     * @param operateDateTime 任务，操作的时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：空；默认值：now() 当前服务器系统时间
     */
    public void setOperateDateTime(Date operateDateTime) {
        this.operateDateTime = operateDateTime;
    }

    /**
     * 获取任务，责任人，ID；字段类型：INT(11)；值：空；默认值：无
     *
     * @return task_ownev_id - 任务，责任人，ID；字段类型：INT(11)；值：空；默认值：无
     */
    public Integer getOwnevId() {
        return ownevId;
    }

    /**
     * 设置任务，责任人，ID；字段类型：INT(11)；值：空；默认值：无
     *
     * @param ownevId 任务，责任人，ID；字段类型：INT(11)；值：空；默认值：无
     */
    public void setOwnevId(Integer ownevId) {
        this.ownevId = ownevId;
    }

    /**
     * 获取任务，责任人，name；字段类型：VARCHAR(100) 值：空；默认值：无
     *
     * @return task_ownev_name - 任务，责任人，name；字段类型：VARCHAR(100) 值：空；默认值：无
     */
    public String getOwnevName() {
        return ownevName;
    }

    /**
     * 设置任务，责任人，name；字段类型：VARCHAR(100) 值：空；默认值：无
     *
     * @param ownevName 任务，责任人，name；字段类型：VARCHAR(100) 值：空；默认值：无
     */
    public void setOwnevName(String ownevName) {
        this.ownevName = ownevName;
    }

    /**
     * 获取任务，分配的司机，ID；字段类型：INT(11)；值：空；默认值：无
     *
     * @return task_driver_id - 任务，分配的司机，ID；字段类型：INT(11)；值：空；默认值：无
     */
    public Integer getDriverId() {
        return driverId;
    }

    /**
     * 设置任务，分配的司机，ID；字段类型：INT(11)；值：空；默认值：无
     *
     * @param driverId 任务，分配的司机，ID；字段类型：INT(11)；值：空；默认值：无
     */
    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    /**
     * 获取任务，分配的司机，name；字段类型：VARCHAR(100) 值：空；默认值：无
     *
     * @return task_driver_name - 任务，分配的司机，name；字段类型：VARCHAR(100) 值：空；默认值：无
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置任务，分配的司机，name；字段类型：VARCHAR(100) 值：空；默认值：无
     *
     * @param driverName 任务，分配的司机，name；字段类型：VARCHAR(100) 值：空；默认值：无
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     *
     * @return task_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    public Date getCreateDateTime() {
        return createDateTime;
    }

    /**
     * 设置创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     *
     * @param createDateTime 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     * 获取逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @return task_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
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