package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.VehicleBean;
import com.comodin.fleet.service.IVehicleService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.vo.VehicleBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class VehicleService extends BaseService<VehicleBean> implements IVehicleService {

    private static final Logger log = Logger.getLogger(VehicleService.class);

    @Override
    public List<VehicleBean> getListByVo(VehicleBeanVo vo) {
        log.info("service getListByVo request parameters vo JSON: " + JSON.toJSONString(vo));

        if (vo == null) {
            log.error("request parameter error, check  vo is null");
            throw new ParameterException("Query parameter error, check  vo is null");
        }
        if (vo.getStart() == null || vo.getLength() == null) {
            log.error("request parameter error, check paging start:" + vo.getStart() + " length: " + vo.getLength());
            throw new ParameterException("Query parameter error, check paging start:" + vo.getStart() + " length: " + vo.getLength());
        }

        Example example = new Example(VehicleBean.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(vo.getName())) {
            criteria.andLike("name", FleetBasiUtil.likePercent(vo.getName().trim()));
        }
        if (StringUtils.isNotBlank(vo.getLicensePlate())) {
            criteria.andLike("licensePlate", FleetBasiUtil.likePercent(vo.getLicensePlate().trim()));
        }

        if (vo.getLocationDeviceId() != null) {
            criteria.andLike("locationDeviceId", FleetBasiUtil.likePercent(vo.getLocationDeviceId().toString().trim()));
        }

        if (StringUtils.isNotBlank(vo.getStatus())) {
            criteria.andEqualTo("status", vo.getStatus().trim());
        }
        if (StringUtils.isNotBlank(vo.getCreateDataTimeStartTime())) {
            criteria.andGreaterThanOrEqualTo("createDateTime", vo.getCreateDataTimeStartTime().trim());
        }
        if (StringUtils.isNotBlank(vo.getCreateDataTimeEndTime())) {
            criteria.andLessThanOrEqualTo("createDateTime", vo.getCreateDataTimeEndTime().trim());
        }
        criteria.andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL);
        return mapper.selectByExampleAndRowBounds(example, new RowBounds(vo.getStart(), vo.getLength()));
    }

    @Override
    public VehicleBean save(VehicleBean entity) {

        log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

        if (entity == null) {
            log.error("parameter error, check  entity is null");
            throw new ParameterException("parameter error, check  entity is null");
        }

        if (StringUtils.isEmpty(entity.getName())) {
            log.error("parameter error, check  name is null");
            throw new ParameterException("parameter error, check name is null");
        }

        VehicleBean record = new VehicleBean();
        record.setName(entity.getName().trim());
        int selectCount = mapper.selectCount(record);
        if (selectCount > 0) {
            log.error("Current Vehicle Name already exists, please re-enter another name.");
            throw new BusinessLogicException("Current Vehicle Name already exists, please re-enter another name.");
        }

        record = new VehicleBean();
        record.setLicensePlate(entity.getLicensePlate().trim());
        record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        selectCount = mapper.selectCount(record);
        if (selectCount > 0) {
            log.error("Change the current Vehicle LicensePlate already exists, please re-enter another name");
            throw new BusinessLogicException("Change the current Vehicle LicensePlate already exists, please re-enter another name");
        }

        entity.setCreateDateTime(new Date());
        entity.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        return super.save(entity);
    }

    @Override
    public void updateNotNull(VehicleBean entity) {
        log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

        if (entity == null) {
            log.error("parameter error, check  entity is null");
            throw new ParameterException("parameter error, check  entity is null");
        }

        if (StringUtils.isEmpty(entity.getName())) {
            log.error("parameter error, check  name is null");
            throw new ParameterException("parameter error, check name is null");
        }

        if (entity.getId() == null) {
            log.error("parameter error, check  Vehicle is null");
            throw new ParameterException("parameter error, check Vehicle is null");
        }

        VehicleBean record = new VehicleBean();
        record.setId(entity.getId());
        record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        int selectCount = mapper.selectCount(record);
        if (selectCount != 1) {
            log.error("Current Vehicle does not exist");
            throw new BusinessLogicException("Current Vehicle does not exist");
        }

        Example example = new Example(VehicleBean.class);
        example.createCriteria().andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL)
                .andNotEqualTo("id", entity.getId()).andEqualTo("name", entity.getName().trim());
        selectCount = mapper.selectCountByExample(example);
        if (selectCount > 0) {
            log.error("Change the current Vehicle name already exists, please re-enter another name");
            throw new BusinessLogicException("Change the current Vehicle name already exists, please re-enter another name");
        }

        example = new Example(VehicleBean.class);
        example.createCriteria().andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL)
                .andNotEqualTo("id", entity.getId()).andEqualTo("licensePlate", entity.getName().trim());
        selectCount = mapper.selectCountByExample(example);
        if (selectCount > 0) {
            log.error("Change the current Vehicle LicensePlate already exists, please re-enter another name");
            throw new BusinessLogicException("Change the current Vehicle LicensePlate already exists, please re-enter another name");
        }

        super.updateNotNull(entity);
    }
}
