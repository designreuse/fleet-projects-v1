package com.comodin.fleet.vo;

/**
 * 用于，用户管理列表中，搜索对应的字段
 * Created by supeng on 2016-04-24 0024.
 */
public class UserBeanVo {

    private Integer draw;
    private Integer start; //从第多少条开始
    private Integer length; //取多少条

    private String username;
    private String curp;
    private String phone;
    private String gender;
    private String departmentId;
    private String roles;

    private String lastLoginTimeStartTime;
    private String lastLoginTimeEndTime;

    private String status;
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
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets curp.
     *
     * @return the curp
     */
    public String getCurp() {
        return curp;
    }

    /**
     * Sets curp.
     *
     * @param curp the curp
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets department id.
     *
     * @return the department id
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets department id.
     *
     * @param departmentId the department id
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public String getRoles() {
        return roles;
    }

    /**
     * Sets roles.
     *
     * @param roles the roles
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * Gets last login time start time.
     *
     * @return the last login time start time
     */
    public String getLastLoginTimeStartTime() {
        return lastLoginTimeStartTime;
    }

    /**
     * Sets last login time start time.
     *
     * @param lastLoginTimeStartTime the last login time start time
     */
    public void setLastLoginTimeStartTime(String lastLoginTimeStartTime) {
        this.lastLoginTimeStartTime = lastLoginTimeStartTime;
    }

    /**
     * Gets last login time end time.
     *
     * @return the last login time end time
     */
    public String getLastLoginTimeEndTime() {
        return lastLoginTimeEndTime;
    }

    /**
     * Sets last login time end time.
     *
     * @param lastLoginTimeEndTime the last login time end time
     */
    public void setLastLoginTimeEndTime(String lastLoginTimeEndTime) {
        this.lastLoginTimeEndTime = lastLoginTimeEndTime;
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