package com.comodin.fleet.service;


import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.vo.KeychainBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;

import java.util.List;
import java.util.Map;

public interface IDriverService {


    /**
     * <pre>
     *     功能：根据 DriverId 查询出 所有与该司机的任务列表【包括，待执行，已失败，已入库的所有任务列表】
     *
     *     业务逻辑：
     *     1、根据司机ID，先查询一下，用户表中是否存在，以及正常使用状态。
     *     2、根据司机ID，查询 task； task.driverId = dirverId
     *     3、调用 任务服务接口，查询出数据
     * </pre>
     *
     * @param driverId 司机ID
     * @param vo       vo.分页页面，vo.start  vo.length
     * @return taskList【task.id task.name task.clientBranchAddress task.status】
     * @throws ParameterException     若参数这为空，抛出异常
     * @throws BusinessLogicException 业务逻辑相关，抛出【司机ID对应的用户，不是正常情况，抛出异常】
     */
    List<TaskBean> getListTaskByDriverId(Integer driverId, TaskBeanVo vo) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *     功能：根据 taskId 查询出该任务的信息记录
     *
     *     业务逻辑：
     *     //1、根据 taskId 调用 Task Service，判断是否有该任务ID
     *     //1.1、若不存在，抛出 业务逻辑异常，代表传递的任务不存在。
     *     //1.2、若存在记录，进行2
     *
     *     //2、判断：参数中driverId 是否与，task对象中.driverId，是否一至；
     *     //2.1、若不一致，抛出 业务逻辑异常，该任务不是当前司机的任务
     *     //2.2、若一致，进行3
     *
     *     //3、将查询出的 task 返回
     * </pre>
     *
     * @param driverId 当前登陆的司机ID
     * @param taskId   司机任务列表中，选择对应的，任务ID，进行查询任务详细
     * @return 返回Task
     * @throws ParameterException     参数若为空，抛出参数异常
     * @throws BusinessLogicException
     */
    TaskBean getTaskDetailsByTaskId(Integer driverId, Integer taskId) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *  功能：当前登陆司机，对自己的任务，进行更改状态。
     *
     *  参数合法性检查：
     *  //1、operateStatus：只能是【Receive、Warehousing、Fail】,若不是抛出异常：参数异常
     *
     *  业务逻辑：
     *  //1、查询出，当前司机 User对象；判断是否正常使用状态。
     *  //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
     *  //1.2、Userc对象不为空，进行2
     *
     *  //2、根据要操作的 Task Id 查询出，当前操作的 Task对象
     *  //2.1、若Task对象不存在，抛出 业务逻辑异常：当前TTask不存在
     *  //2.2、若不为空，进行3
     *
     *  //3、判断当前 Task对象，是否是当前司机【taskBean.driverId】；
     *  //3.1、若Task对象中的 taskBean.driverId 与当前传递参数中的 param.driverId，不一至，抛出异常：业务逻辑异常【当前 司机没有该 taskBean】
     *  //3.2、若相等，进行4
     *
     *  //4、检查当前操作状态
     *  //  【流程分析：入库Warehousing，必需先是收到Receive】，其它暂时无法确定，以后再需求再加，20160520
     *  //4.1、若与流程逻辑不符，抛出异常：业务逻辑异常【】
     *  //4.2、执行5
     *
     *  //5、增加 TaskTrancation记录 和 更新 Task状态，两个操作同步完成。
     *  //5.1：组装 插入的数据
     *  //5.2、增加 TaskTrancation记录
     *  //5.3、更新当前操作的 TaskId 更新状态
     *
     * </pre>
     *
     * @param driverId           当前登陆的司机ID
     * @param operateTaskId      操作的TaskId
     * @param operateTaskStatus  对任务进行状态的改变【状态的改变【Receive、Warehousing、Fail】Fail可以任意环节操作，Warehousing必需在Receive之后才能进行】
     * @param operateTaskComment 对状态的改变进行备注
     * @throws ParameterException     参数为空，抛出异常
     * @throws BusinessLogicException 根据系统逻辑，进行业务抛出
     */
    void updateTaskStatusCommentByTaskId(Integer driverId, Integer operateTaskId, String operateTaskStatus, String operateTaskComment) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *     功能：司机收到钱之后，上传拍照截图
     *
     *  业务逻辑：
     *  //1、查询出，当前司机 User对象；判断是否正常使用状态。
     *  //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
     *  //1.2、Userc对象不为空，进行2
     *
     *  //2、根据要操作的 Task Id 查询出，当前操作的 Task对象
     *  //2.1、若Task对象不存在，抛出 业务逻辑异常：当前TTask不存在
     *  //2.2、若不为空，进行3
     *
     *  //3、判断当前 Task对象，是否是当前司机【taskBean.driverId】；
     *  //3.1、若Task对象中的 taskBean.driverId 与当前传递参数中的 param.driverId，不一至，抛出异常：业务逻辑异常【当前 司机没有该 taskBean】
     *  //3.2、若相等，进行4
     *
     *  //4、增加 TaskTrancation记录。
     *  //4.1：组装 插入的数据
     *  //4.2、增加 TaskTrancation记录
     * </pre>
     *
     * @param driverId      当前登陆的司机ID
     * @param operateTaskId 操作的任务ID
     * @param filePath      上传之后的图片，文件名和路径
     * @throws ParameterException     参数为空，抛出异常
     * @throws BusinessLogicException 根据系统逻辑，进行业务抛出
     */
    void uploadTaskImageByTaskId(Integer driverId, Integer operateTaskId, String filePath) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *     功能：司机每天要获取，客户分支机构对应钥匙
     *     业务逻辑：
     *     //1、查询出，当前司机 User对象；判断是否正常使用状态。
     *     1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
     *     1.2、Userc对象不为空，进行2
     *
     *     2、根据 司机ID，查询 KeychainBean，对应的数据
     * </pre>
     *
     * @param driverId 当前登陆的司机ID
     * @param vo       vo.分页页面，vo.start  vo.length
     * @return 返回对应的 KeychainBean对象
     * @throws ParameterException     参数为空，抛出异常
     * @throws BusinessLogicException 根据系统逻辑，进行业务抛出
     */
    List<KeychainBean> getListKeychainByDriverId(Integer driverId, KeychainBeanVo vo) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *      功能：领取钥匙串，将当前钥匙串表中，钥匙串状态改为：已领取；并在 任务记录表中，新增一条记录
     *     业务逻辑：
     *     //1、查询出，当前司机 User对象；判断是否正常使用状态。
     *     //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
     *     //1.2、Userc对象不为空，进行2
     *
     *     //2、根据 要领取的，钥匙串ID，查询出 KeychainBean，对应的数据
     *     //2.1、若KeychainBean对象，为空；即抛出，业务逻辑异常，提示【没有该钥匙串，请联系客户】
     *     //2.2、若KeychainBean对象，不为空；进行3
     *
     *     //3.判断当前 销量串，是否分配给了当前，登陆的司机；通过 driverId = KeychainBean.driverId
     *     //3.1、若不相等，即抛出，业务逻辑异常，提示【当前钥匙串，未分配给当前司机】
     *     //3.2、若相等，进行4
     *
     *     //4、判断当前，钥匙串，状态是否已经被领取了；KeychainBean.status == Claim
     *     //4.1、若相等，即抛出，业务逻辑异常，提示【当前钥匙串，已被领取】
     *     //4.2、若不相等，进行5
     *
     *     //5、更新钥匙串状态为，Claim；并在，任务记录表中，新增加一条记录
     *     //5.1、组装 钥匙串表   数据，并调用 IKeychainService.方法进行更新
     *     //5.2、组装 任务记录表 数据，并调用 ITaskTransaction.方法，进行增加操作。
     * </pre>
     *
     * @param driverId   当前登陆的司机ID
     * @param keychainId 要领取的，钥匙串ID
     * @throws ParameterException     参数为空，抛出异常
     * @throws BusinessLogicException 根据系统逻辑，进行业务抛出
     */
    void updateClaimKeychainByKeychainId(Integer driverId, Integer keychainId) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *     功能：钥匙串，归还给管理处；钥匙串状态改变：Idle 空闲；并清空，司机ID，司机name字段
     *     业务逻辑：
     *     //1、查询出，当前司机 User对象；判断是否正常使用状态。
     *     //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
     *     //1.2、Userc对象不为空，进行2
     *
     *     //2、根据 要领取的，钥匙串ID，查询出 KeychainBean，对应的数据
     *     //2.1、若KeychainBean对象，为空；即抛出，业务逻辑异常，提示【没有该钥匙串，请联系客户】
     *     //2.2、若KeychainBean对象，不为空；进行3
     *
     *     //3.判断当前 销量串，是否分配给了当前，登陆的司机；通过 driverId = KeychainBean.driverId
     *     //3.1、若不相等，即抛出，业务逻辑异常，提示【当前钥匙串，未分配给当前司机】
     *     //3.2、若相等，进行4
     *
     *     //4.判断当前 销量串，状态是为领取状态，KeychainBean.status=Claim
     *     //4.1、若不相等，即抛出，业务逻辑异常，提示【当前钥匙串，还未领取，请先领取钥匙后，才能归还】
     *     //4.2、若相等，进行5
     *
     *     //5、更新钥匙状态为：Idle空闲状态，并清空，司机ID和司机Name为空; 在 记录表中，增加一条记录表
     *     //5.1、组装 钥匙串表   数据，并调用 IKeychainService.方法进行归还
     *     //5.2、组装 任务记录表 数据，并调用 ITaskTransaction.方法，进行增加操作。
     *
     * </pre>
     *
     * @param driverId   当前登陆的司机ID
     * @param keychainId 要领取的，钥匙串ID
     * @throws ParameterException     参数为空，抛出异常
     * @throws BusinessLogicException 根据系统逻辑，进行业务抛出
     */
    void updateReturnKeychainByKeychainId(Integer driverId, Integer keychainId) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *     功能：处理，当前司机对应的车辆，经经纬度 上传数据
     *     业务逻辑：
     *
     *     //1、检查当前 jsonData数据格式是否正确
     *
     *     //2、使用 MQ 进行数据写入 缓存Redis 和 NoSQL Hbase
     *
     * </pre>
     *
     * @param driverId       当前司机ID
     * @param driverUsername 当前司机用户名
     * @param driverName     当前司机我名【名+姓】
     * @param phoneLocalIp  当前司机，手机设备的IP地址
     * @param latLngDataJson 上传的，经纬度，JSON列表
     * @throws ParameterException
     * @throws BusinessLogicException
     */
    @Deprecated
    void uploadPhoneLatLngData(Integer driverId, String driverUsername, String driverName, String phoneLocalIp, String latLngDataJson) throws ParameterException, BusinessLogicException;

    /**
     * <pre>
     *     功能：处理，当前司机对应的车辆，经经纬度 上传数据
     *     业务逻辑：
     *
     *     //1、检查当前 jsonData数据格式是否正确
     *
     *     //2、使用 MQ 进行数据写入 缓存Redis 和 NoSQL Hbase
     *
     * </pre>
     *
     * @param driverUsername 当前司机用户名
     * @param phoneImei      当前手机设备ID标识 国际移动设备识别码（IMEI：International Mobile Equipment Identification Number）是区别移动设备的标志
     * @param phoneLocalIp  当前司机，手机设备的IP地址
     * @param latLngDataJson 上传的，经纬度，JSON列表
     * @throws ParameterException
     * @throws BusinessLogicException
     */
    Map<String, Object> uploadPhoneLatLngData(String driverUsername, String phoneImei, String phoneLocalIp, String latLngDataJson) throws ParameterException, BusinessLogicException;
}
