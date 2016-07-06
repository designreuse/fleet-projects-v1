package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.*;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IClientBranchService;
import com.comodin.fleet.service.IClientService;
import com.comodin.fleet.service.ITaskService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.SessionUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
public class TaskController {
    private static final Logger log = Logger.getLogger(TaskController.class);


    @Autowired
    private ITaskService taskService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IClientBranchService clientBranchService;


    @RequestMapping(value = "/taskList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "task/list";
    }

    @ResponseBody
    @RequestMapping(value = "/taskList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(TaskBeanVo vo) {
        log.info("request parameters vo JSON: " + JSON.toJSONString(vo));

        List<TaskBean> list = taskService.getListByVo(vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }


    @ResponseBody
    @RequestMapping(value = "/taskAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(HttpServletRequest request, TaskBean taskBean) {
        log.info("request parameters taskBean JSON: " + JSON.toJSONString(taskBean));
        UserBean userEntityByToken = FleetBasiUtil.getSessionUserBeanByRquestCookie(request, UserBean.class);

        Integer currentOperatorId = userEntityByToken.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(userEntityByToken);
        Integer clientId = taskBean.getClientId();
        Integer clientBranchId = taskBean.getClientBranchId();
        String executionDate = (taskBean.getExecutionDate() == null) ? null : String.valueOf(taskBean.getExecutionDate().getTime());
        String taskType = taskBean.getType();

        taskService.addTask(currentOperatorId, currentOperatorName, clientId, clientBranchId, executionDate, taskType);

        return FleetBasiUtil.assemblingReturnResultList("task add success.");
    }

    @ResponseBody
    @RequestMapping(value = "/taskAddOperate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity taskAddOperate(HttpServletRequest request, TaskTransactionBean taskTransactionBean) {
        log.info("request parameters taskTransactionBean JSON: " + JSON.toJSONString(taskTransactionBean));

        String token = FleetBasiUtil.getTokenByRquestCookie(request);
        UserBean userEntityByToken = SessionUtil.getUserEntityByToken(token, UserBean.class);

        Integer currentOperatorId = userEntityByToken.getId();
        String currentOperatorName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(userEntityByToken);

        Integer operateTaskId = taskTransactionBean.getTaskId();
        String operateType = taskTransactionBean.getOperateType();
        String operateComment = taskTransactionBean.getOperateComment();

        taskService.updateTaskStatus(currentOperatorId, currentOperatorName, operateTaskId, operateType, operateComment, null, null);

        return FleetBasiUtil.assemblingReturnResultList("taskTransaction Operate success.");
    }

    @ResponseBody
    @RequestMapping(value = "/taskDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("request parameters idsJSON: " + JSON.toJSONString(ids));

        taskService.batchDeleteFlagByPrimaryKeys((Object[]) ids);

        return FleetBasiUtil.assemblingReturnResultList("task delete success.");
    }


    @ResponseBody
    @RequestMapping(value = "/taskUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("request parameters id: " + id);

        TaskBean taskBean = taskService.selectByPrimaryKey(id);
        if (taskBean == null) {
            throw new BusinessLogicException("Current Task no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(taskBean);
    }

    @ResponseBody
    @RequestMapping(value = "/taskUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, @Validated(value = IBaseValidGroupUpdate.class) TaskBean taskBean, BindingResult result) {
        log.info("request parameters id: " + id + " update data JSON: " + JSON.toJSONString(taskBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        taskService.updateNotNull(taskBean);

        return FleetBasiUtil.assemblingReturnResultList("task update success.");
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxFuzzyMatchingListClientByClientName" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity ajaxFuzzyMatchingListClientByClientName(String clientName) {
        log.info("request parameters clientName: " + clientName);

        List<ClientBean> list = clientService.getListByClientNameLike(clientName);

        return FleetBasiUtil.assemblingReturnResultList(list);
    }


    @ResponseBody
    @RequestMapping(value = "/ajaxFuzzyMatchingListClientBranchByClientAndClientName" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity ajaxFuzzyMatchingListClientBranchByClientAndClientName(Integer clientId, String clientBranchName) {
        log.info("request parameters clientId: " + clientId);
        log.info("request parameters clientBranchName: " + clientBranchName);

        List<ClientBranchBean> list = clientBranchService.getListByClientBranchNameLike(clientId, clientBranchName);

        return FleetBasiUtil.assemblingReturnResultList(list);
    }

}
