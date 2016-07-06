package com.comodin.fleet.service;


import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.vo.UserBeanVo;

import java.util.List;

public interface IUserService extends IBaseService<UserBean> {

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    List<UserBean> getListByVo(UserBeanVo vo) throws ParameterException;

    /**
     * 用户登陆
     *
     * @param username 用户名，必需参数
     * @param password 用户密码，，必需参数
     * @return 返回UserBean，含有 accessToken值
     * @throws ParameterException     若用户名或密码未填，抛出
     * @throws BusinessLogicException 若用户名与密码不匹配， 或者 用户状态是未激活状态，抛出
     */
    UserBean login(String username, String password) throws ParameterException, BusinessLogicException;


    /**
     * 用户退出
     *
     * @param token token标识
     * @throws ParameterException
     * @throws BusinessLogicException
     */
    void logout(String token) throws ParameterException, BusinessLogicException;


    /**
     * 检查用户名是否已经存在
     *
     * @param username 要检查的用户名
     * @return 返回 true代表该 用户名可用,即数据库中不存在；flase代表该 用户名在数据库中已经存在。
     * @throws ParameterException     若 useranme 为空，抛出
     * @throws BusinessLogicException 预留
     */
    boolean checkUserNameIsAvailable(String username) throws ParameterException, BusinessLogicException;

    /**
     * 根据 userId ，查询出 业务逻辑删除标志为，正常状态的用户
     *
     * @param primaryKey userId
     * @return
     */
    UserBean getUserByPrimaryKeyAndDeleteFlagNormal(Object primaryKey);

    /**
     * 根据 userId，查询出 当前用户状态为 激活状态的用户
     *
     * @param primaryKey userId
     * @return
     */
    UserBean getUserByPrimaryKeyStatusActivated(Object primaryKey);

}
