package com.comodin.fleet.json;

/**
 * Created by supeng on 4/26/2016.
 */
public class ResultEntity {

    private Integer resultCode;
    private Object resultMsg;

    /**
     * Instantiates a new Result entity.
     *
     * @param resultCode the result code
     * @param resultMsg  the result msg
     */
    public ResultEntity(Integer resultCode, Object resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    /**
     * Gets result code.
     *
     * @return the result code
     */
    public Integer getResultCode() {
        return resultCode;
    }

    /**
     * Sets result code.
     *
     * @param resultCode the result code
     */
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Gets result msg.
     *
     * @return the result msg
     */
    public Object getResultMsg() {
        return resultMsg;
    }

    /**
     * Sets result msg.
     *
     * @param resultMsg the result msg
     */
    public void setResultMsg(Object resultMsg) {
        this.resultMsg = resultMsg;
    }
}
