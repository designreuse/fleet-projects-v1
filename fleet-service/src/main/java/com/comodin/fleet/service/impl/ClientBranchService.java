package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.service.IClientBranchService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.vo.ClientBranchBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class ClientBranchService extends BaseService<ClientBranchBean> implements IClientBranchService {

    private static final Logger log = Logger.getLogger(ClientBranchService.class);

    @Override
    public List<ClientBranchBean> getListByVo(ClientBranchBeanVo vo) {
        log.info(JSON.toJSONString(vo));

        if (vo == null || vo.getStart() == null || vo.getLength() == null) {
            log.info("parameter error, check paging start length");
            throw new ParameterException("parameter error, check paging start length");
        }

        Example example = new Example(ClientBranchBean.class);
        Example.Criteria criteria = example.createCriteria();


        if (StringUtils.isNotBlank(vo.getName())) {
            criteria.andLike("name", FleetBasiUtil.likePercent(vo.getName().trim()));
        }

        if (vo.getClientId() != null) {
            criteria.andEqualTo("clientId", vo.getClientId());
        }
        if (StringUtils.isNotBlank(vo.getClientName())) {
            criteria.andLike("clientName", FleetBasiUtil.likePercent(vo.getClientName().trim()));
        }

        if (StringUtils.isNotBlank(vo.getContact())) {
            criteria.andLike("contact", FleetBasiUtil.likePercent(vo.getContact().trim()));
        }

        if (StringUtils.isNotBlank(vo.getPhone())) {
            criteria.andLike("phone", FleetBasiUtil.likePercent(vo.getPhone().trim()));
        }


        if (StringUtils.isNotBlank(vo.getAddress())) {
            criteria.andLike("address", FleetBasiUtil.likePercent(vo.getAddress().trim()));
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
    public List<ClientBranchBean> getListClientBranchByClientId(Integer clientId, ClientBranchBeanVo vo) {
        log.info("service request parameters clientId: " + clientId);
        if (clientId == null) {
            throw new ParameterException("parameter error, check  clientId is null");
        }

        return getListByVo(vo);
    }

    @Override
    public List<ClientBranchBean> getListByClientBranchNameLike(Integer clientId, String clientBranchName) {
        log.info("service request parameters clientId: " + clientId);
        log.info("service request parameters clientBranchName: " + clientBranchName);

        if (clientId == null) {
            throw new ParameterException("parameter error, check  clientId is null");
        }
        if (StringUtils.isBlank(clientBranchName)) {
            throw new ParameterException("parameter error, check  clientBranchName is null");
        }

        ClientBranchBeanVo vo = new ClientBranchBeanVo();
        vo.setStart(0);
        vo.setLength(5);
        vo.setClientId(clientId);
        vo.setName(clientBranchName);

        return getListByVo(vo);
    }

    /**
     * <pre>
     *  根据参数，钥匙串ID，查询所有与该钥匙串，对应的客户分支机构列表
     *
     * </pre>
     *
     * @param keychainId 钥匙串ID
     * @return 返回，根据参数，钥匙串ID，查询所有与该钥匙串，对应的客户分支机构列表
     * @throws ParameterException     若钥匙串ID为空，抛出
     * @throws BusinessLogicException
     */
    @Override
    public List<ClientBranchBean> getClientBranchListByKeychainId(Integer keychainId) throws ParameterException, BusinessLogicException {
        log.info("service request parameters keychainId: " + keychainId);
        if (keychainId == null) {
            throw new ParameterException("parameter error, check  keychainId is null");
        }

        ClientBranchBean record = new ClientBranchBean();
        record.setKeychainId(keychainId);
        record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        return super.select(record);
    }

    @Override
    public ClientBranchBean save(ClientBranchBean entity) {
        log.info("service request parameters entity JSON: " + JSON.toJSONString(entity));

        String week = entity.getCronExpression();
        String weekNew = FleetBasiUtil.assemblingCronExpressionWeek(week);

        entity.setCronExpression(weekNew);

        entity.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        entity.setCreateDateTime(new Date());

        return super.save(entity);
    }
}
