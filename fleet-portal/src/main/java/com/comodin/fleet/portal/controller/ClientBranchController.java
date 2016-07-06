package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IClientBranchService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.ClientBranchBeanVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
public class ClientBranchController {
    private static final Logger log = Logger.getLogger(ClientBranchController.class);


    @Autowired
    private IClientBranchService clientBranchService;

    @RequestMapping(value = "/clientBranchList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "clientBranch/list";
    }

    @ResponseBody
    @RequestMapping(value = "/clientBranchList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(ClientBranchBeanVo vo) {
        log.info("Controller request parameters vo JSON: " + JSON.toJSONString(vo));

        List<ClientBranchBean> list = clientBranchService.getListByVo(vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/clientBranchAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(HttpServletRequest request, ClientBranchBean clientBranchBean) {
        log.info("request parameters clientBranchBean JSON: " + JSON.toJSONString(clientBranchBean));
        UserBean userEntityByToken = FleetBasiUtil.getSessionUserBeanByRquestCookie(request, UserBean.class);

        clientBranchService.save(clientBranchBean);

        return FleetBasiUtil.assemblingReturnResultList("clientBranch add success.");
    }


    @ResponseBody
    @RequestMapping(value = "/clientBranchDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("Controller request parameters idsJSON: " + JSON.toJSONString(ids));

        clientBranchService.batchDeleteFlagByPrimaryKeys((Object[]) ids);

        return FleetBasiUtil.assemblingReturnResultList("clientBranch delete success.");
    }


    @ResponseBody
    @RequestMapping(value = "/clientBranchUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("Controller request parameters id: " + id);

        ClientBranchBean clientBranchBean = clientBranchService.selectByPrimaryKey(id);
        if (clientBranchBean == null) {
            throw new BusinessLogicException("Current Client Branch no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(clientBranchBean);
    }

    @ResponseBody
    @RequestMapping(value = "/clientBranchUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, HttpServletRequest request, @Validated(value = IBaseValidGroupUpdate.class) ClientBranchBean clientBranchBean, BindingResult result) {
        log.info("request parameters id: " + id + " update data JSON: " + JSON.toJSONString(clientBranchBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        clientBranchService.updateNotNull(clientBranchBean);

        return FleetBasiUtil.assemblingReturnResultList("clientBranch update success.");
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxGetClientBranchListByKeychainId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity ajaxGetClientBranchListByKeychainId(Integer keychainId) {
        log.info("Controller request parameters keychainId: " + keychainId);

        List<ClientBranchBean> list = clientBranchService.getClientBranchListByKeychainId(keychainId);

        return FleetBasiUtil.assemblingReturnResultList(list);
    }
}
