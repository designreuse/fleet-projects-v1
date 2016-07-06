package com.comodin.fleet.api.controller;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.json.ResultEntity;
import com.comodin.fleet.service.IDriverService;
import com.comodin.fleet.util.CommonUtil;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.date.DateUtil;
import com.comodin.fleet.vo.KeychainBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DriverController {
    private static final Logger log = Logger.getLogger(DriverController.class);

    @Autowired
    private IDriverService driverService;

    //获取，司机自己当前的任务列表
    @ResponseBody
    @RequestMapping(value = "/driverGetListTaskByDriverId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity driverGetListTaskByDriverId(HttpServletRequest request, TaskBeanVo vo) {
        log.info("controller parameters vo JSON: " + JSON.toJSONString(vo));

        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();

        List<TaskBean> list = driverService.getListTaskByDriverId(driverId, vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    //查看，某个任务的详细
    @ResponseBody
    @RequestMapping(value = "/driverGetTaskDetailsByTaskId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity driverGetTaskDetailsByTaskId(HttpServletRequest request, Integer taskId) {
        log.info("controller parameters taskId: " + taskId);

        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();

        TaskBean taskBean = driverService.getTaskDetailsByTaskId(driverId, taskId);
        return FleetBasiUtil.assemblingReturnResultList(taskBean);
    }

    //更新，任务状态
    @ResponseBody
    @RequestMapping(value = "/driverUpdateTaskStatusByTaskId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity driverUpdateTaskResultByTaskId(HttpServletRequest request, Integer operateTaskId, String operateStatus, String operateComment) {
        log.info("controller parameters " + " operateTaskId: " + operateTaskId + " operateStatus: " + operateStatus + " operateComment: " + operateComment);

        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();

        driverService.updateTaskStatusCommentByTaskId(driverId, operateTaskId, operateStatus, operateComment);

        return FleetBasiUtil.assemblingReturnResultList("update task status success.");
    }

    //上传，签名 或，截图 【以 pictureType 进行区分】
    @ResponseBody
    @RequestMapping(value = "/driverUploadTaskResultImageByTaskId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity driverUploadTaskResultImageByTaskId(HttpServletRequest request, String pictureType, Integer operateTaskId, MultipartFile multipartFile) throws IOException {
        log.info("controller parameters pictureType: " + pictureType);
        log.info("controller parameters operateTaskId: " + operateTaskId);
        log.info("controller parameters MultipartFile: " + multipartFile);

        //'任务，结果；类型：INT(1)；值：必填【0代表待执行；1代表执行中；2代表执行成功；3代表执行失败】；默认值：0'
        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();
        if (multipartFile == null || multipartFile.isEmpty()) {
            log.error("parameter error, driverId is null.");
            throw new ParameterException("multipartFile is null");
        }

        if (StringUtils.isEmpty(pictureType)) {
            log.error("parameter error, pictureType is null.");
            throw new ParameterException("pictureType is null");
        }

        pictureType = pictureType.trim();
        if (!ConstantsFinalValue.TASK_UPLOAD_IMAGES_PHOTOGRAPH.equals(pictureType) && !ConstantsFinalValue.TASK_UPLOAD_IMAGES_SIGNATURE.equals(pictureType)) {
            String info = "parameter error, pictureType Only " + ConstantsFinalValue.TASK_UPLOAD_IMAGES_PHOTOGRAPH + " or " + ConstantsFinalValue.TASK_UPLOAD_IMAGES_SIGNATURE;
            log.error(info);
            throw new ParameterException(info);
        }

        ServletContext sc = request.getSession().getServletContext();
        String dir = sc.getRealPath(ConstantsFinalValue.UPLOAD_IMAGE_PATH);    //设定文件保存的目录
        String originalFilename = multipartFile.getOriginalFilename();          //文件原始名字
        log.info("文件原始名字: " + originalFilename);

        String newFileName = pictureType + "-" + operateTaskId + "-" + DateUtil.timeStamp2Date(String.valueOf(new Date().getTime()), "yyyy-MM-dd HH.mm.ss") + originalFilename;
        FileUtils.writeByteArrayToFile(new File(dir, newFileName), multipartFile.getBytes());

        //上传
        String filePath = ConstantsFinalValue.UPLOAD_IMAGE_PATH + newFileName;
        driverService.uploadTaskImageByTaskId(driverId, operateTaskId, filePath);

        return FleetBasiUtil.assemblingReturnResultList("add task result Image success.");
    }

    //获取，该司机自己的，需要领取钥匙列表
    @ResponseBody
    @RequestMapping(value = "/driverGetListKeychainByDriverId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.GET)
    public ResultEntity driverGetListKeychainByDriverId(HttpServletRequest request, KeychainBeanVo vo) {
        log.info("controller parameters vo JSON: " + JSON.toJSONString(vo));

        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();

        List<KeychainBean> list = driverService.getListKeychainByDriverId(driverId, vo);

        Map<String, Object> stringObjectMap = FleetBasiUtil.assemblingReturnResultListBandPageInfo(list, vo.getDraw());
        return FleetBasiUtil.assemblingReturnResultList(stringObjectMap);
    }

    //领取 钥匙串
    @ResponseBody
    @RequestMapping(value = "/driverClaimKeychainByKeychainId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity driverClaimKeychainByKeychainId(HttpServletRequest request, Integer keychainId) {
        log.info("controller parameters keychainId: " + keychainId);

        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();

        driverService.updateClaimKeychainByKeychainId(driverId, keychainId);

        return FleetBasiUtil.assemblingReturnResultList("chain Keychain success.");
    }

    //归还 钥匙串
    @ResponseBody
    @RequestMapping(value = "/driverReturnKeychainByKeychainId" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity driverReturnKeychainByKeychainId(HttpServletRequest request, Integer keychainId) {
        log.info("controller parameters keychainId: " + keychainId);

        UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        Integer driverId = driverUserBean.getId();

        driverService.updateReturnKeychainByKeychainId(driverId, keychainId);

        return FleetBasiUtil.assemblingReturnResultList("return Keychain success.");
    }

    //上传，车辆经纬度数据接口
    @ResponseBody
    @RequestMapping(value = "/driverUploadPhoneLatLngData" + ConstantsFinalValue.INTERCEPTOR_URL_SUFFIX, method = RequestMethod.POST)
    public ResultEntity driverUploadPhoneLatLngData(HttpServletRequest request, String driverUsername, String phoneImei, String latLngDataJson) {
        log.info("controller parameters driverUsername: " + driverUsername);
        log.info("controller parameters phoneIMEI: " + phoneImei);
//        log.info("controller parameters latLngDataJson: " + latLngDataJson);

        String phoneLocalIp = CommonUtil.getRemoteIp(request);

        Map<String, Object> resultInfo = driverService.uploadPhoneLatLngData(driverUsername, phoneImei, phoneLocalIp, latLngDataJson);

        //UserBean driverUserBean = FleetBasiUtil.getSessionUserBeanByRquestHeader(request, UserBean.class);
        //Integer driverId = driverUserBean.getId();
        //String driverName = FleetBasiUtil.assemblingUserFullNameByLastNameAndFirstName(driverUserBean);
        //String driverLocalIp = CommonUtil.getRemoteIp(request);
        //driverService.uploadPhoneLatLngData(driverId, driverUsername, driverName, driverLocalIp, latLngDataJson);

        return FleetBasiUtil.assemblingReturnResultList(resultInfo);
    }
}
