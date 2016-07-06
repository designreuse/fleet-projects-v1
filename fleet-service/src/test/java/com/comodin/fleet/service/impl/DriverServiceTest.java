package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.service.IDriverService;
import com.comodin.fleet.util.date.DateUtil;
import com.comodin.fleet.vo.KeychainBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.BaseTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by supeng on 5/19/2016.
 */
public class DriverServiceTest extends BaseTest {

    @Autowired
    private IDriverService driverService;

    @Test
    public void testGetListTaskByDriverId() throws Exception {

        TaskBeanVo vo = new TaskBeanVo();
        vo.setStart(0);
        vo.setLength(10);
        List<TaskBean> taskBeanList = driverService.getListTaskByDriverId(3, vo);
    }

    @Test(expectedExceptions = {ParameterException.class})
    public void testGetListTaskByDriverIdParamDriverIdIsNull() throws Exception {

        TaskBeanVo vo = new TaskBeanVo();
        vo.setLength(10);
        List<TaskBean> taskBeanList = driverService.getListTaskByDriverId(null, vo);
    }

    @Test(expectedExceptions = {ParameterException.class})
    public void testGetListTaskByDriverIdParamVoIsNull() throws Exception {

        List<TaskBean> taskBeanList = driverService.getListTaskByDriverId(3, null);
    }

    @Test(expectedExceptions = {ParameterException.class})
    public void testGetListTaskByDriverIdParamVoStartIsNull() throws Exception {

        TaskBeanVo vo = new TaskBeanVo();
        vo.setLength(10);
        List<TaskBean> taskBeanList = driverService.getListTaskByDriverId(3, vo);
    }

    @Test(expectedExceptions = {ParameterException.class})
    public void testGetListTaskByDriverIdParamVoLengthIsNull() throws Exception {

        TaskBeanVo vo = new TaskBeanVo();
        vo.setStart(0);
        List<TaskBean> taskBeanList = driverService.getListTaskByDriverId(3, vo);
    }

    @Test
    public void testGetTaskDetailsByTaskId() {
        driverService.getTaskDetailsByTaskId(3, 1);
    }

    @Test
    public void testUpdateTaskStatusCommentByTaskId() throws Exception {

    }

    @Test
    public void testUploadTaskImageByTaskId() throws Exception {

    }

    @Test
    public void testGetListKeychainByDriverId() throws Exception {
        KeychainBeanVo vo = new KeychainBeanVo();
        vo.setStart(0);
        vo.setLength(10);

        List<KeychainBean> list = driverService.getListKeychainByDriverId(3, vo);

        for (KeychainBean keychainBean : list) {
            System.out.println(JSON.toJSONString(keychainBean));
        }
    }

    @Test
    public void testUpdateClaimKeychainByKeychainId() throws Exception {
        //driverService.updateClaimKeychainByKeychainId(3,1);
    }

    @Test()
    public void testUpdateReturnKeychainByKeychainId() throws Exception {
        //driverService.updateReturnKeychainByKeychainId(3,1);
    }

    @DataProvider(name = "vehicleLongitudeLatitudeData")
    public Object[][] vehicleLongitudeLatitudeData() {

        Random random1 = new Random();
        Random random2 = new Random();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            Map<String, Object> mapDriver = new HashMap<>();
            mapDriver.put("driverId", i);
            mapDriver.put("driverUsername", "driver" + i);
            mapDriver.put("driverName", "name driver" + i);
            mapDriver.put("driverLocalIp", "192.168.1." + i);


            List<LatLngDriverBean> coordinatesDriverBeanList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                LatLngDriverBean coordinatesDriverBean = new LatLngDriverBean();
                coordinatesDriverBean.setPhoneLat("23." + random2.nextInt(100000));
                coordinatesDriverBean.setPhoneLng("113." + random1.nextInt(100000));

                int nextInt = new Random().nextInt(10);
                Date getCoordinatesDateTime = DateUtil.addMinute(new Date(), -nextInt);
                coordinatesDriverBean.setPhoneUploadTimestamp(getCoordinatesDateTime);

                coordinatesDriverBeanList.add(coordinatesDriverBean);
            }
            mapDriver.put("coordinatesDataJson", JSON.toJSONString(coordinatesDriverBeanList));
            resultList.add(mapDriver);
        }

        Object[][] result = new Object[resultList.size()][];
        for (int i = 0, len = resultList.size(); i < len; i++) {
            result[i] = new Object[]{resultList.get(i)};
        }
        return result;
    }

    @Test(dataProvider = "vehicleLongitudeLatitudeData")
    public void testUploadVehicleLongitudeLatitudeData(HashMap<String, Object> mapDriver) throws Exception {
        Integer driverId = (Integer) mapDriver.get("driverId");
        String driverUsername = String.valueOf(mapDriver.get("driverUsername"));
        String driverName = String.valueOf(mapDriver.get("driverName"));
        String driverLocalIp = String.valueOf(mapDriver.get("driverLocalIp"));
        String coordinatesDataJson = String.valueOf(mapDriver.get("coordinatesDataJson"));

        driverService.uploadPhoneLatLngData(driverId, driverUsername, driverName, driverLocalIp, coordinatesDataJson);
    }
}