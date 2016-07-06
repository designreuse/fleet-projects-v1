package com.comodin.fleet.portal.quartz;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.service.IDriverService;
import com.comodin.fleet.util.date.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

@Component
public class TestMonitorLatLngDriverAllJob {
    private static final Logger log = Logger.getLogger(TestMonitorLatLngDriverAllJob.class);

    @Inject
    private IDriverService driverService;

    public void doIt() {
        log.info("...................................TestMonitorLatLngDriverAllJob start.....");

        List<Map<String, Object>> initDriverTestDataList = initDriverTestData();

        for (Map<String, Object> stringObjectMap : initDriverTestDataList) {
            Integer driverId = (Integer) stringObjectMap.get("driverId");
            String driverName = String.valueOf(stringObjectMap.get("driverUsername"));

            String driverUsername = String.valueOf(stringObjectMap.get("driverUsername"));
            String phoneImei = String.valueOf(stringObjectMap.get("phoneImei"));
            String phoneLocalIp = String.valueOf(stringObjectMap.get("phoneLocalIp"));
            String latLngDataJson = String.valueOf(stringObjectMap.get("latLngDataJson"));

            driverService.uploadPhoneLatLngData(driverUsername,phoneImei,phoneLocalIp,latLngDataJson);

        }
        log.info("...................................TestMonitorLatLngDriverAllJob end.....");
    }


    private List<Map<String, Object>> initDriverTestData() {
        Random random1 = new Random();
        Random random2 = new Random();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (int i = 1001; i <= 2000; i++) {

            Map<String, Object> mapDriver = new HashMap<>();
            mapDriver.put("driverId", i);
            mapDriver.put("driverName", "fullName" + i);

            mapDriver.put("driverUsername", "driver" + i);
            mapDriver.put("phoneImei", "864563029558626");
            mapDriver.put("phoneLocalIp", "192.168.1." + (100 + i));

            List<LatLngDriverBean> latLngDriverBeanList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                LatLngDriverBean latLngDriverBean = new LatLngDriverBean();
//                // 23.113721, 113.311426    公司
//                latLngDriverBean.setLat("23.11" + random2.nextInt(1000));
//                latLngDriverBean.setLng("113.31" + random1.nextInt(1000));

                // 墨西哥城，19.432965，-99.132621
                latLngDriverBean.setPhoneLat("19.4" + random2.nextInt(10000));
                latLngDriverBean.setPhoneLng("-99.1" + random1.nextInt(10000));

                int nextInt = new Random().nextInt(10);
                Date phoneUploadTimestamp = DateUtil.addMinute(new Date(), -nextInt);
                latLngDriverBean.setPhoneUploadTimestamp(phoneUploadTimestamp);

                latLngDriverBeanList.add(latLngDriverBean);
            }
            mapDriver.put("latLngDataJson", JSON.toJSONString(latLngDriverBeanList));

            resultList.add(mapDriver);

        }
        return resultList;
    }
}
