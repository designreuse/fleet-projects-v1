package com.comodin.fleet.vo;

/**
 * 用于，用户管理列表中，搜索对应的字段
 * Created by supeng on 2016-04-24 0024.
 */
public class ClientBeanVo {

    private Integer draw;
    private Integer start; //从第多少条开始
    private Integer length; //取多少条

    private String name;
    private String phone;

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