package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Role bean.
 */
@Table(name = "t_role")
public class RoleBean {
    /**
     * 角色，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "role_name")
    private String name;

    /**
     * 角色，对应的权限；字段类型：VARCHAR(1024)；值：NOT NULL【此处以 taskList|taskAdd|taskUpdate|taskDelete 风格进行表示；对应表：无表状态；默认为0；代表权限】；默认值：无
     */
    @Column(name = "role_privilege")
    private String privilege;

    /**
     * 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    @Column(name = "role_status")
    private String status;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "role_create_date_time")
    private Date createDateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "role_delete_flag")
    private String deleteFlag;

    /**
     * 获取角色，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @return role_id - 角色，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @param id 角色，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return role_name - 角色，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param name 角色，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色，对应的权限；字段类型：VARCHAR(1024)；值：NOT NULL【此处以 taskList|taskAdd|taskUpdate|taskDelete 风格进行表示；对应表：无表状态；默认为0；代表权限】；默认值：无
     *
     * @return role_privilege - 角色，对应的权限；字段类型：VARCHAR(1024)；值：NOT NULL【此处以 taskList|taskAdd|taskUpdate|taskDelete 风格进行表示；对应表：无表状态；默认为0；代表权限】；默认值：无
     */
    public String getPrivilege() {
        return privilege;
    }

    /**
     * 设置角色，对应的权限；字段类型：VARCHAR(1024)；值：NOT NULL【此处以 taskList|taskAdd|taskUpdate|taskDelete 风格进行表示；对应表：无表状态；默认为0；代表权限】；默认值：无
     *
     * @param privilege 角色，对应的权限；字段类型：VARCHAR(1024)；值：NOT NULL【此处以 taskList|taskAdd|taskUpdate|taskDelete 风格进行表示；对应表：无表状态；默认为0；代表权限】；默认值：无
     */
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    /**
     * 获取状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @return role_status - 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
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
     * @return role_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
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
     * @return role_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
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