package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.service.IKeychainService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.vo.KeychainBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class KeychainService extends BaseService<KeychainBean> implements IKeychainService {

    private static final Logger log = Logger.getLogger(KeychainService.class);

    @Override
    public List<KeychainBean> getListByVo(KeychainBeanVo vo) throws ParameterException {

        log.info("service parameters vo JSON: " + JSON.toJSONString(vo));

        if (vo == null) {
            log.error("parameter error, check  vo is null");
            throw new ParameterException("parameter error, check  vo is null");
        }
        if (vo.getStart() == null || vo.getLength() == null) {
            log.error("parameter error, check paging start:" + vo.getStart() + " length: " + vo.getLength());
            throw new ParameterException("parameter error, check paging start:" + vo.getStart() + " length: " + vo.getLength());
        }

        Example example = new Example(KeychainBean.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(vo.getName())) {
            criteria.andLike("name", FleetBasiUtil.likePercent(vo.getName().trim()));
        }


        if (vo.getAssignDriverId() != null) {
            criteria.andEqualTo("assignDriverId", vo.getAssignDriverId());
        }
        if (StringUtils.isNotBlank(vo.getAssignDriverName())) {
            criteria.andLike("assignDriverName", FleetBasiUtil.likePercent(vo.getAssignDriverName().trim()));
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
    public KeychainBean save(KeychainBean entity) {
        log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

        if (entity == null) {
            log.error("parameter error, check  entity is null");
            throw new ParameterException("parameter error, check  entity is null");
        }

        if (StringUtils.isEmpty(entity.getName())) {
            log.error("parameter error, check  name is null");
            throw new ParameterException("parameter error, check name is null");
        }

        KeychainBean record = new KeychainBean();
        record.setName(entity.getName().trim());
        int selectCount = mapper.selectCount(record);
        if (selectCount > 0) {
            log.error("Current keychain name already exists, please re-enter another name");
            throw new BusinessLogicException("Current keychain name already exists, please re-enter another name");
        }

        entity.setCreateDateTime(new Date());
        entity.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        return super.save(entity);
    }

    @Override
    public void updateNotNull(KeychainBean entity) {

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
            log.error("parameter error, check  keychain is null");
            throw new ParameterException("parameter error, check keychain is null");
        }


        KeychainBean record = new KeychainBean();
        record.setId(entity.getId());
        record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        int selectCount = mapper.selectCount(record);
        if (selectCount != 1) {
            log.error("Current keychain does not exist");
            throw new BusinessLogicException("Current keychain does not exist");
        }


        Example example = new Example(KeychainBean.class);
        example.createCriteria().andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL)
                .andNotEqualTo("id", entity.getId()).andEqualTo("name", entity.getName().trim());
        selectCount = mapper.selectCountByExample(example);
        if (selectCount > 0) {
            log.error("Change the current keychain name already exists, please re-enter another name");
            throw new BusinessLogicException("Change the current keychain name already exists, please re-enter another name");
        }

        super.updateNotNull(entity);
    }

    public void updateKeychainStatusByPrimaryKey(KeychainBean entity) throws ParameterException, BusinessLogicException {
        log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

        if (entity == null) {
            log.error("parameter error, check  entity is null");
            throw new ParameterException("parameter error, check  entity is null");
        }

        if (entity.getId() == null) {
            log.error("parameter error, check  keychain is null");
            throw new ParameterException("parameter error, check keychain is null");
        }


        KeychainBean record = new KeychainBean();
        record.setId(entity.getId());
        record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        int selectCount = mapper.selectCount(record);
        if (selectCount != 1) {
            log.error("Current keychain does not exist");
            throw new BusinessLogicException("Current keychain does not exist");
        }

        super.updateNotNull(entity);
    }
}
