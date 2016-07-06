package com.comodin.fleet.portal.controller;


import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.ILatLngDriverService;
import com.comodin.fleet.util.FleetBasiUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LatLngDriverController {
    private static final Logger log = Logger.getLogger(LatLngDriverController.class);

    @Autowired
    private ILatLngDriverService coordinatesDriverService;

    @RequestMapping(value = "/monitor" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String monitor() {
        return "monitor/monitor";
    }

    @RequestMapping(value = "/monitorDriver" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String monitorDriver() {
        return "monitor/monitorDriver";
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxLatLngDriverGetAllDriverCurrentLatLng" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity getAllDriverCurrentLatLng() {
        return FleetBasiUtil.assemblingReturnResultList(FleetBasiUtil.getAllDriverCurrentCoordinates());
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxLatLngDriverListByDriverUsername" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity listByDriverUserName(String driverUsername, String phoneUploadTimestampByDate, String lastPhoneUploadTimestamp) {
        log.info("request parameters driverUsername: " + driverUsername);
        log.info("request parameters phoneUploadTimestampByDate: " + phoneUploadTimestampByDate);
        log.info("request parameters lastPhoneUploadTimestamp: " + lastPhoneUploadTimestamp);

        List<LatLngDriverBean> list = coordinatesDriverService.listByDriverUsername(driverUsername, phoneUploadTimestampByDate, lastPhoneUploadTimestamp);
        return FleetBasiUtil.assemblingReturnResultList(list);
    }
}
