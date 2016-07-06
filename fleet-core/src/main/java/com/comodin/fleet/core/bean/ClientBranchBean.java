package com.comodin.fleet.core.bean;

import java.util.Date;
import javax.persistence.*;

/**
 * The type Client branch bean.
 */
@Table(name = "t_client_branch")
public class ClientBranchBean {
    /**
     * 客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【系统自增长】；默认值：无
     */
    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_name")
    private String name;

    /**
     * 客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     */
    @Column(name = "branch_type")
    private String type;

    /**
     * 客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_client_id")
    private Integer clientId;

    /**
     * 客户，名；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_client_name")
    private String clientName;

    /**
     * 客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_contact")
    private String contact;

    /**
     * 客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_phone")
    private String phone;

    /**
     * 客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_address")
    private String address;

    /**
     * 客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_latitude")
    private String latitude;

    /**
     * 客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_longitude")
    private String longitude;

    /**
     * 客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_window_start_time")
    private Date windowStartTime;

    /**
     * 客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_window_end_time")
    private Date windowEndTime;

    /**
     * 客户，分支机构，窗口，预计时长；字段类型：TIME；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_window_duration")
    private Date windowDuration;

    /**
     * 客户，分支机构，任务计划周期性；字段类型：VARCHAR(30)；值：NOT NULL【天 月 周】；默认值：空
     */
    @Column(name = "branch_cron_expression")
    private String cronExpression;

    /**
     * 客户，分支机构，钥匙；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Column(name = "branch_key_code")
    private String keyCode;

    /**
     * 客户，分支机构，当前钥匙对应的钥匙串ID；字段类型：INT(11)对应表 t_key_ring.id；值：可选；默认值：无
     */
    @Column(name = "branch_keychain_id")
    private Integer keychainId;

    /**
     * 客户，分支机构，当前钥匙对应的钥匙串Name；字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     */
    @Column(name = "branch_keychain_name")
    private String keychainName;

    /**
     * 客户，分支机构，状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    @Column(name = "branch_status")
    private String status;

    /**
     * 客户，分支机构，创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "branch_create_date_time")
    private Date createDateTime;

    /**
     * 客户，分支机构，逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "branch_delete_flag")
    private String deleteFlag;

    /**
     * 获取客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【系统自增长】；默认值：无
     *
     * @return branch_id - 客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【系统自增长】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【系统自增长】；默认值：无
     *
     * @param id 客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【系统自增长】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return branch_name - 客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param name 客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     *
     * @return branch_type - 客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     */
    public String getType() {
        return type;
    }

    /**
     * 设置客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     *
     * @param type 客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     *
     * @return branch_client_id - 客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * 设置客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     *
     * @param clientId 客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取客户，名；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     *
     * @return branch_client_name - 客户，名；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 设置客户，名；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     *
     * @param clientName 客户，名；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 获取客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return branch_contact - 客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param contact 客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * 获取客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @return branch_phone - 客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @param phone 客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     *
     * @return branch_address - 客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     *
     * @param address 客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @return branch_latitude - 客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @param latitude 客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @return branch_longitude - 客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     *
     * @param longitude 客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @return branch_window_start_time - 客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public Date getWindowStartTime() {
        return windowStartTime;
    }

    /**
     * 设置客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @param windowStartTime 客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public void setWindowStartTime(Date windowStartTime) {
        this.windowStartTime = windowStartTime;
    }

    /**
     * 获取客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @return branch_window_end_time - 客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public Date getWindowEndTime() {
        return windowEndTime;
    }

    /**
     * 设置客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @param windowEndTime 客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public void setWindowEndTime(Date windowEndTime) {
        this.windowEndTime = windowEndTime;
    }

    /**
     * 获取客户，分支机构，窗口，预计时长；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @return branch_window_duration - 客户，分支机构，窗口，预计时长；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public Date getWindowDuration() {
        return windowDuration;
    }

    /**
     * 设置客户，分支机构，窗口，预计时长；字段类型：TIME；值：NOT NULL；默认值：无
     *
     * @param windowDuration 客户，分支机构，窗口，预计时长；字段类型：TIME；值：NOT NULL；默认值：无
     */
    public void setWindowDuration(Date windowDuration) {
        this.windowDuration = windowDuration;
    }

    /**
     * 获取客户，分支机构，任务计划周期性；字段类型：VARCHAR(30)；值：NOT NULL【天 月 周】；默认值：空
     *
     * @return branch_cron_expression - 客户，分支机构，任务计划周期性；字段类型：VARCHAR(30)；值：NOT NULL【天 月 周】；默认值：空
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置客户，分支机构，任务计划周期性；字段类型：VARCHAR(30)；值：NOT NULL【天 月 周】；默认值：空
     *
     * @param cronExpression 客户，分支机构，任务计划周期性；字段类型：VARCHAR(30)；值：NOT NULL【天 月 周】；默认值：空
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取客户，分支机构，钥匙；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return branch_key_code - 客户，分支机构，钥匙；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getKeyCode() {
        return keyCode;
    }

    /**
     * 设置客户，分支机构，钥匙；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param keyCode 客户，分支机构，钥匙；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * 获取客户，分支机构，当前钥匙对应的钥匙串ID；字段类型：INT(11)对应表 t_key_ring.id；值：可选；默认值：无
     *
     * @return branch_keychain_id - 客户，分支机构，当前钥匙对应的钥匙串ID；字段类型：INT(11)对应表 t_key_ring.id；值：可选；默认值：无
     */
    public Integer getKeychainId() {
        return keychainId;
    }

    /**
     * 设置客户，分支机构，当前钥匙对应的钥匙串ID；字段类型：INT(11)对应表 t_key_ring.id；值：可选；默认值：无
     *
     * @param keychainId 客户，分支机构，当前钥匙对应的钥匙串ID；字段类型：INT(11)对应表 t_key_ring.id；值：可选；默认值：无
     */
    public void setKeychainId(Integer keychainId) {
        this.keychainId = keychainId;
    }

    /**
     * 获取客户，分支机构，当前钥匙对应的钥匙串Name；字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     *
     * @return branch_keychain_name - 客户，分支机构，当前钥匙对应的钥匙串Name；字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     */
    public String getKeychainName() {
        return keychainName;
    }

    /**
     * 设置客户，分支机构，当前钥匙对应的钥匙串Name；字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     *
     * @param keychainName 客户，分支机构，当前钥匙对应的钥匙串Name；字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无
     */
    public void setKeychainName(String keychainName) {
        this.keychainName = keychainName;
    }

    /**
     * 获取客户，分支机构，状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @return branch_status - 客户，分支机构，状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置客户，分支机构，状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @param status 客户，分支机构，状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取客户，分支机构，创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     *
     * @return branch_create_date_time - 客户，分支机构，创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    public Date getCreateDateTime() {
        return createDateTime;
    }

    /**
     * 设置客户，分支机构，创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     *
     * @param createDateTime 客户，分支机构，创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    /**
     * 获取客户，分支机构，逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @return branch_delete_flag - 客户，分支机构，逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置客户，分支机构，逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     *
     * @param deleteFlag 客户，分支机构，逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}