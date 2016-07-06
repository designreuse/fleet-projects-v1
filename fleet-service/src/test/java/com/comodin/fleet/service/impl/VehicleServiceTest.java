package com.comodin.fleet.service.impl;

import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.VehicleBean;
import com.comodin.fleet.service.IVehicleService;
import test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by supeng on 05/25/2016.
 */
public class VehicleServiceTest extends BaseTest {
    @Autowired
    private IVehicleService vehicleService;

    @Test
    public void testGetListByVo() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        VehicleBean vehicleBean = new VehicleBean();
        vehicleBean.setName("test Name");
        vehicleBean.setLicensePlate("test LicensePlate");
        vehicleBean.setLocationDeviceId("sdaf");
        vehicleBean.setStatus(ConstantsFinalValue.VEHICLE_STATUS_ENABLE);
        vehicleService.save(vehicleBean);
    }

    @Test
    public void testUpdateNotNull() throws Exception {
        VehicleBean vehicleBean = new VehicleBean();
        vehicleBean.setId(1);
        vehicleBean.setName("test Name");
        vehicleBean.setLicensePlate("test LicensePlate");
        vehicleBean.setLocationDeviceId("sdaf");

        vehicleService.updateNotNull(vehicleBean);
    }

}