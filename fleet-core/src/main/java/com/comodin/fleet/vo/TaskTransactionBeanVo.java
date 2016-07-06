package com.comodin.fleet.vo;

/**
 * Created by supeng on 5/18/2016.
 */
public class TaskTransactionBeanVo {
    private Integer draw;
    private Integer start; //从第多少条开始
    private Integer length; //取多少条

    //任务交易记录，操作人，姓名
    private String operatorName;
    //任务交易记录，操作类型
    private String operatorType;

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
     * Gets operator type.
     *
     * @return the operator type
     */
    public String getOperatorType() {
        return operatorType;
    }

    /**
     * Sets operator type.
     *
     * @param operatorType the operator type
     */
    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
}
