package com.comodin.fleet.json;

/**
 * Created by supeng on 4/15/2016.
 */
public class AccessToKen {

    private String access_token;//用户token，UUID生成
    private Integer vehicle_id;//司机对应车辆ID
    private long currentServerTimestamp;


    /**
     * Instantiates a new Access to ken.
     */
    public AccessToKen() {
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * Sets access token.
     *
     * @param access_token the access token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * Gets vehicle id.
     *
     * @return the vehicle id
     */
    public Integer getVehicle_id() {
        return vehicle_id;
    }

    /**
     * Sets vehicle id.
     *
     * @param vehicle_id the vehicle id
     */
    public void setVehicle_id(Integer vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    /**
     * Gets current server timestamp.
     *
     * @return the current server timestamp
     */
    public long getCurrentServerTimestamp() {
        return currentServerTimestamp;
    }

    /**
     * Sets current server timestamp.
     *
     * @param currentServerTimestamp the current server timestamp
     */
    public void setCurrentServerTimestamp(long currentServerTimestamp) {
        this.currentServerTimestamp = currentServerTimestamp;
    }
}
