package com.comodin.fleet.core.bean;

import tk.mybatis.mapper.entity.IDynamicTableName;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Lat lng driver bean.
 */
@Table(name = "t_latlng_driver")
public class LatLngDriverBean implements IDynamicTableName {
    /**
     * 经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    @Id
    @Column(name = "latlng_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串
     */
    @Column(name = "latlng_driver_username")
    private String driverUsername;

    /**
     * 当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串
     */
    @Column(name = "latlng_driver_name")
    private String driverName;

    /**
     * 司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串
     */
    @Column(name = "latlng_phone_imei")
    private String phoneImei;

    /**
     * 司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     */
    @Column(name = "latlng_phone_local_ip")
    private String phoneLocalIp;

    /**
     * 司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     */
    @Column(name = "latlng_phone_lng")
    private String phoneLng;

    /**
     * 司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     */
    @Column(name = "latlng_phone_lat")
    private String phoneLat;

    /**
     * 司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无
     */
    @Column(name = "latlng_phone_upload_timestamp")
    private Date phoneUploadTimestamp;

    /**
     * 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    @Column(name = "latlng_status")
    private String status;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "latlng_create_date_time")
    private Date createDateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "latlng_delete_flag")
    private String deleteFlag;

    /**
     * 获取经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     *
     * @return latlng_id - 经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     *
     * @param id 经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串
     *
     * @return latlng_driver_username - 当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串
     */
    public String getDriverUsername() {
        return driverUsername;
    }

    /**
     * 设置当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串
     *
     * @param driverUsername 当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串
     */
    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    /**
     * 获取当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串
     *
     * @return latlng_driver_name - 当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串
     *
     * @param driverName 当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 获取司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串
     *
     * @return latlng_phone_imei - 司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串
     */
    public String getPhoneImei() {
        return phoneImei;
    }

    /**
     * 设置司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串
     *
     * @param phoneImei 司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串
     */
    public void setPhoneImei(String phoneImei) {
        this.phoneImei = phoneImei;
    }

    /**
     * 获取司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     *
     * @return latlng_phone_local_ip - 司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     */
    public String getPhoneLocalIp() {
        return phoneLocalIp;
    }

    /**
     * 设置司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     *
     * @param phoneLocalIp 司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无
     */
    public void setPhoneLocalIp(String phoneLocalIp) {
        this.phoneLocalIp = phoneLocalIp;
    }

    /**
     * 获取司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     *
     * @return latlng_phone_lng - 司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     */
    public String getPhoneLng() {
        return phoneLng;
    }

    /**
     * 设置司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     *
     * @param phoneLng 司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     */
    public void setPhoneLng(String phoneLng) {
        this.phoneLng = phoneLng;
    }

    /**
     * 获取司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     *
     * @return latlng_phone_lat - 司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     */
    public String getPhoneLat() {
        return phoneLat;
    }

    /**
     * 设置司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     *
     * @param phoneLat 司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无
     */
    public void setPhoneLat(String phoneLat) {
        this.phoneLat = phoneLat;
    }

    /**
     * 获取司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无
     *
     * @return latlng_phone_upload_timestamp - 司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无
     */
    public Date getPhoneUploadTimestamp() {
        return phoneUploadTimestamp;
    }

    /**
     * 设置司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无
     *
     * @param phoneUploadTimestamp 司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无
     */
    public void setPhoneUploadTimestamp(Date phoneUploadTimestamp) {
        this.phoneUploadTimestamp = phoneUploadTimestamp;
    }

    /**
     * 获取状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @return latlng_status - 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
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
     * @return latlng_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
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
     * @return latlng_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
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

    /**
     * 获取动态表名 - 只要有返回值，不是null和''，就会用返回值作为表名
     *
     * @return
     */
    @Override
    public String getDynamicTableName() {
        return dynamicTableName;
    }
    @Transient//非表字段，字段名称无所谓
    private String dynamicTableName;

    /**
     * Sets dynamic table name.
     *
     * @param dynamicTableName the dynamic table name
     */
    public void setDynamicTableName(String dynamicTableName) {
        this.dynamicTableName = dynamicTableName;
    }
}