package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.validation.IBaseValidGroupAdd;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBean;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IClientService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.ClientBeanVo;
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
public class ClientController {
    private static final Logger log = Logger.getLogger(ClientController.class);


    @Autowired
    private IClientService clientService;

    @RequestMapping(value = "/clientList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "client/list";
    }

    @ResponseBody
    @RequestMapping(value = "/clientList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(ClientBeanVo vo) {
        log.info("Controller parameters vo JSON: " + JSON.toJSONString(vo));

        List<ClientBean> list = clientService.getListByVo(vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/clientAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(@Validated(value = IBaseValidGroupAdd.class) ClientBean clientBean, BindingResult bindingResult) {
        log.info("Controller parameters clientBranchBean JSON: " + JSON.toJSONString(clientBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(bindingResult);

        clientService.save(clientBean);

        return FleetBasiUtil.assemblingReturnResultList("client add success.");
    }


    @ResponseBody
    @RequestMapping(value = "/clientDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("Controller parameters idsJSON: " + JSON.toJSONString(ids));

        clientService.batchDeleteFlagByPrimaryKeys((Object[]) ids);

        return FleetBasiUtil.assemblingReturnResultList("client delete success.");
    }


    @ResponseBody
    @RequestMapping(value = "/clientUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("Controller parameters id: " + id);

        ClientBean clientBean = clientService.selectByPrimaryKey(id);
        if (clientBean == null) {
            throw new BusinessLogicException("Current Client no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(clientBean);
    }

    @ResponseBody
    @RequestMapping(value = "/clientUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, HttpServletRequest request, @Validated(value = IBaseValidGroupUpdate.class) ClientBean clientBean, BindingResult result) {
        log.info("parameters id: " + id + " update data JSON: " + JSON.toJSONString(clientBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        clientService.updateNotNull(clientBean);

        return FleetBasiUtil.assemblingReturnResultList("client update success.");
    }

}
