package com.comodin.fleet.api.controller;


import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.json.AccessToKen;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IUserService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private IUserService userService;


    @ResponseBody
    @RequestMapping(value = "/userLogin" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity login(String username, String password) {
        log.info("request parameters，username=" + username + " password=" + password);

        UserBean userBean = userService.login(username, password);

        AccessToKen accessToKen = new AccessToKen();
        accessToKen.setAccess_token(userBean.getToken());
        accessToKen.setVehicle_id(1);   //用于测试
        accessToKen.setCurrentServerTimestamp(new Date().getTime());

        return FleetBasiUtil.assemblingReturnResultList(accessToKen);
    }

    //获取，当前服务器时间
    @ResponseBody
    @RequestMapping(value = "/getCurrentServerTimestamp" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity getCurrentServerTimestamp(HttpServletRequest request) {
        //UserBean userBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        AccessToKen accessToKen = new AccessToKen();
        accessToKen.setCurrentServerTimestamp(new Date().getTime());
        return FleetBasiUtil.assemblingReturnResultList(accessToKen);
    }
}
