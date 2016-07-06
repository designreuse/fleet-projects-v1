package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Client bean.
 */
@Table(name = "t_client")
public class ClientBean {
    /**
     * 客户id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户名
     */
    @Column(name = "client_name")
    private String name;

    /**
     * 手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "client_phone")
    private String phone;

    /**
     * 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    @Column(name = "client_status")
    private String status;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "client_create_date_time")
    private Date createDateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "client_delete_flag")
    private String deleteFlag;

    /**
     * 获取客户id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @return client_id - 客户id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置客户id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @param id 客户id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户名
     *
     * @return client_name - 客户名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客户名
     *
     * @param name 客户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     *
     * @return client_phone - 手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     *
     * @param phone 手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @return client_status - 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
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
     * @return client_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
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
     * @return client_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
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