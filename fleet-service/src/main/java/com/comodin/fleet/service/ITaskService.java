package com.comodin.fleet.service;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.vo.TaskBeanVo;

import java.util.List;

public interface ITaskService extends IBaseService<TaskBean> {

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    List<TaskBean> getListByVo(TaskBeanVo vo) throws ParameterException;


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
    TaskBean addTask(Integer currentOperatorId, String currentOperatorName, Integer clientId, Integer clientBranchId, String taskExecutionDateTimeStamp, String taskType) throws ParameterException, BusinessLogicException;

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
    void updateTaskStatus(Integer currentOperatorId, String currentOperatorName, Integer operateTaskId, String operateTaskStatus, String operateTaskComment, Integer taskOwnevId, String taskOwnevName) throws ParameterException, BusinessLogicException;


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
    void updateTaskDriver(Integer currentOperatorId, String currentOperatorName, Integer taskId, Integer driverId, String driverName) throws ParameterException, BusinessLogicException;

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
    void updateTaskOwnev(Integer currentOperatorId, String currentOperatorName, Integer taskId, Integer ownevId, String ownevName) throws ParameterException, BusinessLogicException;

}
