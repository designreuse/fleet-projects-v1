package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.TaskTransactionBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.ITaskTransactionService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.TaskTransactionBeanVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
public class TaskTransactionController {

    private static final Logger log = Logger.getLogger(TaskTransactionController.class);
    @Autowired
    private ITaskTransactionService taskTransactionService;

    @RequestMapping(value = "/taskTransactionList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "taskTransaction/list";
    }

    @ResponseBody
    @RequestMapping(value = "/taskTransactionList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(TaskTransactionBeanVo vo) {
        log.info("controller  request parameters vo JSON: " + JSON.toJSONString(vo));

        List<TaskTransactionBean> list = taskTransactionService.getListByVo(vo);
        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/getTaskTransactionListByTaskId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity getTaskTransactionListByTaskId(Integer taskId) {
        log.info("controller request parameters taskId: " + taskId);

        List<TaskTransactionBean> list = taskTransactionService.getListAllByTaskId(taskId);

        return FleetBasiUtil.assemblingReturnResultList(list);
    }


    @ResponseBody
    @RequestMapping(value = "/taskTransactionAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(@RequestBody TaskTransactionBean taskTransactionBean) {
        log.info("controller add request parameters taskTransactionBean JSON: " + JSON.toJSONString(taskTransactionBean));

        taskTransactionService.save(taskTransactionBean);

        return FleetBasiUtil.assemblingReturnResultList("taskTransaction add success.");
    }


    @ResponseBody
    @RequestMapping(value = "/taskTransactionDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("controller request parameters idsJSON: " + JSON.toJSONString(ids));

        taskTransactionService.batchDeleteFlagByPrimaryKeys((Object[]) ids);

        return FleetBasiUtil.assemblingReturnResultList("taskTransaction delete success.");
    }


    @ResponseBody
    @RequestMapping(value = "/taskTransactionUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("controller request parameters id: " + id);

        TaskTransactionBean taskTransactionBean = taskTransactionService.selectByPrimaryKey(id);
        if (taskTransactionBean == null) {
            throw new BusinessLogicException("Current Task Transaction no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(taskTransactionBean);
    }

    @ResponseBody
    @RequestMapping(value = "/taskTransactionUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, HttpServletRequest request, @Validated(value = IBaseValidGroupUpdate.class) TaskTransactionBean taskTransactionBean, BindingResult result) {
        log.info("controller request parameters id: " + id + " update data JSON: " + JSON.toJSONString(taskTransactionBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        taskTransactionService.updateNotNull(taskTransactionBean);

        return FleetBasiUtil.assemblingReturnResultList("taskTransaction update success.");
    }
}
