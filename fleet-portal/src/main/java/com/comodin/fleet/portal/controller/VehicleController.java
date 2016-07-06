package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.validation.IBaseValidGroupAdd;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.VehicleBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IVehicleService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.VehicleBeanVo;
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
public class VehicleController {

    private static final Logger log = Logger.getLogger(VehicleController.class);
    @Autowired
    private IVehicleService vehicleService;

    @RequestMapping(value = "/vehicleList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "vehicle/list";
    }

    @ResponseBody
    @RequestMapping(value = "/vehicleList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(VehicleBeanVo vo) {
        log.info("request parameters vo JSON: " + JSON.toJSONString(vo));

        List<VehicleBean> list = vehicleService.getListByVo(vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/vehicleAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(HttpServletRequest request, @Validated(value = IBaseValidGroupAdd.class) VehicleBean vehicleBean, BindingResult bindingResult) {
        log.info("request parameters vehicleBean JSON: " + JSON.toJSONString(vehicleBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(bindingResult);

        vehicleService.save(vehicleBean);

        return FleetBasiUtil.assemblingReturnResultList("vehicle add success.");
    }


    @ResponseBody
    @RequestMapping(value = "/vehicleDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("vehicleDelete request parameters idsJSON: " + JSON.toJSONString(ids));

        vehicleService.batchDeleteFlagByPrimaryKeys((Object[]) ids);
        return FleetBasiUtil.assemblingReturnResultList("vehicle delete success.");
    }


    @ResponseBody
    @RequestMapping(value = "/vehicleUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("update vehicle request parameters id: " + id);

        VehicleBean vehicleBean = vehicleService.selectByPrimaryKey(id);
        if (vehicleBean == null) {
            throw new BusinessLogicException("Current Vehicle no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(vehicleBean);
    }

    @ResponseBody
    @RequestMapping(value = "/vehicleUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, HttpServletRequest request, @Validated(value = IBaseValidGroupUpdate.class) VehicleBean vehicleBean, BindingResult result) {
        log.info("update vehicle request parameters id: " + id + " update data JSON: " + JSON.toJSONString(vehicleBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        vehicleService.updateNotNull(vehicleBean);

        return FleetBasiUtil.assemblingReturnResultList("vehicle update success.");
    }

}
