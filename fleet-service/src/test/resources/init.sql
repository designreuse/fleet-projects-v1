DROP DATABASE IF EXISTS fleet_study;
CREATE DATABASE fleet_study
  CHARACTER SET utf8;
USE fleet_study;

# DROP DATABASE IF EXISTS fleet;
# CREATE DATABASE fleet
#   CHARACTER SET utf8;
# USE fleet;

DROP TABLE IF EXISTS t_department;
CREATE TABLE t_department (
  department_id               INT(11)     NOT NULL                AUTO_INCREMENT
  COMMENT '部门，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无',
  department_name             VARCHAR(20) NOT NULL
  COMMENT '部门，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无',
  department_status           CHAR(7)     NOT NULL                DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',


  department_create_date_time DATETIME    NOT NULL                DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  department_delete_flag      CHAR(6)     NOT NULL                DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (department_id)
)
  COMMENT '部门表';
INSERT INTO t_department (department_name, department_status, department_create_date_time, department_delete_flag)
VALUES
  ('技术部', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('运输部', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('安全部', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('Client', 'Enable', '2016-05-01 09:30:00', 'Normal');

SELECT *
FROM
  t_department;

-- ######################################################################
--    用户模块【用户；权限；用户与权限；多对多关系】
-- ######################################################################
DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role (
  role_id               INT(11)  NOT NULL                AUTO_INCREMENT
  COMMENT '角色，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无',
  role_name             VARCHAR(20)
  COMMENT '角色，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  role_privilege        VARCHAR(1024)
  COMMENT '角色，对应的权限；字段类型：VARCHAR(1024)；值：NOT NULL【此处以 taskList|taskAdd|taskUpdate|taskDelete 风格进行表示；对应表：无表状态；默认为0；代表权限】；默认值：无',
  role_status           CHAR(7)  NOT NULL                DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',


  role_create_date_time DATETIME NOT NULL                DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  role_delete_flag      CHAR(6)  NOT NULL                DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (role_id)
)
  COMMENT '用户角色';

INSERT INTO t_role (role_name, role_privilege, role_status, role_create_date_time, role_delete_flag)
VALUES
  ('admin role', 'taskList|taskView|taskAdd|taskUpdate|taskDelete', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('driver role', 'taskList|taskView|taskUpdate', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('bodyguard role', '', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('client role', '', 'Enable', '2016-05-01 09:30:00', 'Normal'),
  ('test', 'taskList|taskView|taskAdd|taskUpdate|taskDelete', 'Enable', '2016-05-01 09:30:00', 'Normal');


SELECT *
FROM t_role;


DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  user_id               INT(11)     NOT NULL                AUTO_INCREMENT
  COMMENT '用户，ID；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无',
  user_username         VARCHAR(20) NOT NULL
  COMMENT '用户名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  user_password         VARCHAR(20) NOT NULL
  COMMENT '用户密码；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',

  user_type             CHAR(8)     NOT NULL
  COMMENT '用户类型；字段类型：CHAR(8)；值：NOT NULL【以 Employee、Client】，默认值：无',
  user_sn               VARCHAR(11) NOT NULL                DEFAULT ''
  COMMENT '用户编号；字段类型VARCHAR(11)；值：NOT NULL【若为用户类型为 Client，此字段“用户编号”，即为 t_client.client_id 字段，】；默认值：空字符串',

  user_first_name       VARCHAR(50) NOT NULL
  COMMENT '姓名，名；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无',
  user_last_name        VARCHAR(50) NOT NULL
  COMMENT '姓名，姓；字段类型：VARCHAR(50)；值：NOT NULL；默认值：无',

  user_phone            VARCHAR(15) NOT NULL
  COMMENT '手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无',
  user_landline         VARCHAR(15)
  COMMENT '座机电话号；字段类型：varchar(15)；值：可选；默认值：无',
  user_email            VARCHAR(20)
  COMMENT '电子邮箱；字段类型：varchar(20)；值：NOT NULL；默认值：无',

  user_gender           CHAR(6)
  COMMENT '性别；字段类型：CHAR(6)；值：NOT NULL【以 Male、Female】；默认值：无',
  user_birthday         DATE
  COMMENT '生日；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无',
  user_curp             VARCHAR(20) NOT NULL
  COMMENT '身份证号；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无；备注：是 西班牙语什么的缩写 Clave Única de Registro de Población (CURP)的缩写。是一串代表身份的数字和字母,就类似身份证号码一样。',

  user_address          VARCHAR(100)
  COMMENT '个人，住址；字段类型：varchar(100)；值：可选；默认值：无',
  user_personal_photo   VARCHAR(20)
  COMMENT '个人，头像；字段类型：VARCHAR(20)；值：可选；默认为：无',
  user_personal_profile VARCHAR(300)
  COMMENT '个人，简介；字段类型：varchar(300)；值：可选；默认值：无',

  user_last_login_time  DATETIME                            DEFAULT NULL
  COMMENT '最近登陆，时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：可选；默认值：无',
  user_last_login_ip    VARCHAR(20)                         DEFAULT NULL
  COMMENT '最近登陆，IP；字段类型：varchar(20)；值：可选；默认值：无',

  user_department_id    INT                                 DEFAULT NULL
  COMMENT '部门，ID；字段类型：int；值：可选；默认值：无',
  user_roles            VARCHAR(50)                         DEFAULT ''
  COMMENT '角色，ID；字段类型：varchar(50)；值：可选【以  1|2|3 表示；对应表：角色表(t_role) 的 id】；默认值：空字符串',

  user_status           CHAR(7)     NOT NULL                DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',


  user_create_date_time DATETIME    NOT NULL                DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  user_delete_flag      CHAR(6)     NOT NULL                DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (user_id)
)
  COMMENT '用户表';

INSERT INTO t_user
(user_username, user_password, user_type, user_sn, user_first_name, user_last_name, user_phone, user_landline, user_email, user_gender, user_birthday, user_curp
  , user_address, user_personal_photo, user_personal_profile, user_last_login_time, user_last_login_ip, user_department_id, user_roles, user_status, user_create_date_time, user_delete_flag)
VALUES
  ('admin1', 'admin1', 'Employee', '', 'ad', 'min', '12345678901', '020-37579662', 'admin1@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'admin1 address', '头像url', 'admin1 personal_profile', '2016-04-19 14:22:22', '192.168.1.10', 1, '|1|', 'Enable', '2016-04-19 10:50:30', 'Normal'),
  ('test1', 'test1', 'Employee', '', 'su', 'peng', '12345678901', '020-37579662', 'test1@mail.com', 'Female', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'test1 address', '头像url', 'test1 personal_profile', '2016-04-20 14:22:22', '192.168.1.10', 1, '|1|2|3|4|', 'Enable', '2016-04-20 10:50:30', 'Normal'),

  ('driver1', 'driver1', 'Employee', '', 'driver1 FirstName', 'driver1 LastName', '12345678901', '020-37579662', 'driver1@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver1 address', '头像url', 'driver1 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver2', 'driver2', 'Employee', '', 'driver2 FirstName', 'driver2 LastName', '12345678901', '020-37579662', 'driver2@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver2 address', '头像url', 'driver2 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver3', 'driver3', 'Employee', '', 'driver3 FirstName', 'driver3 LastName', '12345678901', '020-37579662', 'driver3@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver3 address', '头像url', 'driver3 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver4', 'driver4', 'Employee', '', 'driver4 FirstName', 'driver4 LastName', '12345678901', '020-37579662', 'driver4@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver4 address', '头像url', 'driver4 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver5', 'driver5', 'Employee', '', 'driver5 FirstName', 'driver5 LastName', '12345678901', '020-37579662', 'driver5@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver5 address', '头像url', 'driver5 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver6', 'driver6', 'Employee', '', 'driver6 FirstName', 'driver6 LastName', '12345678901', '020-37579662', 'driver6@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver6 address', '头像url', 'driver6 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver7', 'driver7', 'Employee', '', 'driver7 FirstName', 'driver7 LastName', '12345678901', '020-37579662', 'driver7@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver7 address', '头像url', 'driver7 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver8', 'driver8', 'Employee', '', 'driver8 FirstName', 'driver8 LastName', '12345678901', '020-37579662', 'driver8@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver8 address', '头像url', 'driver8 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver9', 'driver9', 'Employee', '', 'driver9 FirstName', 'driver9 LastName', '12345678901', '020-37579662', 'driver9@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver9 address', '头像url', 'driver9 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),
  ('driver10', 'driver10', 'Employee', '', 'driver10 FirstName', 'driver10 LastName', '12345678901', '020-37579662', 'driver10@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'driver10 address', '头像url', 'driver10 personal_profile', '2016-04-22 14:22:22', '192.168.1.10', 2, '|2|4|', 'Enable', '2016-04-22 10:50:30', 'Normal'),

  ('bodyguard1', 'bodyguard1', 'Employee', '', 'bodyguard1 FirstName', 'bodyguard1 LastName', '12345678901', '020-37579662', 'bodyguard1@mail.com', 'Female', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'bodyguard1 address', '头像url', 'bodyguard1 personal_profile', '2016-04-25 14:22:22', '192.168.1.10', 3, '|3|', 'Enable', '2016-04-25 10:50:30', 'Normal'),
  ('bodyguard2', 'bodyguard2', 'Employee', '', 'bodyguard2 FirstName', 'bodyguard2 LastName', '12345678901', '020-37579662', 'bodyguard2@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'bodyguard2 address', '头像url', 'bodyguard2 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 3, '|3|', 'Enable', '2016-04-26 10:50:30', 'Normal'),
  ('bodyguard3', 'bodyguard3', 'Employee', '', 'bodyguard3 FirstName', 'bodyguard3 LastName', '12345678901', '020-37579662', 'bodyguard3@mail.com', 'Female', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'bodyguard3 address', '头像url', 'bodyguard3 personal_profile', '2016-04-25 14:22:22', '192.168.1.10', 3, '|3|', 'Enable', '2016-04-25 10:50:30', 'Normal'),
  ('bodyguard4', 'bodyguard4', 'Employee', '', 'bodyguard4 FirstName', 'bodyguard4 LastName', '12345678901', '020-37579662', 'bodyguard4@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'bodyguard4 address', '头像url', 'bodyguard4 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 3, '|3|', 'Enable', '2016-04-26 10:50:30', 'Normal'),
  ('bodyguard5', 'bodyguard5', 'Employee', '', 'bodyguard5 FirstName', 'bodyguard5 LastName', '12345678901', '020-37579662', 'bodyguard5@mail.com', 'Female', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'bodyguard5 address', '头像url', 'bodyguard5 personal_profile', '2016-04-25 14:22:22', '192.168.1.10', 3, '|3|', 'Enable', '2016-04-25 10:50:30', 'Normal'),
  ('bodyguard6', 'bodyguard6', 'Employee', '', 'bodyguard6 FirstName', 'bodyguard6 LastName', '12345678901', '020-37579662', 'bodyguard6@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'bodyguard6 address', '头像url', 'bodyguard6 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 3, '|3|', 'Enable', '2016-04-26 10:50:30', 'Normal'),

  ('client1', 'client1', 'Client', '1', '沃尔玛', '账号1', '12345678901', '020-37579662', 'client1@mail.com', 'Female', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'client1 address', '头像url', 'client1 personal_profile', '2016-04-25 14:22:22', '192.168.1.10', 4, '|5|', 'Enable', '2016-04-25 10:50:30', 'Normal'),
  ('client2', 'client2', 'Client', '1', '沃尔玛', '账号2', '12345678901', '020-37579662', 'client2@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'client2 address', '头像url', 'client2 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 4, '|5|', 'Enable', '2016-04-26 10:50:30', 'Normal'),
  ('client3', 'client3', 'Client', '1', '沃尔玛', '账号3', '12345678901', '020-37579662', 'client3@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'client3 address', '头像url', 'client3 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 4, '|5|', 'Enable', '2016-04-26 10:50:30', 'Normal'),
  ('client4', 'client4', 'Client', '2', '家乐福', '账号1', '12345678901', '020-37579662', 'client4@mail.com', 'Female', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'client4 address', '头像url', 'client4 personal_profile', '2016-04-25 14:22:22', '192.168.1.10', 4, '|5|', 'Enable', '2016-04-25 10:50:30', 'Normal'),
  ('client5', 'client5', 'Client', '2', '家乐福', '账号2', '12345678901', '020-37579662', 'client5@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'client5 address', '头像url', 'client5 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 4, '|5|', 'Enable', '2016-04-26 10:50:30', 'Normal'),
  ('client3', 'client3', 'Client', '2', '家乐福', '账号3', '12345678901', '020-37579662', 'client6@mail.com', 'Male', '2016-04-05', 'DIEG821113HMCZSR06'
    , 'client6 address', '头像url', 'client6 personal_profile', '2016-04-26 14:22:22', '192.168.1.10', 4, '|5|', 'Enable', '2016-04-26 10:50:30', 'Normal');
SELECT *
FROM t_user;

DROP PROCEDURE IF EXISTS pro_create_data_User;
CREATE PROCEDURE pro_create_data_User()
  BEGIN

    DECLARE i INT;
    SET i = 101;
    WHILE i <= 150 DO
      INSERT INTO t_user
      (user_username, user_password, user_type, user_sn, user_first_name, user_last_name, user_phone, user_landline, user_email, user_gender, user_birthday, user_curp
        , user_address, user_personal_photo, user_personal_profile, user_last_login_time, user_last_login_ip, user_department_id, user_roles, user_status, user_create_date_time, user_delete_flag)
      VALUES
        (concat('temp', i), concat('temp', i), 'Employee', '', concat('temp', i, 'LastName'), concat('temp', i, ' FirstName'), '12345678901', '020-37579662', concat('temp', i, '@temp.com')
          , 'Male', '2016-04-05', 'DIEG821113HMCZSR06', concat('temp', i, ' Address'), concat('temp', i, ' 头像url'), concat('temp', i, ' personal_profile'), now(), '192.168.1.10', 1, '|1|'
          , 'Disable', now(), 'Normal');
      SET i = i + 1;
    END WHILE;

  END;

CALL pro_create_data_User();

SELECT *
FROM t_user;


DROP TABLE IF EXISTS t_vehicle;
CREATE TABLE t_vehicle (
  vehicle_id                 INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT '车辆，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无',
  vehicle_name               VARCHAR(20) NOT NULL
  COMMENT '车辆，名称；字段类型：VARCHAR(20)；值：NOT NULL【】',
  vehicle_license_plate      VARCHAR(20) NOT NULL
  COMMENT '车辆，牌照；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无',
  vehicle_location_device_id VARCHAR(11) NOT NULL
  COMMENT '车辆，定位设置ID；字段类型：VARCHAR(11)；值：NOT NULL【】；默认值：无',


  vehicle_status             CHAR(7)     NOT NULL DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',


  vehicle_create_date_time   DATETIME    NOT NULL DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  vehicle_delete_flag        CHAR(6)     NOT NULL DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (vehicle_id)
)
  COMMENT '车辆管理表';

INSERT INTO t_vehicle (vehicle_name, vehicle_license_plate, vehicle_location_device_id, vehicle_status, vehicle_create_date_time, vehicle_delete_flag)
VALUES
  ('车辆1', '粤A888881', 1, 'Enable', '2016-05-01 12:30:30', 'Normal'),
  ('车辆2', '粤A888882', 2, 'Enable', '2016-05-01 16:30:30', 'Normal'),
  ('车辆3', '粤A888883', 3, 'Enable', '2016-05-02 12:30:30', 'Normal'),
  ('车辆4', '粤A888884', 4, 'Enable', '2016-05-02 16:30:30', 'Normal'),
  ('车辆5', '粤A888885', 5, 'Enable', '2016-05-03 12:30:30', 'Normal'),
  ('车辆6', '粤A888886', 6, 'Enable', '2016-05-03 16:30:30', 'Normal'),
  ('车辆7', '粤A888887', 7, 'Enable', '2016-05-04 12:30:30', 'Normal'),
  ('车辆8', '粤A888888', 8, 'Enable', '2016-05-04 16:30:30', 'Normal'),
  ('车辆9', '粤A888889', 9, 'Enable', '2016-05-05 12:30:30', 'Normal'),
  ('车辆10', '粤A888890', 10, 'Enable', '2016-05-05 16:30:30', 'Normal'),
  ('车辆11', '粤A888891', 10, 'Enable', '2016-05-06 12:30:30', 'Normal'),
  ('车辆12', '粤A888892', 12, 'Enable', '2016-05-06 16:30:30', 'Normal'),
  ('车辆13', '粤A888893', 13, 'Enable', '2016-05-07 12:30:30', 'Normal'),
  ('车辆14', '粤A888894', 14, 'Enable', '2016-05-07 16:30:30', 'Normal'),
  ('车辆15', '粤A888895', 15, 'Enable', '2016-05-08 12:30:30', 'Normal'),
  ('车辆16', '粤A888896', 16, 'Enable', '2016-05-08 16:30:30', 'Normal'),
  ('车辆17', '粤A888897', 17, 'Enable', '2016-05-09 12:30:30', 'Normal'),
  ('车辆18', '粤A888898', 18, 'Enable', '2016-05-09 16:30:30', 'Normal'),
  ('车辆19', '粤A888899', 19, 'Enable', '2016-05-10 12:30:30', 'Normal'),
  ('车辆20', '粤A888900', 20, 'Enable', '2016-05-10 16:30:30', 'Normal');

SELECT *
FROM t_vehicle;


DROP TABLE IF EXISTS t_client;
CREATE TABLE t_client (
  client_id               INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT '客户id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无',
  client_name             VARCHAR(50)          DEFAULT NULL
  COMMENT '客户名',
  client_phone            VARCHAR(15) NOT NULL
  COMMENT '手机号；字段类型：varchar(15)；值：NOT NULL；默认值：无',

  client_status           CHAR(7)     NOT NULL DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',


  client_create_date_time DATETIME    NOT NULL DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  client_delete_flag      CHAR(6)     NOT NULL DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (client_id)
)
  COMMENT '客户表';

INSERT INTO t_client (client_name, client_phone, client_status, client_create_date_time, client_delete_flag)
VALUES
  ('沃尔玛', '17773322341', 'Enable', '2016-05-01 09:30', 'Normal'),
  ('家乐福', '17773322342', 'Enable', '2016-05-02 09:30', 'Normal'),
  ('7-ELEVEN', '17773322341', 'Enable', '2016-05-01 09:30', 'Normal');

SELECT *
FROM t_client;


DROP TABLE IF EXISTS t_client_branch;
CREATE TABLE t_client_branch (
  branch_id                INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【系统自增长】；默认值：无',
  branch_name              VARCHAR(20)           DEFAULT NULL
  COMMENT '客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  branch_type              VARCHAR(10)  NOT NULL
  COMMENT '客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无',

  branch_client_id         INT(11)      NOT NULL
  COMMENT '客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无',
  branch_client_name       VARCHAR(100) NOT NULL
  COMMENT '客户，名；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无',

  branch_contact           VARCHAR(20)  NOT NULL
  COMMENT '客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  branch_phone             VARCHAR(15)  NOT NULL
  COMMENT '客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无',

  branch_address           VARCHAR(100) NOT NULL
  COMMENT '客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无',
  branch_latitude          VARCHAR(15)  NOT NULL
  COMMENT '客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无',
  branch_longitude         VARCHAR(15)  NOT NULL
  COMMENT '客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无',

  branch_window_start_time TIME         NOT NULL
  COMMENT '客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无',
  branch_window_end_time   TIME         NOT NULL
  COMMENT '客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无',
  branch_window_duration   TIME         NOT NULL
  COMMENT '客户，分支机构，窗口，预计时长；字段类型：TIME；值：NOT NULL；默认值：无',

  branch_cron_expression   VARCHAR(30)  NOT NULL DEFAULT ''
  COMMENT '客户，分支机构，任务计划周期性；字段类型：VARCHAR(30)；值：NOT NULL【天 月 周】；默认值：空',

  branch_key_code          VARCHAR(20)  NOT NULL
  COMMENT '客户，分支机构，钥匙；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  branch_keychain_id       INT(11)
  COMMENT '客户，分支机构，当前钥匙对应的钥匙串ID；字段类型：INT(11)对应表 t_key_ring.id；值：可选；默认值：无',
  branch_keychain_name     VARCHAR(20)
  COMMENT '客户，分支机构，当前钥匙对应的钥匙串Name；字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无',

  branch_status            CHAR(7)      NOT NULL DEFAULT 'Enable'
  COMMENT '客户，分支机构，状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',


  branch_create_date_time  DATETIME     NOT NULL DEFAULT now()
  COMMENT '客户，分支机构，创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  branch_delete_flag       CHAR(6)      NOT NULL DEFAULT 'Normal'
  COMMENT '客户，分支机构，逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (branch_id)
)
  COMMENT '客户分支机构表【1、 客户有多个分支机构，可以有多次合同【可分柜台，ATM机】2、客户的分支机构，是含在合同中；一份合同只能是一个客户，且是一个或多个分支机构；3、一个分支机构，根据业务功能不同【可分柜台，ATM机】，只能有一个有效合同期内执行。】';

#CronTrigger配置完整格式为： [秒] [分] [小时]      [日]   [月]     [周]    [年]

INSERT INTO t_client_branch
(branch_name, branch_type, branch_client_id, branch_client_name, branch_contact, branch_phone, branch_address, branch_latitude, branch_longitude,
 branch_window_start_time, branch_window_end_time, branch_window_duration, branch_cron_expression, `branch_key_code`, branch_keychain_id, branch_keychain_name, branch_status, branch_create_date_time, branch_delete_flag)
VALUES
  ('沃尔玛天河区店', 'Counter', 1, '沃尔玛', '负责人姓名1', '17773322340', '广州市天河区石东小学', '23.1138305417', '113.3639196018', '08:10:00', '09:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 1, 'test10001', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛海珠区店', 'Counter', 1, '沃尔玛', '负责人姓名2', '17773322340', '广州塔', '23.1064630857', '113.3244370854', '08:10:00', '09:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 2, 'test10002', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛越秀区店', 'Counter', 1, '沃尔玛', '负责人姓名3', '17773322340', '颐景轩', '23.1139466467', '113.3112889667', '08:10:00', '09:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 3, 'test10003', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛白云区店', 'Counter', 1, '沃尔玛', '负责人姓名4', '17773322340', '同和', '23.1972794878', '113.3261547407', '08:10:00', '09:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 1, 'test10001', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛番禺店', 'Counter', 1, '沃尔玛', '负责人姓名5', '17773322340', '市桥', '22.9495562909', '113.3618031200', '08:10:00', '09:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 2, 'test10002', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛荔湾区店', 'Counter', 1, '沃尔玛', '负责人姓名6', '17773322340', '荔湾广场', '23.1149780381', '113.2488178024', '08:10:00', '09:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 3, 'test10003', 'Enable', '2016-05-03 14:30:00', 'Normal'),

  ('家乐福天河区店', 'Counter', 2, '家乐福', '负责人姓名1', '17773322340', '广州市天河区石东小学', '23.1138305417', '113.3639196018', '10:10:00', '11:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 1, 'test10001', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福海珠区店', 'Counter', 2, '家乐福', '负责人姓名2', '17773322340', '广州塔', '23.1064630857', '113.3244370854', '10:10:00', '11:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 2, 'test10002', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福越秀区店', 'Counter', 2, '家乐福', '负责人姓名3', '17773322340', '颐景轩', '23.1139466467', '113.3112889667', '10:10:00', '11:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 3, 'test10003', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福白云区店', 'Counter', 2, '家乐福', '负责人姓名4', '17773322340', '同和', '23.1972794878', '113.3261547407', '10:10:00', '11:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 1, 'test10001', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福番禺店', 'Counter', 2, '家乐福', '负责人姓名5', '17773322340', '市桥', '22.9495562909', '113.3618031200', '10:10:00', '11:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 2, 'test10002', 'Enable', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福荔湾区店', 'Counter', 2, '家乐福', '负责人姓名6', '17773322340', '荔湾广场', '23.1149780381', '113.2488178024', '10:10:00', '11:30:00', '00:40:00'
    , '* * * 1-9,11-31 * ? *', '钥匙一套', 3, 'test10003', 'Enable', '2016-05-03 14:30:00', 'Normal');

SELECT *
FROM t_client_branch;

DROP TABLE IF EXISTS t_keychain;
CREATE TABLE t_keychain (
  keychain_id                 INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT '钥匙串，ID；       字段类型：int(11)； 值：NOT NULL【系统自增长方式】；默认值：无',
  keychain_name               VARCHAR(20) NOT NULL
  COMMENT '钥匙串，名称；     字段类型：VARCHAR(20)；     值：NOT NULL；默认值：无',

  keychain_assign_driver_id   INT(11)
  COMMENT '钥匙串，分配的，司机,ID ；字段类型：int(11)；值：；默认值：无',
  keychain_assign_driver_name VARCHAR(100)
  COMMENT '钥匙串，分配的，司机，名称 ；字段类型：VARCHAR(100)；值：；默认值：无',
  keychain_status             CHAR(10)
  COMMENT '钥匙串状态 ；字段类型：CHAR(10)；值：【以 空闲Idle 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无',

  keychain_create_date_time   DATETIME    NOT NULL DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  keychain_delete_flag        CHAR(6)     NOT NULL DEFAULT 'Normal'
  COMMENT '钥匙串逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (keychain_id)
)
  COMMENT '钥匙串表';

INSERT INTO t_keychain (keychain_name, keychain_assign_driver_id, keychain_assign_driver_name, keychain_status, keychain_create_date_time, keychain_delete_flag)
VALUES
  ('test10001', 3, 'driver1', 'Wait', '2016-05-19 23:00:03', 'Normal'),
  ('test10002', 3, 'driver1', 'Wait', '2016-05-19 23:00:32', 'Normal'),
  ('test10003', 3, 'driver1', 'Wait', '2016-05-19 23:00:53', 'Normal');

SELECT *
FROM t_keychain;

DROP TABLE IF EXISTS t_task;
CREATE TABLE t_task (
  task_id                              INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '任务，id；字段类型：INT(11)；值：NOT NULL【系统自增长方式】；默认值：无',
  task_name                            VARCHAR(20)  NOT NULL
  COMMENT '任务，名称；字段类型：varchar(20)；值：NOT NULL【推荐：客户名简称+分支机构的名】；默认值：无',
  task_type                            VARCHAR(20)  NOT NULL
  COMMENT '任务，类型；字段类型：VARCHAR(20)；值：NOT NULL【以 收钱 Receive_Money，送钱 Give_Money，ATM维护 Maintain_ATM】；默认值：无',
  task_execution_date                  DATE         NOT NULL
  COMMENT '任务，执行日期；字段类型：DATE(yyyy-mm-dd)；值：NOT NULL；默认值：无',
  task_status                          VARCHAR(20)  NOT NULL
  COMMENT '任务，状态【即t_task_tranaction.transaction_operate_type，对应】；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】；默认值：无',

  task_client_id                       INT(11)      NOT NULL
  COMMENT '客户，ID；字段类型：INT(11)；值：NOT NULL；默认值：无',
  task_client_name                     VARCHAR(20)  NOT NULL
  COMMENT '客户，姓名；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',

  task_client_branch_id                INT(11)      NOT NULL
  COMMENT '客户，分支机构，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无',
  task_client_branch_name              VARCHAR(50)  NOT NULL
  COMMENT '客户，分支机构，名称；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  task_client_branch_type              VARCHAR(10)  NOT NULL
  COMMENT '客户，分支机构，类型类型；字段类型：VARCHAR(10)；值：NOT NULL【以 Counter[柜台]、ATM】；默认值：无',
  task_client_branch_contact           VARCHAR(20)  NOT NULL
  COMMENT '客户，分支机构，联系人；字段类型：VARCHAR(20)；值：NOT NULL；默认值：无',
  task_client_branch_phone             VARCHAR(15)  NOT NULL
  COMMENT '客户，分支机构，联系人，电话；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无',
  task_client_branch_address           VARCHAR(100) NOT NULL
  COMMENT '客户，分支机构，地址详细；字段类型：VARCHAR(100)；值：NOT NULL；默认值：无',
  task_client_branch_latitude          VARCHAR(15)  NOT NULL
  COMMENT '客户，分支机构，地址的纬度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无',
  task_client_branch_longitude         VARCHAR(15)  NOT NULL
  COMMENT '客户，分支机构，地址的经度；字段类型：VARCHAR(15)；值：NOT NULL；默认值：无',
  task_client_branch_window_start_time TIME         NOT NULL
  COMMENT '客户，分支机构，窗口，开始时间；字段类型：TIME；值：NOT NULL；默认值：无',
  task_client_branch_window_end_time   TIME         NOT NULL
  COMMENT '客户，分支机构，窗口，结束时间；字段类型：TIME；值：NOT NULL；默认值：无',
  task_client_branch_window_duration   TIME         NOT NULL
  COMMENT '客户，分支机构，窗口，时长；字段类型：TIME；值：NOT NULL；默认值：无',

  task_operator_id                     INT(11)      NOT NULL
  COMMENT '任务，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无',
  task_operator_name                   VARCHAR(100) NOT NULL
  COMMENT '任务，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无',
  task_operate_date_time               DATETIME              DEFAULT now()
  COMMENT '任务，操作的时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：空；默认值：now() 当前服务器系统时间',
  task_ownev_id                        INT(11)
  COMMENT '任务，责任人，ID；字段类型：INT(11)；值：空；默认值：无',
  task_ownev_name                      VARCHAR(100)
  COMMENT '任务，责任人，name；字段类型：VARCHAR(100) 值：空；默认值：无',

  task_driver_id                       INT(11)
  COMMENT '任务，分配的司机，ID；字段类型：INT(11)；值：空；默认值：无',
  task_driver_name                     VARCHAR(100)
  COMMENT '任务，分配的司机，name；字段类型：VARCHAR(100) 值：空；默认值：无',

  task_create_date_time                DATETIME     NOT NULL DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  task_delete_flag                     CHAR(6)      NOT NULL DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (task_id)
)
  COMMENT '任务表；根据客户合同订单；系统会自动生成任务【其中任何字段类型可分；系统生成；或人工建立】';

INSERT INTO t_task (task_name, task_type, task_execution_date, task_status, task_client_id, task_client_name
  , task_client_branch_id, task_client_branch_name, task_client_branch_type, task_client_branch_contact, task_client_branch_phone, task_client_branch_address, task_client_branch_latitude, task_client_branch_longitude, task_client_branch_window_start_time, task_client_branch_window_end_time, task_client_branch_window_duration
  , task_operator_id, task_operator_name, task_operate_date_time, task_ownev_id, task_ownev_name, task_driver_id, task_driver_name, task_create_date_time, task_delete_flag)
VALUES
  ('沃尔玛天河区店', 'Counter', '2016-05-16', 'Wait', 1, '沃尔玛', 1, '天河区店', 'Counter', '负责人姓名1', '17773322340', '广州市天河区石东小学', '23.1138305417', '113.3639196018', '08:10:00', '09:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛海珠区店', 'ATM', '2016-05-16', 'Wait', 1, '沃尔玛', 2, '海珠区店', 'ATM', '负责人姓名2', '17773322340', '广州塔', '23.1064630857', '113.3244370854', '08:10:00', '09:30:00', '00:40:00'
    , 2, 'test1', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛越秀区店', 'Counter', '2016-05-16', 'Wait', 1, '沃尔玛', 3, '越秀区店', 'Counter', '负责人姓名3', '17773322340', '颐景轩', '23.1139466467', '113.3112889667', '08:10:00', '09:30:00', '00:40:00'
    , 1, 'admin1', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛白云区店', 'ATM', '2016-05-16', 'Wait', 1, '沃尔玛', 4, '白云区店', 'ATM', '负责人姓名4', '17773322340', '同和', '23.1972794878', '113.3261547407', '08:10:00', '09:30:00', '00:40:00'
    , 15, '沃尔玛账号1', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛番禺店', 'Counter', '2016-05-16', 'Wait', 1, '沃尔玛', 5, '番禺店', 'Counter', '负责人姓名5', '17773322340', '市桥', '22.9495562909', '113.3618031200', '08:10:00', '09:30:00', '00:40:00'
    , 15, '沃尔玛账号1', now(), 1, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('沃尔玛荔湾区店', 'ATM', '2016-05-16', 'Wait', 1, '沃尔玛', 6, '荔湾区店', 'ATM', '负责人姓名6', '17773322340', '荔湾广场', '23.1149780381', '113.2488178024', '08:10:00', '09:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),

  ('家乐福天河区店', 'Counter', '2016-05-16', 'Wait', 2, '家乐福', 7, '天河区店', 'Counter', '负责人姓名1', '17773322340', '广州市天河区石东小学', '23.1138305417', '113.3639196018', '10:10:00', '11:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福海珠区店', 'ATM', '2016-05-16', 'Wait', 2, '家乐福', 8, '海珠区店', 'ATM', '负责人姓名2', '17773322340', '广州塔', '23.1064630857', '113.3244370854', '10:10:00', '11:30:00', '00:40:00'
    , 0, 'System', now(), 1, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福越秀区店', 'Counter', '2016-05-16', 'Wait', 2, '家乐福', 9, '越秀区店', 'Counter', '负责人姓名3', '17773322340', '颐景轩', '23.1139466467', '113.3112889667', '10:10:00', '11:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福白云区店', 'ATM', '2016-05-16', 'Wait', 2, '家乐福', 10, '白云区店', 'ATM', '负责人姓名4', '17773322340', '同和', '23.1972794878', '113.3261547407', '10:10:00', '11:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福番禺店', 'Counter', '2016-05-16', 'Wait', 2, '家乐福', 11, '番禺店', 'Counter', '负责人姓名5', '17773322340', '市桥', '22.9495562909', '113.3618031200', '10:10:00', '11:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal'),
  ('家乐福荔湾区店', 'ATM', '2016-05-16', 'Wait', 2, '家乐福', 12, '荔湾区店', 'ATM', '负责人姓名6', '17773322340', '荔湾广场', '23.1149780381', '113.2488178024', '10:10:00', '11:30:00', '00:40:00'
    , 0, 'System', now(), 3, 'driver1 LastName driver1 FirstName', 3, 'driver1 LastName driver1 FirstName', '2016-05-03 14:30:00', 'Normal');


DROP TABLE IF EXISTS t_task_transaction;
CREATE TABLE t_task_transaction (
  transaction_id              INT(11)      NOT NULL AUTO_INCREMENT
  COMMENT '任务交易记录，ID',
  transaction_task_id         INT(11)      NOT NULL
  COMMENT '任务交易记录，任务ID；字段类型：INT(11)；值：NOT NULL【关联task表中的task_id】；默认值：无',
  transaction_operator_id     INT(11)      NOT NULL
  COMMENT '任务交易记录，操作人，ID；字段类型：INT(11)；值：NOT NULL【0代表为系统自动生成的任务；其他即为：当前操作人的ID】；默认值：无',
  transaction_operator_name   VARCHAR(100) NOT NULL
  COMMENT '任务交易记录，操作人，姓名；字段类型：VARCHAR(100)；值：NOT NULL【System代表为系统自动生成的任务；其他即为：当前操作人的姓名】；默认值：无',

  transaction_operate_type    VARCHAR(20)  NOT NULL
  COMMENT '任务交易记录，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait_Assignment、分配中 Assignment[driver|vehicle|bodyguard]、收到 Receive、入库 Warehousing，结算中 settlement、fail、success】，默认值：无',
  transaction_operate_comment VARCHAR(150)
  COMMENT '任务交易记录，操作的内容，字段类型：VARCHAR(150)；值：NOT NULL【根据任务操作类型不同，所产生的】；默认值：无',
  transaction_operate_time    DATETIME     NOT NULL DEFAULT now()
  COMMENT '任务交易记录，操作的时间，字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：无',

  transaction_task_ownev_id   INT(11)
  COMMENT '任务交易记录，责任人，ID；字段类型：INT(11)；值：NOT NULL；默认值：无',
  transaction_task_ownev_name VARCHAR(100)
  COMMENT '任务交易记录，责任人，name；字段类型：VARCHAR(100) 值：NOT NULL；默认值：无',
  transaction_task_oeate_time DATETIME              DEFAULT now()
  COMMENT '任务交易记录，责任人的时间，字段类型：DATETIME；值：NOT NULL【一般情况下与操作的时间一致，其他情况：在责任人不是当前操作人的情况下，更新操作记录时间表 】，默认值：无',

  transaction_delete_flag     CHAR(6)      NOT NULL DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (transaction_id)
)
  COMMENT '任务交易记录表';

INSERT INTO t_task_transaction
(transaction_task_id, transaction_operator_id, transaction_operator_name, transaction_operate_type, transaction_operate_comment, transaction_operate_time
  , transaction_task_ownev_id, transaction_task_ownev_name, transaction_task_oeate_time, transaction_delete_flag)
VALUES
  (1, 1, 'admin', 'Assignment', 'Assignment driver', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'Assignment', 'Assignment vehicle', now(), 9, 'bodyguard1 LastName bodyguard1 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'Assignment', 'Assignment vehicle', now(), 10, 'bodyguard2 LastName bodyguard2 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'Assignment', 'Assignment vehicle', now(), 11, 'bodyguard3 LastName bodyguard3 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'Assignment', 'Assignment vehicle', now(), 12, 'bodyguard4 LastName bodyguard4 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'Receive', 'Receive comment', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'Warehousing', 'Warehousing', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),
  (1, 1, 'admin', 'settlement', 'settlement', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),

  (2, 2, 'test1', 'Assignment', 'Assignment driver', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'Assignment', 'Assignment vehicle', now(), 9, 'bodyguard1 LastName bodyguard1 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'Assignment', 'Assignment vehicle', now(), 10, 'bodyguard2 LastName bodyguard2 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'Assignment', 'Assignment vehicle', now(), 11, 'bodyguard3 LastName bodyguard3 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'Assignment', 'Assignment vehicle', now(), 12, 'bodyguard4 LastName bodyguard4 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'Receive', 'Receive comment', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'Warehousing', 'Warehousing', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal'),
  (2, 2, 'test1', 'settlement', 'settlement', now(), 3, 'driver1 LastName driver1 FirstName', now(), 'Normal');


DROP TABLE IF EXISTS t_latlng_driver;
CREATE TABLE t_latlng_driver (
  latlng_id                     INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT '经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无',
  latlng_driver_username        VARCHAR(20)          DEFAULT ''
  COMMENT '当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串',
  latlng_driver_name            VARCHAR(100)         DEFAULT ''
  COMMENT '当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串',

  latlng_phone_imei             VARCHAR(15)          DEFAULT ''
  COMMENT '司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串',
  latlng_phone_local_ip         VARCHAR(20) NOT NULL
  COMMENT '司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无',

  latlng_phone_lng              VARCHAR(15) NOT NULL
  COMMENT '司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无',
  latlng_phone_lat              VARCHAR(15) NOT NULL
  COMMENT '司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无',
  latlng_phone_upload_timestamp DATETIME    NOT NULL
  COMMENT '司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无',

  latlng_status                 CHAR(7)     NOT NULL DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',

  latlng_create_date_time       DATETIME    NOT NULL DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  latlng_delete_flag            CHAR(6)     NOT NULL DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (latlng_id)
)
  COMMENT '司机登陆设备【手机】，经纬度表'
  DEFAULT CHARSET = utf8
  ENGINE = MyISAM;


SELECT *
FROM t_latlng_driver;


DROP TABLE IF EXISTS t_latlng_driver_testtable;
CREATE TABLE t_latlng_driver_testtable (
  latlng_id                     INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT '经纬度，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无',
  latlng_driver_username        VARCHAR(20)          DEFAULT ''
  COMMENT '当前司机登陆账号；字段类型：VARCHAR(20)；值：【不强制】；默认值：空字符串',
  latlng_driver_name            VARCHAR(100)         DEFAULT ''
  COMMENT '当前司机，姓名；字段类型：VARCHAR(100)；值：【不强制】；默认值：空字符串',

  latlng_phone_imei             VARCHAR(15)          DEFAULT ''
  COMMENT '司机登陆手机设备，ID标识；字段类型：VARCHAR(15)；值：【不强制】；默认值：空字符串',
  latlng_phone_local_ip         VARCHAR(20) NOT NULL
  COMMENT '司机登陆手机设备，IP；字段类型：VARCHAR(20)；值：NOT NULL【】；默认值：无',

  latlng_phone_lng              VARCHAR(15) NOT NULL
  COMMENT '司机登陆手机设备，经度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无',
  latlng_phone_lat              VARCHAR(15) NOT NULL
  COMMENT '司机登陆手机设备，纬度；字段类型：VARCHAR(15)；值：NOT NULL【】；默认值：无',
  latlng_phone_upload_timestamp DATETIME    NOT NULL
  COMMENT '司机登陆手机设备，获取经纬度时间戳；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值得：NOT NULL；默认值：无',

  latlng_status                 CHAR(7)     NOT NULL DEFAULT 'Enable'
  COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',

  latlng_create_date_time       DATETIME    NOT NULL DEFAULT now()
  COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
  latlng_delete_flag            CHAR(6)     NOT NULL DEFAULT 'Normal'
  COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
  PRIMARY KEY (latlng_id)
)
  COMMENT '司机登陆设备【手机】，经纬度表'
  DEFAULT CHARSET = utf8
  ENGINE = MyISAM;

SELECT *
FROM t_latlng_driver_testtable;

# DROP TABLE IF EXISTS t_branch_matrix;
# CREATE TABLE t_branch_matrix (
#   matrix_id               INT(11)    NOT NULL                AUTO_INCREMENT
#   COMMENT '路线矩阵，ID；字段类型：INT(11)；值：NOT NULL【】；默认值：无',
#
#   #   matrix_start_branch_id  INT(11)
#   #   matrix_start_branch_location_lat,
#   #   matrix_start_branch_location_lng,
#   #
#   #   matrix_end_branch_id
#   #   matrix_end_branch_location_lat,
#   #   matrix_end_branch_location_lng,
#   #   matrix_distance,
#   #   matrix_duration,
#   #   matrix_route
#
#   matrix_status           CHAR(7) NOT NULL                DEFAULT 'Enable'
#   COMMENT '状态 ；字段类型：CHAR(7)；值：NOT NULL【以 Enable、Disable】；默认值：Enable',
#
#   matrix_create_date_time DATETIME   NOT NULL                DEFAULT now()
#   COMMENT '创建时间；字段类型：DateTime(yyyy-MM-dd HH:mm:ss)；值：NOT NULL；默认值：now() 当前服务器系统时间',
#   matrix_delete_flag      CHAR(6) NOT NULL                DEFAULT 'Normal'
#   COMMENT '逻辑删除标志；字段类型：CHAR(6)；值：NOT NULL【以 Remove、Normal】；默认值：Normal',
#   PRIMARY KEY (matrix_id)
# )
#   COMMENT '分支机构-分支机构，路线矩阵数据集';
