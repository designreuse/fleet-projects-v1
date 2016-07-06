package com.comodin.fleet.service;


import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.vo.KeychainBeanVo;

import java.util.List;

public interface IKeychainService extends IBaseService<KeychainBean> {

    List<KeychainBean> getListByVo(KeychainBeanVo vo) throws ParameterException;

    void updateKeychainStatusByPrimaryKey(KeychainBean entity) throws ParameterException, BusinessLogicException;
}
