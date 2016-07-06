package com.comodin.fleet.service.impl;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.service.ILatLngDriverService;
import com.comodin.fleet.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class LatLngDriverService extends BaseService<LatLngDriverBean> implements ILatLngDriverService {

    private static final Logger log = Logger.getLogger(LatLngDriverService.class);


    @Override
    public List<LatLngDriverBean> listByDriverUsername(String driverUsername, String phoneUploadTimestampByDate, String lastPhoneUploadTimestamp) throws ParameterException, BusinessLogicException {
        log.info("service request parameters driverUsername: " + driverUsername);
        log.info("service request parameters phoneUploadTimestampByDate: " + phoneUploadTimestampByDate);
        Date timeStampToDateTime = DateUtil.timeStampToDate(lastPhoneUploadTimestamp, null);
        log.info("service request parameters lastPhoneUploadTimestamp: " + lastPhoneUploadTimestamp);

        //检验，参数是否有效。
        if (StringUtils.isEmpty(driverUsername)) {
            log.error("request parameter error, driverUsername is null.");
            throw new ParameterException("driverUsername is null");
        }

        Example example = new Example(LatLngDriverBean.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("driverUsername", driverUsername.trim());
        if (StringUtils.isNotBlank(phoneUploadTimestampByDate)) {
            criteria.andGreaterThanOrEqualTo("phoneUploadTimestamp", phoneUploadTimestampByDate + " 00:00:00");
            criteria.andLessThanOrEqualTo("phoneUploadTimestamp", phoneUploadTimestampByDate + " 23:59:59");
        }

        if (StringUtils.isNotBlank(lastPhoneUploadTimestamp)) {
            criteria.andGreaterThan("phoneUploadTimestamp", timeStampToDateTime);
        }

        criteria.andEqualTo("status", ConstantsFinalValue.COORDINATES_DRIVER_STATUS_ENABLE);
        criteria.andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL);
        example.orderBy("phoneUploadTimestamp").asc();
        return mapper.selectByExample(example);
    }
}
