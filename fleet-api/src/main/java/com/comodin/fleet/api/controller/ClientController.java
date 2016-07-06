package com.comodin.fleet.api.controller;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.TaskTransactionBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IClientService;
import com.comodin.fleet.service.ITaskService;
import com.comodin.fleet.service.ITaskTransactionService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.vo.ClientBranchBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {
    private static final Logger log = Logger.getLogger(ClientController.class);

    @Autowired
    private IClientService clientService;
    @Autowired
    private ITaskTransactionService taskTransactionService;
    @Autowired
    private ITaskService taskService;

    @ResponseBody
    @RequestMapping(value = "/clientGetListTaskByClientId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity clientGetListTaskByClientId(HttpServletRequest request, TaskBeanVo vo) {
        log.info("controller parameters vo JSON: " + JSON.toJSONString(vo));

        UserBean userEntityByToken = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer currentOperatorId = userEntityByToken.getId();
        String currentClientId = userEntityByToken.getSn();
        Integer clientId = (currentClientId == null) ? null : Integer.valueOf(currentClientId);

        List<TaskBean> list = clientService.getListTaskByClientId(currentOperatorId, clientId, vo);


        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/clientGetTaskDetailsByTaskId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity clientGetTaskDetailsByTaskId(HttpServletRequest request, Integer taskId) {
        log.info("controller parameters taskId: " + taskId);

        UserBean userEntityByToken = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer currentOperatorId = userEntityByToken.getId();
        String currentClientId = userEntityByToken.getSn();
        Integer clientId = (currentClientId == null) ? null : Integer.valueOf(currentClientId);

        TaskBean taskBean = clientService.getTaskDetailsByTaskId(currentOperatorId, clientId, taskId);
        return FleetBasiUtil.assemblingReturnResultList(taskBean);
    }

    @ResponseBody
    @RequestMapping(value = "/clientGetAllTaskTransactionByTaskId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity clientGetAllTaskTransactionByTaskId(HttpServletRequest request, Integer taskId) {
        log.info("controller parameters taskId: " + taskId);

        List<TaskTransactionBean> list = taskTransactionService.getListAllByTaskId(taskId);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, null);
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/clientGetListClientBranchByClientId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity clientGetListClientBranchByClientId(HttpServletRequest request, ClientBranchBeanVo vo) {
        log.info("controller parameters vo JSON: " + JSON.toJSONString(vo));

        UserBean userEntityByToken = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer currentOperatorId = userEntityByToken.getId();
        String currentClientId = userEntityByToken.getSn();
        Integer clientId = (currentClientId == null) ? null : Integer.valueOf(currentClientId);

        List<ClientBranchBean> list = clientService.getListClientBranchByClientId(currentOperatorId, clientId, vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }


    @ResponseBody
    @RequestMapping(value = "/clientAddTaskByClientBranchId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity clientAddTaskByClientBranchId(HttpServletRequest request, Integer clientBranchId, String taskExecutionDateTimeStamp, String taskType) {
        log.info("controller parameters clientBranchId: " + clientBranchId);
        log.info("controller parameters taskExecutionDateTimeStamp: " + taskExecutionDateTimeStamp);
        log.info("controller parameters taskType: " + taskType);

        UserBean userEntityByToken = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer currentOperatorId = userEntityByToken.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(userEntityByToken);
        String currentClientId = userEntityByToken.getSn();
        Integer clientId = (currentClientId == null) ? null : Integer.valueOf(currentClientId);

        taskService.addTask(currentOperatorId, currentOperatorName, clientId, clientBranchId, taskExecutionDateTimeStamp, taskType);

        return FleetBasiUtil.assemblingReturnResultList("update task success.");
    }

}
