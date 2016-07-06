package com.comodin.fleet.vo;

/**
 * 用于，用户管理列表中，搜索对应的字段
 * Created by supeng on 2016-04-24 0024.
 */
public class TaskBeanVo {

    private Integer draw;
    private Integer start; //从第多少条开始
    private Integer length; //取多少条

    //任务名称
    private String name;
    //任务类型
    private String type;
    //任务执行日期
    private String executionDateStartDate;
    private String executionDateEndDate;
    //任务状态
    private String status;

    //客户，ID
    private Integer clientId;
    //客户，姓名
    private String clientName;
    //客户，分支点，名称
    private String clientBranchName;
    //客户，分支点，联系人
    private String clientBranchContact;
    //客户，分支机构，联系人，电话；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
    private String clientBranchPhone;
    //客户，分支点，地址详细
    private String clientBranchAddress;


    //任务，操作人，姓名
    private String operatorName;
    //任务，责任人，姓名
    private String ownevName;
    //任务，司机，ID
    private Integer driverId;
    //任务，司机，姓名
    private String driverName;

    private String createDataTimeStartTime;
    private String createDataTimeEndTime;

    /**
     * Gets draw.
     *
     * @return the draw
     */
    public Integer getDraw() {
        return draw;
    }

    /**
     * Sets draw.
     *
     * @param draw the draw
     */
    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * Gets length.
     *
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Sets length.
     *
     * @param length the length
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets execution date start date.
     *
     * @return the execution date start date
     */
    public String getExecutionDateStartDate() {
        return executionDateStartDate;
    }

    /**
     * Sets execution date start date.
     *
     * @param executionDateStartDate the execution date start date
     */
    public void setExecutionDateStartDate(String executionDateStartDate) {
        this.executionDateStartDate = executionDateStartDate;
    }

    /**
     * Gets execution date end date.
     *
     * @return the execution date end date
     */
    public String getExecutionDateEndDate() {
        return executionDateEndDate;
    }

    /**
     * Sets execution date end date.
     *
     * @param executionDateEndDate the execution date end date
     */
    public void setExecutionDateEndDate(String executionDateEndDate) {
        this.executionDateEndDate = executionDateEndDate;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets client name.
     *
     * @return the client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets client name.
     *
     * @param clientName the client name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets client branch name.
     *
     * @return the client branch name
     */
    public String getClientBranchName() {
        return clientBranchName;
    }

    /**
     * Sets client branch name.
     *
     * @param clientBranchName the client branch name
     */
    public void setClientBranchName(String clientBranchName) {
        this.clientBranchName = clientBranchName;
    }

    /**
     * Gets client branch contact.
     *
     * @return the client branch contact
     */
    public String getClientBranchContact() {
        return clientBranchContact;
    }

    /**
     * Sets client branch contact.
     *
     * @param clientBranchContact the client branch contact
     */
    public void setClientBranchContact(String clientBranchContact) {
        this.clientBranchContact = clientBranchContact;
    }

    /**
     * Gets client branch phone.
     *
     * @return the client branch phone
     */
    public String getClientBranchPhone() {
        return clientBranchPhone;
    }

    /**
     * Sets client branch phone.
     *
     * @param clientBranchPhone the client branch phone
     */
    public void setClientBranchPhone(String clientBranchPhone) {
        this.clientBranchPhone = clientBranchPhone;
    }

    /**
     * Gets client branch address.
     *
     * @return the client branch address
     */
    public String getClientBranchAddress() {
        return clientBranchAddress;
    }

    /**
     * Sets client branch address.
     *
     * @param clientBranchAddress the client branch address
     */
    public void setClientBranchAddress(String clientBranchAddress) {
        this.clientBranchAddress = clientBranchAddress;
    }

    /**
     * Gets operator name.
     *
     * @return the operator name
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * Sets operator name.
     *
     * @param operatorName the operator name
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * Gets ownev name.
     *
     * @return the ownev name
     */
    public String getOwnevName() {
        return ownevName;
    }

    /**
     * Sets ownev name.
     *
     * @param ownevName the ownev name
     */
    public void setOwnevName(String ownevName) {
        this.ownevName = ownevName;
    }

    /**
     * Gets driver id.
     *
     * @return the driver id
     */
    public Integer getDriverId() {
        return driverId;
    }

    /**
     * Sets driver id.
     *
     * @param driverId the driver id
     */
    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    /**
     * Gets driver name.
     *
     * @return the driver name
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Sets driver name.
     *
     * @param driverName the driver name
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * Gets create data time start time.
     *
     * @return the create data time start time
     */
    public String getCreateDataTimeStartTime() {
        return createDataTimeStartTime;
    }

    /**
     * Sets create data time start time.
     *
     * @param createDataTimeStartTime the create data time start time
     */
    public void setCreateDataTimeStartTime(String createDataTimeStartTime) {
        this.createDataTimeStartTime = createDataTimeStartTime;
    }

    /**
     * Gets create data time end time.
     *
     * @return the create data time end time
     */
    public String getCreateDataTimeEndTime() {
        return createDataTimeEndTime;
    }

    /**
     * Sets create data time end time.
     *
     * @param createDataTimeEndTime the create data time end time
     */
    public void setCreateDataTimeEndTime(String createDataTimeEndTime) {
        this.createDataTimeEndTime = createDataTimeEndTime;
    }
}