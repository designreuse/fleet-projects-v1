package com.comodin.fleet.portal.controller;


import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.validation.IBaseValidGroupAdd;
import com.comodin.fleet.basic.validation.IBaseValidGroupUpdate;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IUserService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.ValidationUtil;
import com.comodin.fleet.vo.UserBeanVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/userList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String list() {
        return "user/list";
    }

    @ResponseBody
    @RequestMapping(value = "/userList" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity list(UserBeanVo vo) {
        log.info("request parameters vo JSON: " + JSON.toJSONString(vo));

        List<UserBean> list = userService.getListByVo(vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    @ResponseBody
    @RequestMapping(value = "/getUserDetailByUserId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity getUserDetailByUserId(String userId) {
        log.info("getUserDetailByUserId request parameters userId: " + userId);

        UserBean userBean = userService.selectByPrimaryKey(userId);

        return FleetBasiUtil.assemblingReturnResultList(userBean);
    }

    @ResponseBody
    @RequestMapping(value = "/userAdd" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity add(HttpServletRequest request, @Validated(value = IBaseValidGroupAdd.class) UserBean userBean, BindingResult bindingResult) {

        log.info("taskAdd request parameters userBean JSON: " + JSON.toJSONString(userBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(bindingResult);

        //判断 再次密码是否一致，若不一致抛出异常，返回给客户端；以参数异常
        String confirmPassword = request.getParameter("confirmPassword");
        if (confirmPassword == null || !confirmPassword.equals(userBean.getPassword().trim())) {
            throw new ParameterException("Fill in the two passwords do not match");
        }

        userService.save(userBean);

        return FleetBasiUtil.assemblingReturnResultList("user add success.");
    }


    @ResponseBody
    @RequestMapping(value = "/userDelete" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity delete(Integer... ids) {
        log.info("userDelete request parameters idsJSON: " + JSON.toJSONString(ids));

        userService.batchDeleteFlagByPrimaryKeys((Object[]) ids);

        return FleetBasiUtil.assemblingReturnResultList("delete success.");
    }

    @ResponseBody
    @RequestMapping(value = "/userUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity update(int id) {
        log.info("request parameters id: " + id);

        UserBean userBean = userService.selectByPrimaryKey(id);
        if (userBean == null) {
            throw new BusinessLogicException("Current User no longer exists.");
        }

        return FleetBasiUtil.assemblingReturnResultList(userBean);
    }

    @ResponseBody
    @RequestMapping(value = "/userUpdate" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity update(int id, HttpServletRequest request, @Validated(value = IBaseValidGroupUpdate.class) UserBean userBean, BindingResult result) {
        log.info("update user request parameters id: " + id + " update data JSON: " + JSON.toJSONString(userBean));

        //先检查，使用hiberante检验框架，是否包含对应Bean的校验失败的字段，并且根据国际化处理消息内容
        ValidationUtil.WebRequestParametersValidation(result);

        //判断 再次密码是否一致，若不一致抛出异常，返回给客户端；以参数异常
        String confirmPassword = request.getParameter("confirmPassword");
        if (confirmPassword == null || !confirmPassword.equals(userBean.getPassword().trim())) {
            throw new ParameterException("Fill in the two passwords do not match");
        }

        userService.updateNotNull(userBean);

        return FleetBasiUtil.assemblingReturnResultList("user update success.");
    }


    @ResponseBody
    @RequestMapping(value = "/getDepartmentAndRoleALLByStatusIsActivated" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity getDepartmentAndRoleALLByStatusIsActivated() {
        log.info("userSearch user request get type：Get information department List and role List");

        Map<String, Map<String, ?>> map = new HashMap<>();

        map.put("departmentList", FleetBasiUtil.getRedisDepartmentMap());

        map.put("roleList", FleetBasiUtil.getRedisRoleMap());

        log.info("userSearch user request get type：Get information department table and role role \n" + JSON.toJSONString(map));
        return FleetBasiUtil.assemblingReturnResultList(map);
    }

    @ResponseBody
    @RequestMapping(value = "/checkUserNameExists" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public String checkUserNameExists(String userName) {
        log.info("add user checkUserNameExists parameters userName: " + userName);

        boolean userNameIsAvailable = userService.checkUserNameIsAvailable(userName);

        log.info("add user checkUserNameExists result " + userNameIsAvailable);

        // The back-end then will determine if the username is available or not,
        // and finally returns a JSON { "valid": true } or { "valid": false }
        String validJSON = "[\"valid\":" + userNameIsAvailable + "]";
        return validJSON;
    }

    @RequestMapping(value = "/userLogin" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    @ResponseBody
    @RequestMapping(value = "/userLogin" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity login(String username, String password) {
        log.info("user Login，username=" + username + " password=" + password);

        UserBean userBean = userService.login(username, password);
        Map<String, Object> map = new HashMap<>();
        map.put(ConstantsFinalValue.TOKEN, userBean.getToken());
        map.put(ConstantsFinalValue.SERVER_REDIRECT_URL_MARK, "/main" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX);
        return FleetBasiUtil.assemblingReturnResultList(map);
    }


    @RequestMapping(value = "/main" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public String main() {
        return "main/main";
    }


}
