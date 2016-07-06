package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.service.IClientBranchService;
import com.comodin.fleet.service.ITaskService;
import com.comodin.fleet.service.ITaskTransactionService;
import com.comodin.fleet.service.IUserService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.date.DateUtil;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class TaskService extends BaseService<TaskBean> implements ITaskService {

    private static final Logger log = Logger.getLogger(TaskService.class);

    @Autowired
    private IClientBranchService clientBranchService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskTransactionService taskTransactionService;

    @Override
    public List<TaskBean> getListByVo(TaskBeanVo vo) {
        log.info("service getListByVo request parameters vo JSON: " + JSON.toJSONString(vo));

        if (vo == null || vo.getStart() == null || vo.getLength() == null) {
            log.info("Query parameter error, check paging start length");
            throw new ParameterException("Query parameter error, check paging start length");
        }

        Example example = new Example(TaskBean.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(vo.getName())) {
            criteria.andLike("name", FleetBasiUtil.likePercent(vo.getName().trim()));
        }
        if (StringUtils.isNotBlank(vo.getType())) {
            criteria.andEqualTo("type", vo.getType().trim());
        }


        if (StringUtils.isNotBlank(vo.getExecutionDateStartDate())) {
            criteria.andGreaterThanOrEqualTo("executionDate", vo.getExecutionDateStartDate().trim());
        }
        if (StringUtils.isNotBlank(vo.getExecutionDateEndDate())) {
            criteria.andLessThanOrEqualTo("executionDate", vo.getExecutionDateEndDate().trim());
        }
        if (StringUtils.isNotBlank(vo.getStatus())) {
            criteria.andEqualTo("status", vo.getStatus().trim());
        }

        if (vo.getClientId() != null) {
            criteria.andEqualTo("clientId", vo.getClientId());
        }
        if (StringUtils.isNotBlank(vo.getClientName())) {
            criteria.andLike("clientName", FleetBasiUtil.likePercent(vo.getClientName().trim()));
        }
        if (StringUtils.isNotBlank(vo.getClientBranchName())) {
            criteria.andLike("clientBranchName", FleetBasiUtil.likePercent(vo.getClientBranchName().trim()));
        }
        if (StringUtils.isNotBlank(vo.getClientBranchContact())) {
            criteria.andLike("clientBranchContact", FleetBasiUtil.likePercent(vo.getClientBranchContact().trim()));
        }
        if (StringUtils.isNotBlank(vo.getClientBranchPhone())) {
            criteria.andLike("clientBranchPhone", FleetBasiUtil.likePercent(vo.getClientBranchPhone().trim()));
        }
        if (StringUtils.isNotBlank(vo.getClientBranchAddress())) {
            criteria.andLike("clientBranchAddress", FleetBasiUtil.likePercent(vo.getClientBranchAddress().trim()));
        }


        if (StringUtils.isNotBlank(vo.getOperatorName())) {
            criteria.andLike("operatorName", FleetBasiUtil.likePercent(vo.getOperatorName().trim()));
        }
        if (StringUtils.isNotBlank(vo.getOwnevName())) {
            criteria.andLike("ownevName", FleetBasiUtil.likePercent(vo.getOwnevName().trim()));
        }

        if (vo.getDriverId() != null) {
            criteria.andEqualTo("driverId", vo.getDriverId());
        }
        if (StringUtils.isNotBlank(vo.getDriverName())) {
            criteria.andLike("driverName", FleetBasiUtil.likePercent(vo.getDriverName().trim()));
        }


        if (StringUtils.isNotBlank(vo.getCreateDataTimeStartTime())) {
            criteria.andGreaterThanOrEqualTo("createDateTime", vo.getCreateDataTimeStartTime().trim());
        }
        if (StringUtils.isNotBlank(vo.getCreateDataTimeEndTime())) {
            criteria.andLessThanOrEqualTo("createDateTime", vo.getCreateDataTimeEndTime().trim());
        }
        criteria.andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL);
        return mapper.selectByExampleAndRowBounds(example, new RowBounds(vo.getStart(), vo.getLength()));
    }


    /**
     * <pre>
     *      增加任务
     *
     * 增加任务唯一性依据：client_id + client_branch_id + task_type + task_execution_date + deleteFlag 查询是否已经存在 任务记录
     *
     * 业务逻辑：
     * //1、根据 currentOperatorId，查询出用户对象
     * //1.1、若 currentOperatorId 对应的User对象不存在，BusinessLogicException，没有当前用户
     *
     * //3、通过 clientBranchId，调用 IClientBranchService，获取当前 clientBranchId 对应的 ClientBranchBean
     * //3.1、检查 ClientBranchBean.clientId == clientId；若为true，即是当前客户对应的分支机构
     *
     * //4.根据 client_id + client_branch_id + task_type + task_execution_date + deleteFlag 查询是否已经存在 任务记录
     * //4.1 若存在，抛出 业务逻辑异常，提示已经增加过任务
     * //4.2 若不存在，即5
     *
     * //5、组装task数据，并调用父类，super.save()方法进行保存
     *
     * //6、往 taskTransaction表中，插入记录信息；
     * <pre>
     * @param currentOperatorId     当前操作人的ID
     * @param currentOperatorName   当前操作人的姓名
     * @param clientId              客户ID，必填
     * @param clientBranchId        客户分支机构ID，必填
     * @param taskExecutionDateTimeStamp  任务执行日期时间戳
     * @param taskType              任务执行日期
     * @return 该任务的对象，里面包含了 任务ID
     * @throws ParameterException     若参数：currentOperatorId，clientId，clientBranchId 为空抛出参数异常
     * @throws BusinessLogicException 若与业务逻辑不符，抛出此异常
     */
    public TaskBean addTask(Integer currentOperatorId, String currentOperatorName, Integer clientId, Integer clientBranchId, String taskExecutionDateTimeStamp, String taskType) throws ParameterException, BusinessLogicException {
        log.info("service request parameters currentOperatorId: " + currentOperatorId + " currentOperatorName: " + currentOperatorName + " clientId: " + clientId + " clientBranchId: " + clientBranchId + " taskExecutionDateTimeStamp: " + taskExecutionDateTimeStamp + " taskType: " + taskType);

        //检验，参数是否有效。
        if (currentOperatorId == null) {
            log.error("request parameter error, operatorId is null.");
            throw new ParameterException("operatorId is null");
        }
        if (StringUtils.isEmpty(currentOperatorName)) {
            log.error("request parameter error, currentOperatorName is null.");
            throw new ParameterException("request parameter error, currentOperatorName is null.");
        }

        if (clientId == null) {
            log.error("request parameter error, clientId is null.");
            throw new ParameterException("request parameter error, clientId is null.");
        }
        if (clientBranchId == null) {
            log.error("request parameter error, clientBranchId is null.");
            throw new ParameterException("request parameter error, clientBranchId is null.");
        }
        if (StringUtils.isEmpty(taskExecutionDateTimeStamp)) {
            log.error("request parameter error, taskExecutionDateTimeStamp is null.");
            throw new ParameterException("request parameter error, taskExecutionDateTimeStamp is null.");
        }
        if (taskExecutionDateTimeStamp.trim().length() != 13) {
            log.error("request parameter error, taskExecutionDateTimeStamp Length is only 13.");
            throw new ParameterException("request parameter error, taskExecutionDateTimeStamp Length is only 13.");
        }

        if (StringUtils.isEmpty(taskType)) {
            log.error("request parameter error, taskType is null.");
            throw new ParameterException("request parameter error, taskType is null.");
        }


        if (currentOperatorId != ConstantsFinalValue.TASK_CREATE_TYPE_SYSTEM_CODE && !currentOperatorName.equals(ConstantsFinalValue.TASK_CREATE_TYPE_SYSTEM_MARK)) {
            //1、根据 currentOperatorId，查询出用户对象
            UserBean userBean = userService.selectByPrimaryKey(currentOperatorId);
            //1.1、若 currentOperatorId 对应的User对象不存在，抛出RuntimeException，没有当前用户
            if (userBean == null) {
                throw new BusinessLogicException("Abnormal current login ID, please contact customer service.");
            }
        }


        ////2、判断当前登陆用户，是客户账号 还是 公司人员；
        ////   通过 userBean.sn 是否为空；为空代表公司人员，若不为空，即代表客户账号登陆，sn的值即为对应的t_client.clientId。
        ////2.1、若为客户账号登陆：先检查当前客户账号中对应的sn【对应哪一个客户的登陆账号】 与 参数clientId匹配；若一致代表是相同进入3；若不一致代表，当前登陆用户账号不是正常操作，抛出业务逻辑异常
        ////2.2、若为公司人员登陆：直接进入3
        //String userBeanSn = userBean.getSn();
        //if (!StringUtils.isBlank(userBeanSn)) {
        //    if (!userBeanSn.equals(clientId)) {//
        //        throw new BusinessLogicException("The current name of the Client, not currently logged in user account !!!");
        //    }
        //}

        //3、通过 clientBranchId，调用 IClientBranchService，获取当前 clientBranchId 对应的 ClientBranchBean
        ClientBranchBean branchBean = clientBranchService.selectByPrimaryKey(clientBranchId);
        //3.1、检查 ClientBranchBean.clientId == clientId；若为true，即是当前客户对应的分支机构
        if (branchBean == null || !clientId.equals(branchBean.getClientId())) {
            throw new BusinessLogicException("当前客户名下，没有当前选择的分支机构信息！！！");
        }


        //4.根据 client_id + client_branch_id + task_type + task_execution_date + deleteFlag 查询是否已经存在 任务记录
        Date taskExecutionDate = DateUtil.timeStampToDate(taskExecutionDateTimeStamp, ConstantsFinalValue.DATE_FORMAT);
        TaskBean taskRecord = new TaskBean();
        taskRecord.setClientId(clientId);
        taskRecord.setClientBranchId(clientBranchId);
        taskRecord.setType(taskType);
        taskRecord.setExecutionDate(taskExecutionDate);
        taskRecord.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        int selectCount = super.selectCount(taskRecord);
        //4.1 若存在，抛出 业务逻辑异常，提示已经增加过任务
        if (selectCount > 0) {
            log.error("The current task has been created client organization exists.");
            throw new BusinessLogicException("The current task has been created client organization exists.");
        }
        //4.2 若不存在，i即5

        //5、组装task数据，并调用父类，super.save()方法进行保存
        Date currentDateTime = new Date();
        TaskBean insertTaskBean = new TaskBean();
        insertTaskBean.setId(null);
        insertTaskBean.setName(branchBean.getClientName() + " " + branchBean.getName());
        insertTaskBean.setType(taskType);
        insertTaskBean.setExecutionDate(taskExecutionDate);
        insertTaskBean.setStatus(ConstantsFinalValue.TASK_STATUS_WAIT);

        insertTaskBean.setClientId(branchBean.getClientId());
        insertTaskBean.setClientName(branchBean.getClientName());

        insertTaskBean.setClientBranchId(branchBean.getId());
        insertTaskBean.setClientBranchName(branchBean.getName());
        insertTaskBean.setClientBranchType(branchBean.getType());
        insertTaskBean.setClientBranchContact(branchBean.getContact());
        insertTaskBean.setClientBranchPhone(branchBean.getPhone());
        insertTaskBean.setClientBranchAddress(branchBean.getAddress());
        insertTaskBean.setClientBranchLatitude(branchBean.getLatitude());
        insertTaskBean.setClientBranchLongitude(branchBean.getLongitude());
        insertTaskBean.setClientBranchWindowStartTime(branchBean.getWindowStartTime());
        insertTaskBean.setClientBranchWindowEndTime(branchBean.getWindowEndTime());
        insertTaskBean.setClientBranchWindowDuration(branchBean.getWindowDuration());

        insertTaskBean.setOperatorId(currentOperatorId);
        insertTaskBean.setOperatorName(currentOperatorName);
        insertTaskBean.setOperateDateTime(currentDateTime);

        insertTaskBean.setOwnevId(null);
        insertTaskBean.setOwnevName(null);
        insertTaskBean.setDriverId(null);
        insertTaskBean.setDriverName(null);

        insertTaskBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        insertTaskBean.setCreateDateTime(currentDateTime);

        super.save(insertTaskBean);

        //6、往 taskTransaction表中，插入记录信息；
        taskTransactionService.addTaskTransaction(currentOperatorId, currentOperatorName, insertTaskBean.getId(), insertTaskBean.getStatus(), null, null, null);
        return insertTaskBean;
    }

    /**
     * <pre>
     * 功能：更新当道 taskId 对应的任务状态
     *
     * //1.先查询当前要操作 TaskId 是否存在；若存在继续， 否则抛出业务逻辑异常，当前任务不存在
     *
     * //2.若当前 task.status 状态与要更新的状态一致；就不进行更新操作，记录一下日记
     *
     * //3.更新 task 状态
     *
     * </pre>
     *
     * @param currentOperatorId   当前操作人的ID
     * @param currentOperatorName 当前操作人的Name
     * @param operateTaskId       操作的TaskId
     * @param operateTaskStatus   操作的状态
     * @param operateTaskComment  更新状态的说明
     * @param taskOwnevId         责任人ID
     * @param taskOwnevName       责任人Name
     * @throws ParameterException     若传递参数为空，抛出异常
     * @throws BusinessLogicException 若与业务逻辑有关，抛出此异常
     */
    @Override
    public void updateTaskStatus(Integer currentOperatorId, String currentOperatorName, Integer operateTaskId, String operateTaskStatus, String operateTaskComment, Integer taskOwnevId, String taskOwnevName) throws ParameterException, BusinessLogicException {
        log.info("service request parameters currentOperatorId: " + currentOperatorId + " currentOperatorName: " + currentOperatorName + " operateTaskId: " + operateTaskId + " operateTaskStatus: " + operateTaskStatus);

        //检验，参数是否有效。
        if (currentOperatorId == null) {
            log.error("request parameter error, operatorId is null.");
            throw new ParameterException("operatorId is null");
        }
        if (StringUtils.isEmpty(currentOperatorName)) {
            log.error("request parameter error, currentOperatorName is null.");
            throw new ParameterException("request parameter error, currentOperatorName is null.");
        }

        if (operateTaskId == null) {
            log.error("request parameter error, taskId is null.");
            throw new ParameterException("taskId is null");
        }
        if (StringUtils.isEmpty(operateTaskStatus)) {
            log.error("request parameter error, taskStatus is null.");
            throw new ParameterException("request parameter error, taskStatus is null.");
        }

        //1.先查询当前要操作 TaskId 是否存在；若存在继续， 否则抛出业务逻辑异常，当前任务不存在
        TaskBean taskBean = super.selectByPrimaryKey(operateTaskId);
        if (taskBean == null || taskBean.getDeleteFlag() == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(taskBean.getDeleteFlag().trim())) {
            log.error("The current task has been created client organization exists.");
            throw new BusinessLogicException("The current task has been created client organization exists.");
        }

        //2.若当前 task.status 状态与要更新的状态一致；就不进行更新操作，记录一下日记
        if (taskBean.getStatus() != null && operateTaskStatus.equals(taskBean.getStatus())) {
            log.error("The current task status is already " + operateTaskStatus);
            return;
        }

        //3.更新 task 状态
        TaskBean entityTaskBean = new TaskBean();
        entityTaskBean.setId(operateTaskId);
        entityTaskBean.setOperatorId(currentOperatorId);
        entityTaskBean.setOperatorName(currentOperatorName);
        entityTaskBean.setOperateDateTime(new Date());
        entityTaskBean.setStatus(operateTaskStatus);
        super.updateNotNull(entityTaskBean);

        if (taskOwnevId == null) {
            taskOwnevId = taskBean.getOwnevId();
            taskOwnevName = taskBean.getOwnevName();
        }
        taskTransactionService.addTaskTransaction(currentOperatorId, currentOperatorName, operateTaskId, operateTaskStatus, operateTaskComment, taskOwnevId, taskOwnevName);
    }

    /**
     * <pre>
     * 功能：更新当道 taskId 对应的 司机信息
     *
     * //1.先查询当前要操作 TaskId 是否存在；若存在继续， 否则抛出业务逻辑异常，当前任务不存在
     *
     * //2.若当前 task.driverId 司机ID与要更新的 司机ID一致；就不进行更新操作，记录一下日记
     *
     * //3.更新 task 司机
     *
     * </pre>
     *
     * @param currentOperatorId   当前操作人的ID
     * @param currentOperatorName 当前操作人的Name
     * @param taskId              要更新的TaskId
     * @param driverId            要更新的 司机Id
     * @param driverName          要更新的 司机Name
     * @throws ParameterException     若传递参数为空，抛出异常
     * @throws BusinessLogicException 若与业务逻辑有关，抛出此异常
     */
    @Override
    public void updateTaskDriver(Integer currentOperatorId, String currentOperatorName, Integer taskId, Integer driverId, String driverName) throws ParameterException, BusinessLogicException {
        log.info("service request parameters currentOperatorId: " + currentOperatorId + " currentOperatorName: " + currentOperatorName + " taskId: " + taskId + " driverId: " + driverId + " driverName: " + driverName);

        //检验，参数是否有效。
        if (currentOperatorId == null) {
            log.error("request parameter error, operatorId is null.");
            throw new ParameterException("operatorId is null");
        }
        if (StringUtils.isEmpty(currentOperatorName)) {
            log.error("request parameter error, currentOperatorName is null.");
            throw new ParameterException("request parameter error, currentOperatorName is null.");
        }

        if (taskId == null) {
            log.error("request parameter error, taskId is null.");
            throw new ParameterException("taskId is null");
        }

        if (driverId == null) {
            log.error("request parameter error, driverId is null.");
            throw new ParameterException("driverId is null");
        }
        if (StringUtils.isEmpty(driverName)) {
            log.error("request parameter error, driverName is null.");
            throw new ParameterException("request parameter error, driverName is null.");
        }

        //1.先查询当前要操作 TaskId 是否存在；若存在继续， 否则抛出业务逻辑异常，当前任务不存在
        TaskBean taskBean = super.selectByPrimaryKey(taskId);
        if (taskBean == null || taskBean.getDeleteFlag() == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(taskBean.getDeleteFlag().trim())) {
            log.error("The current task has been created client organization exists.");
            throw new BusinessLogicException("The current task has been created client organization exists.");
        }

        //2.若当前 task.driverId 司机ID与要更新的 司机ID一致；就不进行更新操作，记录一下日记
        if (taskBean.getDriverId() != null && driverId.intValue() == taskBean.getDriverId().intValue()) {
            log.error("The current task driver is already driverId: " + driverId + " driverName： " + driverName);
            return;
        }

        //3.更新 task 司机
        TaskBean entityTaskBean = new TaskBean();
        entityTaskBean.setId(taskId);
        entityTaskBean.setOperatorId(currentOperatorId);
        entityTaskBean.setOperatorName(currentOperatorName);
        entityTaskBean.setOperateDateTime(new Date());
        entityTaskBean.setDriverId(driverId);
        entityTaskBean.setDriverName(driverName);
        super.updateNotNull(entityTaskBean);
    }

    /**
     * <pre>
     * 功能：更新当道 taskId 对应的 责任人
     *
     * //1.先查询当前要操作 TaskId 是否存在；若存在继续， 否则抛出业务逻辑异常，当前任务不存在
     *
     * //2.若当前 task.ownevId 责任人ID与要更新的 责任人ID一致；就不进行更新操作，记录一下日记
     *
     * //3.更新 task 司机
     *
     * </pre>
     *
     * @param currentOperatorId   当前操作人的ID
     * @param currentOperatorName 当前操作人的Name
     * @param taskId              要更新的TaskId
     * @param ownevId             要更新的 责任人Id
     * @param ownevName           要更新的 责任人Name
     * @throws ParameterException     若传递参数为空，抛出异常
     * @throws BusinessLogicException 若与业务逻辑有关，抛出此异常
     */
    @Override
    public void updateTaskOwnev(Integer currentOperatorId, String currentOperatorName, Integer taskId, Integer ownevId, String ownevName) throws ParameterException, BusinessLogicException {
        log.info("service request parameters currentOperatorId: " + currentOperatorId + " currentOperatorName: " + currentOperatorName + " taskId: " + taskId + " ownevId: " + ownevId + " ownevName: " + ownevName);

        //检验，参数是否有效。
        if (currentOperatorId == null) {
            log.error("request parameter error, operatorId is null.");
            throw new ParameterException("operatorId is null");
        }
        if (StringUtils.isEmpty(currentOperatorName)) {
            log.error("request parameter error, currentOperatorName is null.");
            throw new ParameterException("request parameter error, currentOperatorName is null.");
        }

        if (taskId == null) {
            log.error("request parameter error, taskId is null.");
            throw new ParameterException("taskId is null");
        }

        if (ownevId == null) {
            log.error("request parameter error, ownevId is null.");
            throw new ParameterException("ownevId is null");
        }
        if (StringUtils.isEmpty(ownevName)) {
            log.error("request parameter error, ownevName is null.");
            throw new ParameterException("request parameter error, ownevName is null.");
        }

        //1.先查询当前要操作 TaskId 是否存在；若存在继续， 否则抛出业务逻辑异常，当前任务不存在
        TaskBean taskBean = super.selectByPrimaryKey(taskId);
        if (taskBean == null || taskBean.getDeleteFlag() == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(taskBean.getDeleteFlag().trim())) {
            log.error("The current task has been created client organization exists.");
            throw new BusinessLogicException("The current task has been created client organization exists.");
        }

        //2.若当前 task.ownevId 责任人ID与要更新的 责任人ID一致；就不进行更新操作，记录一下日记
        if (taskBean.getOwnevId() != null && ownevId.intValue() == taskBean.getOwnevId().intValue()) {
            log.error("The current task ownev is already ownevId: " + ownevId + " ownevName： " + ownevName);
            return;
        }

        //3.更新 task 司机
        TaskBean entityTaskBean = new TaskBean();
        entityTaskBean.setId(taskId);
        entityTaskBean.setOperatorId(currentOperatorId);
        entityTaskBean.setOperatorName(currentOperatorName);
        entityTaskBean.setOperateDateTime(new Date());

        entityTaskBean.setOwnevId(ownevId);
        entityTaskBean.setOwnevName(ownevName);
        entityTaskBean.setOperateDateTime(new Date());
        super.updateNotNull(entityTaskBean);
    }

}
