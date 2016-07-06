package com.comodin.fleet.service;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.TaskTransactionBean;
import com.comodin.fleet.vo.TaskTransactionBeanVo;

import java.util.List;

public interface ITaskTransactionService extends IBaseService<TaskTransactionBean> {

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    List<TaskTransactionBean> getListByVo(TaskTransactionBeanVo vo) throws ParameterException;

    /**
     * <pre>
     *  返回 与任务ID，对应的所有任务记录，但不包括，业务逻辑删除标记的数据
     * </pre>
     *
     * @param taskId 任务ID
     * @return 返回 与任务ID，对应的所有任务记录，但不包括，业务逻辑删除标记的数据
     * @throws ParameterException 若 taskId 为空抛出，参数异常
     */
    List<TaskTransactionBean> getListAllByTaskId(Integer taskId) throws ParameterException;


    TaskTransactionBean addTaskTransaction(Integer currentOperatorId, String currentOperatorName, Integer taskId, String operateType, String operateComment, Integer taskOwnevId, String taskOwnevName) throws ParameterException, BusinessLogicException;
}
