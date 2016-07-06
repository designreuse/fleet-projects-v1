package com.comodin.fleet.constants;

/**
 * Created by supeng on 2016/4/28.
 */
public class ConstantsFinalValue {


    /**
     * 项目统一，信息标识
     */
//token 标识
    public static final String TOKEN = "token";
    /**
     * The constant SERVER_REDIRECT_URL_MARK.
     */
    public static final String SERVER_REDIRECT_URL_MARK = "redirect_URL";

    /**
     * The constant INTERCEPTOR_URL_SUFFIX.
     */
//伪装url后缀
    public static final String INTERCEPTOR_URL_SUFFIX = ".php";
    /**
     * The constant UPLOAD_IMAGE_PATH.
     */
//图片上传路上temp
    public static final String UPLOAD_IMAGE_PATH = "/resources/upload/";

    /**
     * The constant ROLE_USER_PRIVILEGE_SEPARATOR.
     */
//数据数组，分隔标识
    public static final String ROLE_USER_PRIVILEGE_SEPARATOR = "|";
    /**
     * The constant DATE_FORMAT.
     */
//日期的格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * The constant DATETYPE_TIME_FORMAT.
     */
    public static final String DATETYPE_TIME_FORMAT = "HH:mm";
    /**
     * The constant DATE_TIME_FORMAT.
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * The constant DELETE_FLAG_REMOVE.
     */
//逻辑删除标志；字段类型：VARCHAR(20)；值：NOT NULL【以 Remove、Normal】；默认值：Normal
    public static final String DELETE_FLAG_REMOVE = "Remove";
    /**
     * The constant DELETE_FLAG_NORMAL.
     */
    public static final String DELETE_FLAG_NORMAL = "Normal";

    /**
     * 全局返回码说明
     * 每次调用接口时，可能获得正确或错误的返回码，可以根据返回码信息调试接口，排查错误。
     * 全局返回码说明如下：
     */
    public static final Integer RESULTS_CODE_ERROR_SYSTEM = -1;//public static final String RESULTS_ERROR_SYSTEM_MESSAGE = "system_error";
    /**
     * The constant RESULTS_CODE_SUCCESS.
     */
    public static final Integer RESULTS_CODE_SUCCESS = 0; //public static final String RESULTS_SUCCESS_MESSAGE = "success";
    /**
     * The constant RESULTS_CODE_TOKEN_FAILURE.
     */
    public static final Integer RESULTS_CODE_TOKEN_FAILURE = 1;
    /**
     * The constant RESULTS_CODE_ERROR_PARAMETER.
     */
    public static final Integer RESULTS_CODE_ERROR_PARAMETER = 2;//public static final String RESULTS_ERROR_PARAMETER_MESSAGE = "parameter_error";
    /**
     * The constant RESULTS_CODE_ERROR_BUSINESS.
     */
    public static final Integer RESULTS_CODE_ERROR_BUSINESS = 3;//public static final String RESULTS_ERROR_BUSINESS_MESSAGE = "business_error";


    /**
     * department Constants
     */
//部门 状态 ；字段类型：VARCHAR(20)；值：NOT NULL【以 Disable、Enable】；默认值：Enable
    public static final String DEPARTMENT_STATUS_ENABLE = "Enable";
    /**
     * The constant DEPARTMENT_STATUS_DISABLE.
     */
    public static final String DEPARTMENT_STATUS_DISABLE = "Disable";


    /**
     * role Constants
     */
//角色  状态 ；字段类型：VARCHAR(20)；值：NOT NULL【以 Disable、Enable】；默认值：Enable
    public static final String ROLE_STATUS_ENABLE = "Enable";
    /**
     * The constant ROLE_STATUS_DISABLE.
     */
    public static final String ROLE_STATUS_DISABLE = "Disable";


    /**
     * user Constants
     */
//用户类型；字段类型INT(1)；值：NOT NULL【以 Employee、Client】，默认值：无
    public static final String USER_TYPE_EMPLOYEE = "Employee";
    /**
     * The constant USER_TYPE_CLIENT.
     */
    public static final String USER_TYPE_CLIENT = "Client";

    /**
     * The constant USER_GENDER_MALE.
     */
//性别；字段类型：VARCHAR(10)；值：NOT NULL【以 Male、Female】；默认值：无
    public static final String USER_GENDER_MALE = "Male";
    /**
     * The constant USER_GENDER_FEMALE.
     */
    public static final String USER_GENDER_FEMALE = "Female";

    /**
     * The constant USER_STATUS_ENABLE.
     */
//用户 状态 ；字段类型：VARCHAR(20)；值：NOT NULL【以Disable、Enable】；默认值：Enable
    public static final String USER_STATUS_ENABLE = "Enable";
    /**
     * The constant USER_STATUS_DISABLE.
     */
    public static final String USER_STATUS_DISABLE = "Disable";


    /**
     * vehicle Constants
     */
//车辆 状态 ；字段类型：VARCHAR(20)；值：NOT NULL【以Disable、Enable】；默认值：Enable
    public static final String VEHICLE_STATUS_DISABLE = "Disable";
    /**
     * The constant VEHICLE_STATUS_ENABLE.
     */
    public static final String VEHICLE_STATUS_ENABLE = "Enable";

    /**
     * Client ClientBranch Constants
     */
//车辆 状态 ；字段类型：VARCHAR(20)；值：NOT NULL【以 Disable、Enable】；默认值：Enable
    public static final String CLIENT_STATUS_ENABLE = "Enable";
    /**
     * The constant CLIENT_STATUS_DISABLE.
     */
    public static final String CLIENT_STATUS_DISABLE = "Disable";
    /**
     * Keychian Constants
     */
//钥匙串状态 ；字段类型：VARCHAR(10)；值：NOT NULL【以 待领取：Wait，领取钥匙：Claim，归还：Return】；默认值：无
    public static final String KEYCHAIN_STATUS_IDLE = "Idle";//空闲：Idle
    /**
     * The constant KEYCHAIN_STATUS_WAIT.
     */
    public static final String KEYCHAIN_STATUS_WAIT = "Wait";//待领取：Wait
    /**
     * The constant KEYCHAIN_STATUS_CLAIM.
     */
    public static final String KEYCHAIN_STATUS_CLAIM = "Claim";//领取钥匙：Claim
    /**
     * The constant KEYCHAIN_STATUS_RETURN.
     */
    public static final String KEYCHAIN_STATUS_RETURN = "Return";//归还：Return


    /**
     * task taskTransaction Constants
     */
//任务，类型：字段类型：VARCHAR(20)；值：NOT NULL【以 counter[柜台]、ATM】；默认值：无
    public static final String TASK_TYPE_COUNTER = "Counter";
    /**
     * The constant TASK_TYPE_ATM.
     */
    public static final String TASK_TYPE_ATM = "ATM";

    /**
     * The constant TASK_TYPE_RECEIVE_MONEY.
     */
    public static final String TASK_TYPE_RECEIVE_MONEY = "Receive_Money";//收钱
    /**
     * The constant TASK_TYPE_GIVE_MONEY.
     */
    public static final String TASK_TYPE_GIVE_MONEY = "Give_Money";//送钱
    /**
     * The constant TASK_TYPE_MAINTAIN_ATM.
     */
    public static final String TASK_TYPE_MAINTAIN_ATM = "Maintain_ATM";//ATM维护

    /**
     * The constant TASK_CREATE_TYPE_SYSTEM_MARK.
     */
//'任务，创建方式；类型：INT(1)；值：必填【0代表为系统自动生成的任务【System】；1代表人工建立【Artificial】；默认值：0',
    public static final String TASK_CREATE_TYPE_SYSTEM_MARK = "System";
    /**
     * The constant TASK_CREATE_TYPE_SYSTEM_CODE.
     */
    public static final int TASK_CREATE_TYPE_SYSTEM_CODE = 0;
    /**
     * The constant TASK_CREATE_TYPE_MANUAL.
     */
    public static final String TASK_CREATE_TYPE_MANUAL = "Manual";

    /**
     * The constant TASK_STATUS_WAIT.
     */
//任务状态，操作的任务类型；字段类型：VARCHAR(20)；值：NOT NULL【等待分配 Wait、分配中 Assignment[Keychain|Driver|Vehicle|Bodyguard]、收到 Receive、入库 Warehousing，结算中 Settlement、Fail、Success】，默认值：无
    public static final String TASK_STATUS_WAIT = "Wait";               //等待分配
    /**
     * The constant TASK_STATUS_ASSIGNMENT.
     */
    public static final String TASK_STATUS_ASSIGNMENT = "Assignment";   //分配中..
    /**
     * The constant TASK_STATUS_ASSIGNMENT_KEYCHAIN.
     */
    public static final String TASK_STATUS_ASSIGNMENT_KEYCHAIN = "Assignment_Keychain";   //分配中..
    /**
     * The constant TASK_STATUS_KEYCHAIN_RETURN.
     */
    public static final String TASK_STATUS_KEYCHAIN_RETURN = "Return_Keychain";   //分配中..

    /**
     * The constant TASK_STATUS_RECEIVE.
     */
    public static final String TASK_STATUS_RECEIVE = "Receive";         //已收钱
    /**
     * The constant TASK_STATUS_WAREHOUSING.
     */
    public static final String TASK_STATUS_WAREHOUSING = "Warehousing"; //入库
    /**
     * The constant TASK_STATUS_SETTLEMENT.
     */
    public static final String TASK_STATUS_SETTLEMENT = "Settlement";
    /**
     * The constant TASK_STATUS_TYPE_FAIL.
     */
    public static final String TASK_STATUS_TYPE_FAIL = "Fail";          //失败

    /**
     * The constant TASK_UPLOAD_IMAGES_SIGNATURE.
     */
    public static final String TASK_UPLOAD_IMAGES_SIGNATURE = "task_upload_images_signature";//签名图片，标识
    /**
     * The constant TASK_UPLOAD_IMAGES_PHOTOGRAPH.
     */
    public static final String TASK_UPLOAD_IMAGES_PHOTOGRAPH = "task_upload_images_photograph";//拍照图片，标识
    /**
     * task monitor Constants
     */
    public static final String COORDINATES_DRIVER_STATUS_ENABLE = "Enable";
    /**
     * The constant COORDINATES_DRIVER_STATUS_DISABLE.
     */
    public static final String COORDINATES_DRIVER_STATUS_DISABLE = "Disable";

    /**
     * Redis 各缓存的开关标识：
     */
    public static final String REDIS_ROLE_MARK = "roleList";
    /**
     * The constant REDIS_DEPARTMENT_MARK.
     */
    public static final String REDIS_DEPARTMENT_MARK = "departmentList";
    /**
     * The constant REDIS_SESSION_MARK.
     */
    public static final String REDIS_SESSION_MARK = "session_";       //session＋token
    /**
     * The constant REDIS_SESSION_EXPIRES_IN.
     */
    public static final int REDIS_SESSION_EXPIRES_IN = 1800;
    /**
     * The constant REDIS_SESSION_PRIVILEGES.
     */
    public static final String REDIS_SESSION_PRIVILEGES = "user_privileges";
    /**
     * The constant REDIS_SESSION_USERINFO.
     */
    public static final String REDIS_SESSION_USERINFO = "user_info";

    /**
     * The constant REDIS_VEHICLE_COORDINATES_MARK.
     */
    public static final String REDIS_VEHICLE_COORDINATES_MARK = "vehicle_";
    /**
     * The constant REDIS_VEHICLE_COORDINATES_CURRENT.
     */
    public static final String REDIS_VEHICLE_COORDINATES_CURRENT = "current_Coordinates";
    /**
     * The constant REDIS_VEHICLE_COORDINATES_EXPIRES_IN.
     */
    public static final int REDIS_VEHICLE_COORDINATES_EXPIRES_IN = 86400;

    /**
     * The constant REDIS_DRIVER_ALL_CURRENT_COORDINATES.
     */
    public static final String REDIS_DRIVER_ALL_CURRENT_COORDINATES = "driver_All_Current_Coordinates";

    /**
     * audit日志
     */
    public static final String AUDIT_LOG_REQUEST_ID = "req_id";
    /**
     * The constant AUDIT_LOG_LOCAL_IP.
     */
    public static final String AUDIT_LOG_LOCAL_IP = "local_ip";
    /**
     * The constant AUDIT_LOG_REMOTE_IP.
     */
    public static final String AUDIT_LOG_REMOTE_IP = "remote_ip";
    /**
     * The constant AUDIT_LOG_TIMESTAMP.
     */
    public static final String AUDIT_LOG_TIMESTAMP = "req_start";
    /**
     * The constant AUDIT_LOG_PARAMS.
     */
    public static final String AUDIT_LOG_PARAMS = "req_params";
    /**
     * The constant AUDIT_LOG_REQ_URI.
     */
    public static final String AUDIT_LOG_REQ_URI = "req_uri";
    /**
     * The constant AUDIT_LOG_REQ_METHOD.
     */
    public static final String AUDIT_LOG_REQ_METHOD = "req_method";
    /**
     * The constant AUDIT_LOG_CHECK_AUTH.
     */
    public static final String AUDIT_LOG_CHECK_AUTH = "req_auth";
    /**
     * The constant AUDIT_LOG_AUTH_REASON.
     */
    public static final String AUDIT_LOG_AUTH_REASON = "req_auth_reason";
}
