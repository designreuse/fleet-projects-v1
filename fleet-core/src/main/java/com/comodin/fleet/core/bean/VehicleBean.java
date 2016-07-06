package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Vehicle bean.
 */
@Table(name = "t_vehicle")
public class VehicleBean {
    /**
     * 车辆，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 车辆，名称；字段类型：VARCHAR(20)；值：NOT NULL【】
     */
    @Column(name = "vehicle_name")
    private String name;

    /**
     * 车辆，牌照；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     */
    @Column(name = "vehicle_license_plate")
    private String licensePlate;

    /**
     * 车辆，定位设置ID；字段类型：VARCHAR(11)；值：NOT NULL【】；默认值：无
     */
    @Column(name = "vehicle_location_device_id")
    private String locationDeviceId;

    /**
     * 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    @Column(name = "vehicle_status")
    private String status;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "vehicle_create_date_time")
    private Date createDateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "vehicle_delete_flag")
    private String deleteFlag;

    /**
     * 获取车辆，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     *
     * @return vehicle_id - 车辆，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置车辆，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     *
     * @param id 车辆，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取车辆，名称；字段类型：VARCHAR(20)；值：NOT NULL【】
     *
     * @return vehicle_name - 车辆，名称；字段类型：VARCHAR(20)；值：NOT NULL【】
     */
    public String getName() {
        return name;
    }

    /**
     * 设置车辆，名称；字段类型：VARCHAR(20)；值：NOT NULL【】
     *
     * @param name 车辆，名称；字段类型：VARCHAR(20)；值：NOT NULL【】
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取车辆，牌照；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     *
     * @return vehicle_license_plate - 车辆，牌照；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * 设置车辆，牌照；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     *
     * @param licensePlate 车辆，牌照；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * 获取车辆，定位设置ID；字段类型：VARCHAR(11)；值：NOT NULL【】；默认值：无
     *
     * @return vehicle_location_device_id - 车辆，定位设置ID；字段类型：VARCHAR(11)；值：NOT NULL【】；默认值：无
     */
    public String getLocationDeviceId() {
        return locationDeviceId;
    }

    /**
     * 设置车辆，定位设置ID；字段类型：VARCHAR(11)；值：NOT NULL【】；默认值：无
     *
     * @param locationDeviceId 车辆，定位设置ID；字段类型：VARCHAR(11)；值：NOT NULL【】；默认值：无
     */
    public void setLocationDeviceId(String locationDeviceId) {
        this.locationDeviceId = locationDeviceId;
    }

    /**
     * 获取状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @return vehicle_status - 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @param status 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     *
     * @return vehicle_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
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
     * @return vehicle_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
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