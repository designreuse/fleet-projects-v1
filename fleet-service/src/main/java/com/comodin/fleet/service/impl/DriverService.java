package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.service.*;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.RedisUtil;
import com.comodin.fleet.vo.KeychainBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DriverService extends BaseService<UserBean> implements IDriverService {

    private static final Logger log = Logger.getLogger(DriverService.class);

    @Autowired
    private ITaskService taskService;
    @Autowired
    private ITaskTransactionService taskTransactionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IKeychainService keychainService;
    @Autowired
    private ILatLngDriverService latLngDriverService;

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
    @Override
    public List<TaskBean> getListTaskByDriverId(Integer driverId, TaskBeanVo vo) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters vo JSON: " + JSON.toJSONString(vo));

        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (vo == null || vo.getStart() == null || vo.getLength() == null) {
            log.info("parameter error, check paging start length");
            throw new ParameterException("parameter error, check paging start length");
        }

        vo.setDriverId(driverId);

        return taskService.getListByVo(vo);
    }

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
    @Override
    public TaskBean getTaskDetailsByTaskId(Integer driverId, Integer taskId) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters taskId: " + taskId);

        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (taskId == null) {
            log.error("parameter error, taskId is null.");
            throw new ParameterException("taskId is null");
        }

        //1、根据 taskId 调用 Task Service，判断是否有该任务ID
        TaskBean taskBean = taskService.selectByPrimaryKey(taskId);
        //1.1、若不存在，抛出 业务逻辑异常，代表传递的任务不存在。
        if (taskBean == null) {
            log.error("Current Task does not exist.");
            throw new BusinessLogicException("Current Task does not exist.");
        }

        //2、判断：参数中driverId 是否与，task对象中.driverId，是否一至；
        if (taskBean.getDriverId() == null) {
            log.error("Current Task unassigned driver");
            throw new BusinessLogicException("Current Task unassigned driver");
        }
        //2.1、若不一致，抛出 业务逻辑异常，该任务不是当前司机的任务
        if (driverId.intValue() != taskBean.getDriverId().intValue()) {
            log.error("The current drivers: " + driverId + " do not have this task: " + taskBean.getDriverId());
            throw new BusinessLogicException("The current drivers do not have this task");
        }

        return taskBean;
    }

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
    @Override
    public void updateTaskStatusCommentByTaskId(Integer driverId, Integer operateTaskId, String operateTaskStatus, String operateTaskComment) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters operateTaskId: " + operateTaskId);
        log.info("service parameters operateTaskStatus: " + operateTaskStatus);
        log.info("service parameters operateTaskComment: " + operateTaskComment);


        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (operateTaskId == null) {
            log.error("parameter error, operateTaskId is null.");
            throw new ParameterException("operateTaskId is null");
        }
        if (StringUtils.isEmpty(operateTaskStatus)) {
            log.error("parameter error, operateTaskStatus is null.");
            throw new ParameterException("operateTaskStatus is null");
        }

        //参数合法性检查：
        //1、operateStatus：只能是【Receive、Warehousing、Fail】,若不是抛出异常：参数异常
        List<String> driverOperateTaskStatus = new ArrayList<>();
        driverOperateTaskStatus.add(ConstantsFinalValue.TASK_STATUS_RECEIVE);
        driverOperateTaskStatus.add(ConstantsFinalValue.TASK_STATUS_WAREHOUSING);
        driverOperateTaskStatus.add(ConstantsFinalValue.TASK_STATUS_TYPE_FAIL);
        if (!driverOperateTaskStatus.contains(operateTaskStatus)) {
            log.error("The driver only operation task status: [Receive|Warehousing|Fail]  The current state of the transfer: " + operateTaskStatus);
            throw new ParameterException("The driver only operation task status: [Receive|Warehousing|Fail]  The current state of the transfer: " + operateTaskStatus);
        }


        //1、查询出，当前司机 User对象；判断是否正常使用状态。
        //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
        //1.2、Userc对象不为空，进行2
        UserBean driverUserBean = userService.getUserByPrimaryKeyStatusActivated(driverId);
        if (driverUserBean == null) {
            log.error("The current driver is abnormal, please contact customer service");
            throw new BusinessLogicException("The current driver is abnormal, please contact customer service");
        }

        //2、根据要操作的 Task Id 查询出，当前操作的 Task对象
        //2.1、若Task对象不存在，抛出 业务逻辑异常：当前TTask不存在
        //2.2、若不为空，进行3
        TaskBean taskBean = taskService.selectByPrimaryKey(operateTaskId);
        if (taskBean == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(taskBean.getDeleteFlag())) {
            log.error("Current Task does not exist.");
            throw new BusinessLogicException("Current Task does not exist.");
        }

        //3、判断当前 Task对象，是否是当前司机【taskBean.driverId】；
        //3.1、若Task对象中的 taskBean.driverId 与当前传递参数中的 param.driverId，不一至，抛出异常：业务逻辑异常【当前 司机没有该 taskBean】
        //3.2、若相等，进行4
        if (taskBean.getDriverId() == null) {
            log.error("Current Task unassigned drivers");
            throw new BusinessLogicException("Current Task unassigned drivers");
        }
        if (driverId.intValue() != taskBean.getDriverId().intValue()) {
            log.error("The current drivers: " + driverId + " do not have this task: " + taskBean.getDriverId());
            throw new BusinessLogicException("The current drivers do not have this task");
        }


        //4、检查当前操作状态
        //  【流程分析：入库Warehousing，必需先是收到Receive】，其它暂时无法确定，以后再需求再加，20160520
        //4.1、若与流程逻辑不符，抛出异常：业务逻辑异常【】
        //4.2、执行5
        if (ConstantsFinalValue.TASK_STATUS_WAREHOUSING.equals(operateTaskStatus)) {
            if (!ConstantsFinalValue.TASK_STATUS_RECEIVE.equals(taskBean.getStatus())) {
                String info = "Task status of the operations: " + ConstantsFinalValue.TASK_STATUS_WAREHOUSING + " must first " + ConstantsFinalValue.TASK_STATUS_RECEIVE;
                log.error(info);
                throw new ParameterException(info);
            }
        }

        //5、增加 TaskTrancation记录 和 更新 Task状态，两个操作同步完成。
        //5.1：组装 插入的数据
        Integer currentOperatorId = driverUserBean.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
        Integer taskOwnevId = driverUserBean.getId();
        String taskOwnevName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
//        //5.2、增加 TaskTrancation记录
//        taskTransactionService.addTaskTransaction(currentOperatorId, currentOperatorName, operateTaskId, operateTaskStatus, operateTaskComment, taskOwnevId, taskOwnevName);
        //5.3、更新当前操作的 TaskId 更新状态
        taskService.updateTaskStatus(currentOperatorId, currentOperatorName, operateTaskId, operateTaskStatus, operateTaskComment, taskOwnevId, taskOwnevName);
    }

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
    @Override
    public void uploadTaskImageByTaskId(Integer driverId, Integer operateTaskId, String filePath) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters operateTaskId: " + operateTaskId);
        log.info("service parameters operateTaskComment: " + filePath);


        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (operateTaskId == null) {
            log.error("parameter error, operateTaskId is null.");
            throw new ParameterException("operateTaskId is null");
        }
        if (StringUtils.isEmpty(filePath)) {
            log.error("parameter error, filePath is null.");
            throw new ParameterException("filePath is null");
        }

        //1、查询出，当前司机 User对象；判断是否正常使用状态。
        //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
        //1.2、Userc对象不为空，进行2
        UserBean driverUserBean = userService.getUserByPrimaryKeyStatusActivated(driverId);
        if (driverUserBean == null) {
            log.error("The current driver is abnormal, please contact customer service");
            throw new BusinessLogicException("The current driver is abnormal, please contact customer service");
        }

        //2、根据要操作的 Task Id 查询出，当前操作的 Task对象
        //2.1、若Task对象不存在，抛出 业务逻辑异常：当前TTask不存在
        //2.2、若不为空，进行3
        TaskBean taskBean = taskService.selectByPrimaryKey(operateTaskId);
        if (taskBean == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(taskBean.getDeleteFlag())) {
            log.error("Current Task does not exist.");
            throw new BusinessLogicException("Current Task does not exist.");
        }

        //3、判断当前 Task对象，是否是当前司机【taskBean.driverId】；
        //3.1、若Task对象中的 taskBean.driverId 与当前传递参数中的 param.driverId，不一至，抛出异常：业务逻辑异常【当前 司机没有该 taskBean】
        //3.2、若相等，进行4
        if (taskBean.getDriverId() == null) {
            log.error("Current Task unassigned drivers");
            throw new BusinessLogicException("Current Task unassigned drivers");
        }
        if (driverId.intValue() != taskBean.getDriverId().intValue()) {
            log.error("The current drivers: " + driverId + " do not have this task: " + taskBean.getDriverId());
            throw new BusinessLogicException("The current drivers do not have this task");
        }

        //4、增加 TaskTrancation记录。
        //4.1：组装 插入的数据
        Integer currentOperatorId = driverUserBean.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
        String operateTaskStatus = ConstantsFinalValue.TASK_STATUS_RECEIVE;
        Integer taskOwnevId = driverUserBean.getId();
        String taskOwnevName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
        //4.2、增加 TaskTrancation记录
        taskTransactionService.addTaskTransaction(currentOperatorId, currentOperatorName, operateTaskId, operateTaskStatus, filePath, taskOwnevId, taskOwnevName);
    }

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
    @Override
    public List<KeychainBean> getListKeychainByDriverId(Integer driverId, KeychainBeanVo vo) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters vo JSON: " + JSON.toJSONString(vo));

        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (vo == null || vo.getStart() == null || vo.getLength() == null) {
            log.info("parameter error, check paging start length");
            throw new ParameterException("parameter error, check paging start length");
        }

        //1、查询出，当前司机 User对象；判断是否正常使用状态。
        //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
        //1.2、Userc对象不为空，进行2
        UserBean driverUserBean = userService.getUserByPrimaryKeyStatusActivated(driverId);
        if (driverUserBean == null) {
            log.error("The current driver is abnormal, please contact customer service");
            throw new BusinessLogicException("The current driver is abnormal, please contact customer service");
        }

        vo.setAssignDriverId(driverId);

        return keychainService.getListByVo(vo);
    }

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
    @Override
    public void updateClaimKeychainByKeychainId(Integer driverId, Integer keychainId) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters keychainId: " + keychainId);

        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (keychainId == null) {
            log.error("parameter error, keychainId is null.");
            throw new ParameterException("keychainId is null.");
        }

        //1、查询出，当前司机 User对象；判断是否正常使用状态。
        //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
        //1.2、Userc对象不为空，进行2
        UserBean driverUserBean = userService.getUserByPrimaryKeyStatusActivated(driverId);
        if (driverUserBean == null) {
            log.error("The current driver is abnormal, please contact customer service");
            throw new BusinessLogicException("The current driver is abnormal, please contact customer service");
        }

        //2、根据 要领取的，钥匙串ID，查询出 KeychainBean，对应的数据
        //2.1、若KeychainBean对象，为空；即抛出，业务逻辑异常，提示【没有该钥匙串，请联系客户】
        //2.2、若KeychainBean对象，不为空；进行3
        KeychainBean keychainBean = keychainService.selectByPrimaryKey(keychainId);
        if (keychainBean == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(keychainBean.getDeleteFlag())) {
            log.error("Without this keychain, please contact Customer");
            throw new BusinessLogicException("Without this keychain, please contact Customer");
        }

        //3.判断当前 销量串，是否分配给了当前，登陆的司机；通过 driverId = KeychainBean.driverId
        //3.1、若不相等，即抛出，业务逻辑异常，提示【当前钥匙串，未分配给该司机】
        //3.2、若相等，进行4
        if (keychainBean.getAssignDriverId() == null || driverId.intValue() != keychainBean.getAssignDriverId().intValue()) {
            log.error("Current keychain, not assigned to the driver");
            throw new BusinessLogicException("Current keychain, not assigned to the driver");
        }

        //4、判断当前，钥匙串，状态是否已经被领取了；KeychainBean.status == Claim
        //4.1、若相等，即抛出，业务逻辑异常，提示【当前钥匙串，已被领取】
        //4.2、若不相等，进行5
        if (ConstantsFinalValue.KEYCHAIN_STATUS_CLAIM.equals(keychainBean.getStatus())) {
            log.error("Current keychain has been received");
            throw new BusinessLogicException("Current keychain has been received");
        }

        //5、更新钥匙串状态为，Claim；并在，任务记录表中，新增加一条记录
        //5.1、组装 钥匙串表   数据，并调用 IKeychainService.方法进行更新
        //5.2、组装 任务记录表 数据，并调用 ITaskTransaction.方法，进行增加操作。
        Integer currentOperatorId = driverUserBean.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
        Integer taskId = 0;
        String operateType = ConstantsFinalValue.TASK_STATUS_ASSIGNMENT_KEYCHAIN;
        String operateComment = String.valueOf(keychainBean.getId());
        Integer taskOwnevId = driverUserBean.getId();
        String taskOwnevName = currentOperatorName;
        taskTransactionService.addTaskTransaction(currentOperatorId, currentOperatorName, taskId, operateType, operateComment, taskOwnevId, taskOwnevName);

        KeychainBean entityKeychainBean = new KeychainBean();
        entityKeychainBean.setId(keychainId);
        entityKeychainBean.setAssignDriverId(currentOperatorId);
        entityKeychainBean.setAssignDriverName(currentOperatorName);
        entityKeychainBean.setStatus(ConstantsFinalValue.KEYCHAIN_STATUS_CLAIM);
        keychainService.updateKeychainStatusByPrimaryKey(entityKeychainBean);
    }

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
    @Override
    public void updateReturnKeychainByKeychainId(Integer driverId, Integer keychainId) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters keychainId: " + keychainId);

        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (keychainId == null) {
            log.error("parameter error, keychainId is null.");
            throw new ParameterException("keychainId is null.");
        }

        //1、查询出，当前司机 User对象；判断是否正常使用状态。
        //1.1、User对象为空，即代表用户当前用户不存在，抛出，业务逻辑异常：当前司机登陆异常，请联系客户
        //1.2、Userc对象不为空，进行2
        UserBean driverUserBean = userService.getUserByPrimaryKeyStatusActivated(driverId);
        if (driverUserBean == null) {
            log.error("The current driver is abnormal, please contact customer service");
            throw new BusinessLogicException("The current driver is abnormal, please contact customer service");
        }

        //2、根据 要领取的，钥匙串ID，查询出 KeychainBean，对应的数据
        //2.1、若KeychainBean对象，为空；即抛出，业务逻辑异常，提示【没有该钥匙串，请联系客户】
        //2.2、若KeychainBean对象，不为空；进行3
        KeychainBean keychainBean = keychainService.selectByPrimaryKey(keychainId);
        if (keychainBean == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(keychainBean.getDeleteFlag())) {
            log.error("Without this keychain, please contact Customer");
            throw new BusinessLogicException("Without this keychain, please contact Customer");
        }

        //3.判断当前 销量串，是否分配给了当前，登陆的司机；通过 driverId = KeychainBean.driverId
        //3.1、若不相等，即抛出，业务逻辑异常，提示【当前钥匙串，未分配给当前司机】
        //3.2、若相等，进行4
        if (keychainBean.getAssignDriverId() == null || driverId.intValue() != keychainBean.getAssignDriverId().intValue()) {
            log.error("Current keychain, not assigned to the driver");
            throw new BusinessLogicException("Current keychain, not assigned to the driver");
        }

        //4.判断当前 销量串，状态是为领取状态，KeychainBean.status=Claim
        //4.1、若不相等，即抛出，业务逻辑异常，提示【当前钥匙串，还未领取，请先领取钥匙后，才能归还】
        //4.2、若相等，进行5
        if (!ConstantsFinalValue.KEYCHAIN_STATUS_CLAIM.equals(keychainBean.getStatus())) {
            log.error("Current keychain, yet receive, collect keys after the first, in order to return");
            throw new BusinessLogicException("Current keychain, yet receive, collect keys after the first, in order to return");
        }

        //5、更新钥匙状态为：Idle空闲状态，并清空，司机ID和司机Name为空; 在 记录表中，增加一条记录表
        //5.1、组装 钥匙串表   数据，并调用 IKeychainService.方法进行归还
        //5.2、组装 任务记录表 数据，并调用 ITaskTransaction.方法，进行增加操作。
        Integer currentOperatorId = driverUserBean.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
        Integer taskId = 0;
        String operateType = ConstantsFinalValue.KEYCHAIN_STATUS_RETURN;
        String operateComment = String.valueOf(keychainBean.getId());
        Integer taskOwnevId = driverUserBean.getId();
        String taskOwnevName = currentOperatorName;
        taskTransactionService.addTaskTransaction(currentOperatorId, currentOperatorName, taskId, operateType, operateComment, taskOwnevId, taskOwnevName);

        //keychainBean.setAssignDriverId(null);
        //keychainBean.setAssignDriverName("");
        keychainBean.setStatus(ConstantsFinalValue.KEYCHAIN_STATUS_RETURN);
        keychainService.updateAll(keychainBean);
    }

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
     * @param phoneLocalIp   当前司机，手机设备的IP地址
     * @param latLngDataJson 上传的，经纬度，JSON列表
     * @throws ParameterException
     * @throws BusinessLogicException 此方法，已经过时；
     */
    @Deprecated
    @Override
    public void uploadPhoneLatLngData(Integer driverId, String driverUsername, String driverName, String phoneLocalIp, String latLngDataJson) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverId: " + driverId);
        log.info("service parameters driverUsername: " + driverUsername);
        log.info("service parameters driverName: " + driverName);
        log.info("service parameters phoneLocalIp: " + phoneLocalIp);
        //log.info("service parameters latLngDataJson: " + latLngDataJson);


        //检验，参数是否有效。
        if (driverId == null) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (StringUtils.isEmpty(driverUsername)) {
            log.error("parameter error, driverUsername is null.");
            throw new ParameterException("driverUsername is null.");
        }
        if (StringUtils.isEmpty(driverName)) {
            log.error("parameter error, driverName is null.");
            throw new ParameterException("driverName is null.");
        }
        if (StringUtils.isEmpty(phoneLocalIp)) {
            log.error("parameter error, phoneLocalIp is null.");
            throw new ParameterException("phoneLocalIp is null.");
        }
        if (StringUtils.isEmpty(latLngDataJson)) {
            log.error("parameter error, latLngDataJson is null.");
            throw new ParameterException("latLngDataJson is null.");
        }

        //1、检查当前 jsonData数据格式是否正确
        List<LatLngDriverBean> coordinatesDriverBeanList = JSON.parseArray(latLngDataJson, LatLngDriverBean.class);
        if (coordinatesDriverBeanList.isEmpty()) {
            log.error("Current upload latitude and longitude data is invalid");
            throw new ParameterException("Current upload latitude and longitude data is invalid");
        }

        // 按司机手机，获取的经纬度时间戳进行排序
        Collections.sort(coordinatesDriverBeanList, new Comparator<LatLngDriverBean>() {
            @SuppressWarnings("Duplicates")
            public int compare(LatLngDriverBean arg0, LatLngDriverBean arg1) {
                long time0 = arg0.getPhoneUploadTimestamp().getTime();
                long time1 = arg1.getPhoneUploadTimestamp().getTime();
                if (time0 > time1) {
                    return 1;
                } else if (time0 == time1) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        for (LatLngDriverBean coordinatesDriverBean : coordinatesDriverBeanList) {
            if (StringUtils.isEmpty(coordinatesDriverBean.getPhoneLng())) {
                log.error("parameter error, latLngDataJson.phoneLng  is null.");
                throw new ParameterException("parameter error, latLngDataJson.phoneLat  is null.");
            }
            if (StringUtils.isEmpty(coordinatesDriverBean.getPhoneLat())) {
                log.error("parameter error, latLngDataJson.phoneLat  is null.");
                throw new ParameterException("parameter error, latLngDataJson.phoneLat  is null.");
            }
            if (coordinatesDriverBean.getPhoneUploadTimestamp() == null) {
                log.error("parameter error, latLngDataJson.getDateTime  is null.");
                throw new ParameterException("parameter error, latLngDataJson.getDateTime  is null.");
            }

//            long currentServerTime = new Date().getTime();
//            if (coordinatesDriverBean.getGetTimeStamp().getTime() > currentServerTime) {
//                log.error("parameter error, latLngDataJson.getDateTime  Greater than the current server time.");
//                throw new ParameterException("parameter error, latLngDataJson.getDateTime  Greater than the current server time.");
//            }
        }

        Date currentServerDateTime = new Date();
        //3、使用 MQ 进行数据写入 缓存Redis 和 NoSQL Hbase
        for (LatLngDriverBean coordinatesDriverBean : coordinatesDriverBeanList) {
            coordinatesDriverBean.setDriverUsername(driverUsername);
            coordinatesDriverBean.setDriverName(driverName);
            coordinatesDriverBean.setPhoneLocalIp(phoneLocalIp);
            coordinatesDriverBean.setStatus(ConstantsFinalValue.COORDINATES_DRIVER_STATUS_ENABLE);
            coordinatesDriverBean.setCreateDateTime(currentServerDateTime);
            coordinatesDriverBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);

            latLngDriverService.saveSelective(coordinatesDriverBean);
        }

        LatLngDriverBean coordinatesDriverBean = coordinatesDriverBeanList.get(coordinatesDriverBeanList.size() - 1);
        RedisUtil.hset(ConstantsFinalValue.REDIS_DRIVER_ALL_CURRENT_COORDINATES, driverUsername, coordinatesDriverBean, ConstantsFinalValue.REDIS_VEHICLE_COORDINATES_EXPIRES_IN);
    }

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
     * @param phoneLocalIp   当前司机，手机设备的IP地址
     * @param latLngDataJson 上传的，经纬度，JSON列表
     * @throws ParameterException
     * @throws BusinessLogicException
     */
    @SuppressWarnings("Duplicates")
    @Override
    public Map<String, Object> uploadPhoneLatLngData(String driverUsername, String phoneImei, String phoneLocalIp, String latLngDataJson) throws ParameterException, BusinessLogicException {
        log.info("service parameters driverUsername: " + driverUsername);
        log.info("service parameters phoneImei: " + phoneImei);
        log.info("service parameters phoneLocalIp: " + phoneLocalIp);
        //log.info("service parameters latLngDataJson: " + latLngDataJson);

        //检验，参数是否有效。
        if (StringUtils.isEmpty(driverUsername)) {
            log.error("parameter error, driverUsername is null.");
            throw new ParameterException("driverUsername is null.");
        }
        if (StringUtils.isEmpty(phoneImei)) {
            log.error("parameter error, phoneImei is null.");
            throw new ParameterException("phoneImei is null.");
        }
        if (StringUtils.isEmpty(phoneLocalIp)) {
            log.error("parameter error, phoneLocalIp is null.");
            throw new ParameterException("phoneLocalIp is null.");
        }
        if (StringUtils.isEmpty(latLngDataJson)) {
            log.error("parameter error, latLngDataJson is null.");
            throw new ParameterException("latLngDataJson is null.");
        }

        //1、检查当前 latLngDataJson
        List<LatLngDriverBean> latLngDriverBeanList = JSON.parseArray(latLngDataJson, LatLngDriverBean.class);
        if (latLngDriverBeanList.isEmpty()) {
            log.error("Current upload latitude and longitude data is invalid");
            throw new ParameterException("Current upload latitude and longitude data is invalid");
        }

        //2、检查当前，调用端上传记录数，不能大于100条;
        int maxUploadNumber = 100;
        int currentUploadNumber = latLngDriverBeanList.size();
        if (currentUploadNumber > maxUploadNumber) {
            log.error("Current upload latitude and longitude data More than " + maxUploadNumber + " currentUploadNumber " + currentUploadNumber);
            throw new BusinessLogicException("Current upload latitude and longitude data More than " + maxUploadNumber + " currentUploadNumber " + currentUploadNumber);
        }

        // 按司机手机，获取的经纬度时间戳进行排序
        Collections.sort(latLngDriverBeanList, (arg0, arg1) -> {
            long time0 = arg0.getPhoneUploadTimestamp().getTime();
            long time1 = arg1.getPhoneUploadTimestamp().getTime();
            if (time0 > time1) {
                return 1;
            } else if (time0 == time1) {
                return 0;
            } else {
                return -1;
            }
        });

        //3、检查，每一条数据，各字段的数据的合法性
        for (LatLngDriverBean latLngDriverBean : latLngDriverBeanList) {

            if (StringUtils.isEmpty(latLngDriverBean.getPhoneLat())) {
                log.error("parameter error, latLngDataJson.phoneLng  is null.");
                throw new ParameterException("parameter error, latLngDataJson.phoneLng  is null.");
            }
            if (StringUtils.isEmpty(latLngDriverBean.getPhoneLng())) {
                log.error("parameter error, latLngDataJson.phoneLng  is null.");
                throw new ParameterException("parameter error, latLngDataJson.phoneLng  is null.");
            }
            if (latLngDriverBean.getPhoneUploadTimestamp() == null) {
                log.error("parameter error, latLngDataJson.phoneUploadTimestamp  is null.");
                throw new ParameterException("parameter error, latLngDataJson.phoneUploadTimestamp  is null.");
            }

            //针对，手机app 每次获取经纬度的时间，与当前服务器时间进行比较，不能大于 当前服务器时间，允许时间跨度为：60秒；
            Date currentServerDateTime = new Date();
            long currentServerTime = new Date().getTime();
            long phoneUploadTimestamp = latLngDriverBean.getPhoneUploadTimestamp().getTime();
            if (phoneUploadTimestamp > currentServerTime + 60000) {
                log.error("parameter error, latLngDataJson.phoneUploadTimestamp  Greater than the current server time.");
                throw new ParameterException("parameter error, latLngDataJson.phoneUploadTimestamp  Greater than the current server time.");
            }

            latLngDriverBean.setDriverUsername(driverUsername);
            latLngDriverBean.setPhoneImei(phoneImei);
            latLngDriverBean.setPhoneLocalIp(phoneLocalIp);
            latLngDriverBean.setStatus(ConstantsFinalValue.COORDINATES_DRIVER_STATUS_ENABLE);
            latLngDriverBean.setCreateDateTime(currentServerDateTime);
            latLngDriverBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        }

        //4、将最后一条 经纬度数据，写入到 Redis缓存中，以便后台监控，能及时显示到 googleMap上
        LatLngDriverBean lastLatLngDriverBean = latLngDriverBeanList.get(latLngDriverBeanList.size() - 1);
        RedisUtil.hset(ConstantsFinalValue.REDIS_DRIVER_ALL_CURRENT_COORDINATES, driverUsername, lastLatLngDriverBean, ConstantsFinalValue.REDIS_VEHICLE_COORDINATES_EXPIRES_IN);
        log.info("The latitude and longitude of the last data written to the cache Redis JSON: " + JSON.toJSONString(lastLatLngDriverBean));

        //5、将经纬度数据，批量插入到 数据库中
        int saveListCount = latLngDriverService.saveList(latLngDriverBeanList);
        log.info("The latitude and longitude data, batch inserted into the database");

        Map<String, Object> result = new HashedMap<>();
        result.put("clientUploadNumber", currentUploadNumber);
        result.put("databaseInsertNumber", saveListCount);

        log.info("Client and inserted into the database, the total number of latitude and longitude" + JSON.toJSON(result));
        return result;
    }

}
