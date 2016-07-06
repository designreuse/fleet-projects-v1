package com.comodin.fleet.service.impl;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.TaskTransactionBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.service.ITaskService;
import com.comodin.fleet.service.ITaskTransactionService;
import com.comodin.fleet.service.IUserService;
import com.comodin.fleet.vo.TaskTransactionBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskTransactionService extends BaseService<TaskTransactionBean> implements ITaskTransactionService {

    private static final Logger log = Logger.getLogger(TaskTransactionService.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskService taskService;

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    @Override
    public List<TaskTransactionBean> getListByVo(TaskTransactionBeanVo vo) throws ParameterException {
        return null;
    }

    /**
     * <pre>
     *  返回 与任务ID，对应的所有任务记录，但不包括，业务逻辑删除标记的数据
     * </pre>
     *
     * @param taskId 任务ID
     * @return 返回 与任务ID，对应的所有任务记录，但不包括，业务逻辑删除标记的数据
     * @throws ParameterException 若 taskId 为空抛出，参数异常
     */
    @Override
    public List<TaskTransactionBean> getListAllByTaskId(Integer taskId) throws ParameterException {
        log.info("service request parameters taskId: " + taskId);

        //检验，参数是否有效。
        if (taskId == null) {
            log.error("request parameter error, taskId is null.");
            throw new ParameterException("taskId is null");
        }

        TaskTransactionBean record = new TaskTransactionBean();
        record.setTaskId(taskId);
        return mapper.select(record);
    }


    @Override
    public TaskTransactionBean addTaskTransaction(Integer currentOperatorId, String currentOperatorName, Integer taskId, String operateType, String operateComment, Integer taskOwnevId, String taskOwnevName) throws ParameterException, BusinessLogicException {
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
            throw new ParameterException("request parameter error, taskId is null.");
        }

        if (StringUtils.isEmpty(operateType)) {
            log.error("request parameter error, operateType is null.");
            throw new ParameterException("request parameter error, operateType is null.");
        }

        TaskTransactionBean transactionBean = new TaskTransactionBean();
        transactionBean.setId(null);
        transactionBean.setTaskId(taskId);
        transactionBean.setOperatorId(currentOperatorId);
        transactionBean.setOperatorName(currentOperatorName);
        transactionBean.setOperateType(operateType);
        transactionBean.setOperateComment(operateComment);
        transactionBean.setOperateTime(new Date());

        transactionBean.setTaskOwnevId(taskOwnevId);
        transactionBean.setTaskOwnevName(taskOwnevName);
        transactionBean.setTaskOeateTime((taskOwnevId == null) ? null : new Date());

        transactionBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);

        return super.save(transactionBean);
    }

}
