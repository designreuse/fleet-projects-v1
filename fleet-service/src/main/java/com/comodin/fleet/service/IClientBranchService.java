package com.comodin.fleet.service;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.vo.ClientBranchBeanVo;

import java.util.List;
import java.util.prefs.BackingStoreException;

public interface IClientBranchService extends IBaseService<ClientBranchBean> {

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    List<ClientBranchBean> getListByVo(ClientBranchBeanVo vo) throws ParameterException;

    List<ClientBranchBean> getListClientBranchByClientId(Integer clientId, ClientBranchBeanVo vo);

    List<ClientBranchBean> getListByClientBranchNameLike(Integer clientId, String clientBranchName);

    /**
     * <pre>
     *  根据参数，钥匙串ID，查询所有与该钥匙串，对应的客户分支机构列表
     *
     * </pre>
     *
     * @param keychainId 钥匙串ID
     * @return 返回，根据参数，钥匙串ID，查询所有与该钥匙串，对应的客户分支机构列表
     * @throws ParameterException    若钥匙串ID为空，抛出
     * @throws BusinessLogicException
     */
    List<ClientBranchBean> getClientBranchListByKeychainId(Integer keychainId) throws ParameterException, BusinessLogicException;
}
