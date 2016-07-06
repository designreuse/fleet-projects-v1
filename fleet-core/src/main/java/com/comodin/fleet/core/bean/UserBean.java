package com.comodin.fleet.core.bean;

import com.comodin.fleet.basic.validation.IBaseValidGroupAdd;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 * The type User bean.
 */
@Table(name = "t_user")
public class UserBean {
    /**
     * The interface View fleet portal.
     */
    public static interface ViewFleetPortal {}

    @Transient
    private String token;

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 用户，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Size(min = 3, max = 20, message = "{Size.userBean.username}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_username")
    private String username;

    /**
     * 用户密码；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    @Size(min = 6, max = 20, message = "{Size.userBean.password}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_password")
    private String password;

    /**
     * 用户类型；字段类型：CHAR(8)；值：NOT NULL【以 Employee、Client】，默认值：无
     */
    @NotEmpty(message = "{NotEmpty.userBean.type}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_type")
    private String type;

    /**
     * 用户编号；字段类型VARCHAR(11)；值：NOT NULL【若为用户类型为 Client，此字段“用户编号”，即为 t_client.client_id 字段，】；默认值：空字符串
     */
    @Column(name = "user_sn")
    private String sn;

    /**
     * 姓名，名；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     */
    @NotEmpty(message = "{NotEmpty.userBean.firstName}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_first_name")
    private String firstName;

    /**
     * 姓名，姓；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     */
    @NotEmpty(message = "{NotEmpty.userBean.lastName}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_last_name")
    private String lastName;

    /**
     * 手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     */
    @NotEmpty(message = "{NotEmpty.userBean.phone}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_phone")
    private String phone;

    /**
     * 座机电话号；字段类型：varchar(15)；值：可选；默认值：无
     */
    @Column(name = "user_landline")
    private String landline;

    /**
     * 电子邮箱；字段类型：varchar(20)；值：NOT NULL；默认值：无
     */
    @Email(message = "{Email.userBean.email}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_email")
    private String email;

    /**
     * 性别；字段类型：CHAR(6)；值：NOT NULL【以 Male、Female】；默认值：无
     */
    @NotEmpty(message = "{NotEmpty.userBean.gender}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_gender")
    private String gender;

    /**
     * 生日；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     */
    @Past(message = "{Past.userBean.birthday}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @DateTimeFormat(pattern = ConstantsFinalValue.DATE_FORMAT)
    @Column(name = "user_birthday")
    private Date birthday;

    /**
     * 身份证号；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无；备注：是 西班牙语什么的缩写 Clave Única de Registro de Población (CURP)的缩写。是一串代表身份的数字和字母,就类似身份证号码一样。
     */
    @NotEmpty(message = "{NotEmpty.userBean.curp}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_curp")
    private String curp;

    /**
     * 个人，住址；字段类型：varchar(100)；值：可选；默认值：无
     */
    @Column(name = "user_address")
    private String address;

    /**
     * 个人，头像；字段类型：VARCHAR(20)；值：可选；默认为：无
     */
    @Column(name = "user_personal_photo")
    private String personalPhoto;

    /**
     * 个人，简介；字段类型：varchar(300)；值：可选；默认值：无
     */
    @Column(name = "user_personal_profile")
    private String personalProfile;

    /**
     * 最近登陆，时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：可选；默认值：无
     */
    @Column(name = "user_last_login_time")
    private Date lastLoginTime;

    /**
     * 最近登陆，IP；字段类型：varchar(20)；值：可选；默认值：无
     */
    @Column(name = "user_last_login_ip")
    private String lastLoginIp;

    /**
     * 部门，ID；字段类型：int；值：可选；默认值：无
     */
    @NotNull(message = "{NotNull.userBean.departmentId}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_department_id")
    private Integer departmentId;

    /**
     * 角色，ID；字段类型：varchar(50)；值：可选【以  1|2|3 表示；对应表：角色表(t_role) 的 id】；默认值：空字符串
     */
    @NotEmpty(message = "{NotEmpty.userBean.roles}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_roles")
    private String roles;

    /**
     * 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     */
    @NotEmpty(message = "{NotEmpty.userBean.status}", groups = {IBaseValidGroupAdd.class, IBaseValidGroupUpdate.class})
    @Column(name = "user_status")
    private String status;

    /**
     * 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
     */
    @Column(name = "user_create_date_time")
    private Date createDateTime;

    /**
     * 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
     */
    @Column(name = "user_delete_flag")
    private String deleteFlag;

    /**
     * 获取用户，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @return user_id - 用户，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     *
     * @param id 用户，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return user_username - 用户名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param username 用户名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户密码；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @return user_password - 用户密码；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     *
     * @param password 用户密码；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户类型；字段类型：CHAR(8)；值：NOT NULL【以 Employee、Client】，默认值：无
     *
     * @return user_type - 用户类型；字段类型：CHAR(8)；值：NOT NULL【以 Employee、Client】，默认值：无
     */
    public String getType() {
        return type;
    }

    /**
     * 设置用户类型；字段类型：CHAR(8)；值：NOT NULL【以 Employee、Client】，默认值：无
     *
     * @param type 用户类型；字段类型：CHAR(8)；值：NOT NULL【以 Employee、Client】，默认值：无
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取用户编号；字段类型VARCHAR(11)；值：NOT NULL【若为用户类型为 Client，此字段“用户编号”，即为 t_client.client_id 字段，】；默认值：空字符串
     *
     * @return user_sn - 用户编号；字段类型VARCHAR(11)；值：NOT NULL【若为用户类型为 Client，此字段“用户编号”，即为 t_client.client_id 字段，】；默认值：空字符串
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置用户编号；字段类型VARCHAR(11)；值：NOT NULL【若为用户类型为 Client，此字段“用户编号”，即为 t_client.client_id 字段，】；默认值：空字符串
     *
     * @param sn 用户编号；字段类型VARCHAR(11)；值：NOT NULL【若为用户类型为 Client，此字段“用户编号”，即为 t_client.client_id 字段，】；默认值：空字符串
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取姓名，名；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     *
     * @return user_first_name - 姓名，名；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 设置姓名，名；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     *
     * @param firstName 姓名，名；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 获取姓名，姓；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     *
     * @return user_last_name - 姓名，姓；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 设置姓名，姓；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     *
     * @param lastName 姓名，姓；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 获取手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
     *
     * @return user_phone - 手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无
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
     * 获取座机电话号；字段类型：varchar(15)；值：可选；默认值：无
     *
     * @return user_landline - 座机电话号；字段类型：varchar(15)；值：可选；默认值：无
     */
    public String getLandline() {
        return landline;
    }

    /**
     * 设置座机电话号；字段类型：varchar(15)；值：可选；默认值：无
     *
     * @param landline 座机电话号；字段类型：varchar(15)；值：可选；默认值：无
     */
    public void setLandline(String landline) {
        this.landline = landline;
    }

    /**
     * 获取电子邮箱；字段类型：varchar(20)；值：NOT NULL；默认值：无
     *
     * @return user_email - 电子邮箱；字段类型：varchar(20)；值：NOT NULL；默认值：无
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱；字段类型：varchar(20)；值：NOT NULL；默认值：无
     *
     * @param email 电子邮箱；字段类型：varchar(20)；值：NOT NULL；默认值：无
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取性别；字段类型：CHAR(6)；值：NOT NULL【以 Male、Female】；默认值：无
     *
     * @return user_gender - 性别；字段类型：CHAR(6)；值：NOT NULL【以 Male、Female】；默认值：无
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别；字段类型：CHAR(6)；值：NOT NULL【以 Male、Female】；默认值：无
     *
     * @param gender 性别；字段类型：CHAR(6)；值：NOT NULL【以 Male、Female】；默认值：无
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取生日；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     *
     * @return user_birthday - 生日；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     *
     * @param birthday 生日；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取身份证号；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无；备注：是 西班牙语什么的缩写 Clave Única de Registro de Población (CURP)的缩写。是一串代表身份的数字和字母,就类似身份证号码一样。
     *
     * @return user_curp - 身份证号；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无；备注：是 西班牙语什么的缩写 Clave Única de Registro de Población (CURP)的缩写。是一串代表身份的数字和字母,就类似身份证号码一样。
     */
    public String getCurp() {
        return curp;
    }

    /**
     * 设置身份证号；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无；备注：是 西班牙语什么的缩写 Clave Única de Registro de Población (CURP)的缩写。是一串代表身份的数字和字母,就类似身份证号码一样。
     *
     * @param curp 身份证号；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无；备注：是 西班牙语什么的缩写 Clave Única de Registro de Población (CURP)的缩写。是一串代表身份的数字和字母,就类似身份证号码一样。
     */
    public void setCurp(String curp) {
        this.curp = curp;
    }

    /**
     * 获取个人，住址；字段类型：varchar(100)；值：可选；默认值：无
     *
     * @return user_address - 个人，住址；字段类型：varchar(100)；值：可选；默认值：无
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置个人，住址；字段类型：varchar(100)；值：可选；默认值：无
     *
     * @param address 个人，住址；字段类型：varchar(100)；值：可选；默认值：无
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取个人，头像；字段类型：VARCHAR(20)；值：可选；默认为：无
     *
     * @return user_personal_photo - 个人，头像；字段类型：VARCHAR(20)；值：可选；默认为：无
     */
    public String getPersonalPhoto() {
        return personalPhoto;
    }

    /**
     * 设置个人，头像；字段类型：VARCHAR(20)；值：可选；默认为：无
     *
     * @param personalPhoto 个人，头像；字段类型：VARCHAR(20)；值：可选；默认为：无
     */
    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    /**
     * 获取个人，简介；字段类型：varchar(300)；值：可选；默认值：无
     *
     * @return user_personal_profile - 个人，简介；字段类型：varchar(300)；值：可选；默认值：无
     */
    public String getPersonalProfile() {
        return personalProfile;
    }

    /**
     * 设置个人，简介；字段类型：varchar(300)；值：可选；默认值：无
     *
     * @param personalProfile 个人，简介；字段类型：varchar(300)；值：可选；默认值：无
     */
    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }

    /**
     * 获取最近登陆，时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：可选；默认值：无
     *
     * @return user_last_login_time - 最近登陆，时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：可选；默认值：无
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近登陆，时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：可选；默认值：无
     *
     * @param lastLoginTime 最近登陆，时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：可选；默认值：无
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取最近登陆，IP；字段类型：varchar(20)；值：可选；默认值：无
     *
     * @return user_last_login_ip - 最近登陆，IP；字段类型：varchar(20)；值：可选；默认值：无
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置最近登陆，IP；字段类型：varchar(20)；值：可选；默认值：无
     *
     * @param lastLoginIp 最近登陆，IP；字段类型：varchar(20)；值：可选；默认值：无
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取部门，ID；字段类型：int；值：可选；默认值：无
     *
     * @return user_department_id - 部门，ID；字段类型：int；值：可选；默认值：无
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置部门，ID；字段类型：int；值：可选；默认值：无
     *
     * @param departmentId 部门，ID；字段类型：int；值：可选；默认值：无
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取角色，ID；字段类型：varchar(50)；值：可选【以  1|2|3 表示；对应表：角色表(t_role) 的 id】；默认值：空字符串
     *
     * @return user_roles - 角色，ID；字段类型：varchar(50)；值：可选【以  1|2|3 表示；对应表：角色表(t_role) 的 id】；默认值：空字符串
     */
    public String getRoles() {
        return roles;
    }

    /**
     * 设置角色，ID；字段类型：varchar(50)；值：可选【以  1|2|3 表示；对应表：角色表(t_role) 的 id】；默认值：空字符串
     *
     * @param roles 角色，ID；字段类型：varchar(50)；值：可选【以  1|2|3 表示；对应表：角色表(t_role) 的 id】；默认值：空字符串
     */
    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * 获取状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
     *
     * @return user_status - 状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable
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
     * @return user_create_date_time - 创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间
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
     * @return user_delete_flag - 逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
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