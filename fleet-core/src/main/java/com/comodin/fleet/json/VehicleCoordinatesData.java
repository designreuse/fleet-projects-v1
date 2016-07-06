package com.comodin.fleet.json;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆经纬度数据 json  pojo
 */
public class VehicleCoordinatesData implements Serializable {

    private String username;    //当前司机登陆的用户名

    private Integer vehicleId;  //司机对应的车辆ID

    private String longitude;//经度

    private String latitude;//纬度

    private String uploadTimeStamp;

    private String local_ip;//当前司机手机对应的IP地址
    private Date createDateTime;    //

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
     * Gets vehicle id.
     *
     * @return the vehicle id
     */
    public Integer getVehicleId() {
        return vehicleId;
    }

    /**
     * Sets vehicle id.
     *
     * @param vehicleId the vehicle id
     */
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets upload time stamp.
     *
     * @return the upload time stamp
     */
    public String getUploadTimeStamp() {
        return uploadTimeStamp;
    }

    /**
     * Sets upload time stamp.
     *
     * @param uploadTimeStamp the upload time stamp
     */
    public void setUploadTimeStamp(String uploadTimeStamp) {
        this.uploadTimeStamp = uploadTimeStamp;
    }

    /**
     * Gets local ip.
     *
     * @return the local ip
     */
    public String getLocal_ip() {
        return local_ip;
    }

    /**
     * Sets local ip.
     *
     * @param local_ip the local ip
     */
    public void setLocal_ip(String local_ip) {
        this.local_ip = local_ip;
    }

    /**
     * Gets create date time.
     *
     * @return the create date time
     */
    public Date getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Sets create date time.
     *
     * @param createDateTime the create date time
     */
    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
