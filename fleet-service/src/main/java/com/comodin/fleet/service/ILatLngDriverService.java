package com.comodin.fleet.service;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.LatLngDriverBean;

import java.util.List;

public interface ILatLngDriverService extends IBaseService<LatLngDriverBean> {

    List<LatLngDriverBean> listByDriverUsername(String driverUsername, String phoneUploadTimestampByDate, String lastPhoneUploadTimestamp) throws ParameterException, BusinessLogicException;
}
