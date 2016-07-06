package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IKeychainService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.KeychainBeanVo;
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
public class KeychainController {

    private static final Logger log = Logger.getLogger(KeychainController.class);
    @Autowired
    private IKeychainService keychainService;

    @RequestMapping(value = "/keychainList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "keychain/list";
    }

    @ResponseBody
    @RequestMapping(value = "/keychainList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(KeychainBeanVo vo) {
        log.info("controller parameters vo JSON: " + JSON.toJSONString(vo));

        List<KeychainBean> list = keychainService.getListByVo(vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/keychainAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(HttpServletRequest request, KeychainBean keychainBean, BindingResult bindingResult) {
        log.info("controller parameters keychainBean JSON: " + JSON.toJSONString(keychainBean));

        keychainService.save(keychainBean);

        return FleetBasiUtil.assemblingReturnResultList("Keychain add success.");
    }


    @ResponseBody
    @RequestMapping(value = "/keychainDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("controller parameters idsJSON: " + JSON.toJSONString(ids));

        keychainService.batchDeleteFlagByPrimaryKeys((Object[]) ids);

        return FleetBasiUtil.assemblingReturnResultList("delete success.");
    }

    @ResponseBody
    @RequestMapping(value = "/keychainUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("controller parameters id: " + id);

        KeychainBean keychainBean = keychainService.selectByPrimaryKey(id);
        if (keychainBean == null) {
            throw new BusinessLogicException("Current Keychain no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(keychainBean);
    }

    @ResponseBody
    @RequestMapping(value = "/keychainUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, HttpServletRequest request, @Validated(value = IBaseValidGroupUpdate.class) KeychainBean keychainBean, BindingResult result) {
        log.info("controller parameters id: " + id + " update data JSON: " + JSON.toJSONString(keychainBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        keychainService.updateNotNull(keychainBean);

        return FleetBasiUtil.assemblingReturnResultList("user update success.");
    }

}
