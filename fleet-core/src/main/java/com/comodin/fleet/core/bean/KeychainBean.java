package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Keychain bean.
 */
@Table(name = "t_keychain")
public class KeychainBean {
    /**
     * 钥匙串，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无
     */
    @Id
    @Column(name = "keychain_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 钥匙串，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     */
    @Column(name = "keychain_name")
    private String name;

    /**
     * 钥匙串，分配的，司机,ID ；字段类型：int(11)；值：；默认值：无
     */
    @Column(name = "keychain_assign_driver_id")
    private Integer assignDriverId;

    /**
     * 钥匙串，分配的，司机，名称 ；字段类型：VARCHAR(100)；值：；默认值：无
     */
    @Column(name = "keychain_assign_driver_name")
    private String assignDriverName;

    /**
     * 钥匙串状态 ；字段类型：CHAR(10)；值：【以 空闲Idle 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无
     */
    @Column(name = "keychain_status")
    private String status;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "keychain_create_date_time")
    private Date createDateTime;

    /**
     * 钥匙串逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "keychain_delete_flag")
    private String deleteFlag;

    /**
     * 获取钥匙串，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @return keychain_id - 钥匙串，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置钥匙串，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @param id 钥匙串，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取钥匙串，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     *
     * @return keychain_name - 钥匙串，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     */
    public String getName() {
        return name;
    }

    /**
     * 设置钥匙串，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     *
     * @param name 钥匙串，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取钥匙串，分配的，司机,ID ；字段类型：int(11)；值：；默认值：无
     *
     * @return keychain_assign_driver_id - 钥匙串，分配的，司机,ID ；字段类型：int(11)；值：；默认值：无
     */
    public Integer getAssignDriverId() {
        return assignDriverId;
    }

    /**
     * 设置钥匙串，分配的，司机,ID ；字段类型：int(11)；值：；默认值：无
     *
     * @param assignDriverId 钥匙串，分配的，司机,ID ；字段类型：int(11)；值：；默认值：无
     */
    public void setAssignDriverId(Integer assignDriverId) {
        this.assignDriverId = assignDriverId;
    }

    /**
     * 获取钥匙串，分配的，司机，名称 ；字段类型：VARCHAR(100)；值：；默认值：无
     *
     * @return keychain_assign_driver_name - 钥匙串，分配的，司机，名称 ；字段类型：VARCHAR(100)；值：；默认值：无
     */
    public String getAssignDriverName() {
        return assignDriverName;
    }

    /**
     * 设置钥匙串，分配的，司机，名称 ；字段类型：VARCHAR(100)；值：；默认值：无
     *
     * @param assignDriverName 钥匙串，分配的，司机，名称 ；字段类型：VARCHAR(100)；值：；默认值：无
     */
    public void setAssignDriverName(String assignDriverName) {
        this.assignDriverName = assignDriverName;
    }

    /**
     * 获取钥匙串状态 ；字段类型：CHAR(10)；值：【以 空闲Idle 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无
     *
     * @return keychain_status - 钥匙串状态 ；字段类型：CHAR(10)；值：【以 空闲Idle 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置钥匙串状态 ；字段类型：CHAR(10)；值：【以 空闲Idle 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无
     *
     * @param status 钥匙串状态 ；字段类型：CHAR(10)；值：【以 空闲Idle 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     *
     * @return keychain_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
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
     * 获取钥匙串逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @return keychain_delete_flag - 钥匙串逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置钥匙串逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @param deleteFlag 钥匙串逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}