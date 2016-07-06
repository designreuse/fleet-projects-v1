package com.comodin.fleet.service;

import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.ClientBean;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.TaskTransactionBean;
import com.comodin.fleet.vo.ClientBeanVo;
import com.comodin.fleet.vo.ClientBranchBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;

import java.util.List;

public interface IClientService extends IBaseService<ClientBean> {

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    List<ClientBean> getListByVo(ClientBeanVo vo) throws ParameterException;


    List<ClientBean> getListByClientNameLike(String clientName) throws ParameterException;


    List<TaskBean> getListTaskByClientId(Integer currentOperatorId, Integer clientId, TaskBeanVo vo);

    TaskBean getTaskDetailsByTaskId(Integer currentOperatorId, Integer clientId, Integer taskId);

    List<ClientBranchBean> getListClientBranchByClientId(Integer currentOperatorId, Integer clientId, ClientBranchBeanVo vo);

}
