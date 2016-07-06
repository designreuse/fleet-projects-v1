package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.service.IUserService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.util.SessionUtil;
import com.comodin.fleet.vo.UserBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends BaseService<UserBean> implements IUserService {

    private static final Logger log = Logger.getLogger(UserService.class);

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    @Override
    public List<UserBean> getListByVo(UserBeanVo vo) throws ParameterException {
        log.info("service getListByVo request parameters vo JSON: " + JSON.toJSONString(vo));

        if (vo == null) {
            log.error("request parameter error, check  vo is null");
            throw new ParameterException("Query parameter error, check  vo is null");
        }
        if (vo.getStart() == null || vo.getLength() == null) {
            log.error("request parameter error, check paging start:" + vo.getStart() + " length: " + vo.getLength());
            throw new ParameterException("Query parameter error, check paging start:" + vo.getStart() + " length: " + vo.getLength());
        }


        Example example = new Example(UserBean.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(vo.getUsername())) {
            criteria.andLike("username", FleetBasiUtil.likePercent(vo.getUsername().trim()));
        }
        if (StringUtils.isNotBlank(vo.getPhone())) {
            criteria.andLike("phone", FleetBasiUtil.likePercent(vo.getPhone().trim()));
        }

        if (StringUtils.isNotBlank(vo.getCurp())) {
            criteria.andLike("curp", FleetBasiUtil.likePercent(vo.getCurp().trim()));
        }
        if (StringUtils.isNotBlank(vo.getGender())) {
            criteria.andEqualTo("gender", vo.getGender());
        }

        if (StringUtils.isNotBlank(vo.getDepartmentId())) {
            criteria.andEqualTo("departmentId", vo.getDepartmentId());
        }
        if (StringUtils.isNotBlank(vo.getRoles())) {
            String roles = vo.getRoles();
            roles = ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR + roles.trim() + ConstantsFinalValue.ROLE_USER_PRIVILEGE_SEPARATOR;
            criteria.andLike("roles", FleetBasiUtil.likePercent(roles));
        }


        if (StringUtils.isNotBlank(vo.getLastLoginTimeStartTime())) {
            criteria.andGreaterThanOrEqualTo("lastLoginTime", vo.getLastLoginTimeStartTime());
        }
        if (StringUtils.isNotBlank(vo.getLastLoginTimeEndTime())) {
            criteria.andLessThanOrEqualTo("lastLoginTime", vo.getLastLoginTimeEndTime());
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

    /**
     * 用户登陆
     *
     * @param username 用户名，必需参数
     * @param password 用户密码，，必需参数
     * @return 返回UserBean，含有 accessToken值
     * @throws ParameterException     若用户名或密码未填，抛出
     * @throws BusinessLogicException 若用户名与密码不匹配， 或者 用户状态是未激活状态，抛出
     */
    @Override
    public UserBean login(String username, String password) throws ParameterException, BusinessLogicException {
        log.info("service login request parameters username: " + username + " password: " + password);

        //检验，参数是否有效。
        if (StringUtils.isBlank(username)) {
            log.error("request parameter error, Username can not be empty.");
            throw new ParameterException("Username can not be empty.");
        } else if (password == null || "".equals(password.trim())) {
            log.error("request parameter error, Password can not be empty.");
            throw new ParameterException("Password can not be empty.");
        }

        //根据用户名，查询数据库；检验：用户名和密码是否正确，是否激活，是否有分配有角色
        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        userBean = mapper.selectOne(userBean);
        if (userBean == null || !password.equals(userBean.getPassword())) {
            log.error("Username or password is incorrect, please re-enter.");
            throw new BusinessLogicException("Username or password is incorrect, please re-enter.");
        }
        if (!ConstantsFinalValue.USER_STATUS_ENABLE.equals(userBean.getStatus())) {
            log.error("Username or password is incorrect, please re-enter.");
            throw new BusinessLogicException("The user does not activate, please contact customer service.");
        }

        //创建 accessToken 根据登陆用户，校验用户和密码成功之后，存入到达Redies缓存中。
        String access_token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        userBean.setToken(access_token);

        //写入redis session 缓存数据
        String userBeanToJSONString = JSON.toJSONString(userBean);
        SessionUtil.setAttribute(access_token, ConstantsFinalValue.REDIS_SESSION_USERINFO, userBeanToJSONString, ConstantsFinalValue.REDIS_SESSION_EXPIRES_IN);
        //组装，用户对应的，所有角色中，所有包含的，功能权限列表
        if (userBean.getRoles() != null) {
            String userPrivilege = FleetBasiUtil.assemblingUserPrivilege(userBean.getRoles());
            log.info("username: " + username + " all privilege: " + userPrivilege);
            SessionUtil.setAttribute(access_token, ConstantsFinalValue.REDIS_SESSION_PRIVILEGES, userPrivilege, ConstantsFinalValue.REDIS_SESSION_EXPIRES_IN);
        }

        return userBean;
    }

    /**
     * 用户退出
     *
     * @param token token标识
     * @throws ParameterException
     * @throws BusinessLogicException
     */
    @Override
    public void logout(String token) throws ParameterException, BusinessLogicException {
        log.info("service logout request parameters token: " + token);

        //检验，参数是否有效。
        if (StringUtils.isBlank(token)) {
            log.error("request parameter error, token can not be empty.");
            throw new ParameterException("token can not be empty.");
        }

        try {
            SessionUtil.removeAttribute(token);
            log.info(" user logout remove token Redis success.");
        } catch (Exception e) {
            log.error("user logout remove token Redis error Exception " + e.getMessage());
        }
    }

    /**
     * 检查用户名是否已经存在
     *
     * @param username 要检查的用户名
     * @return 返回 true代表该 用户名可用,即数据库中不存在；flase代表该 用户名在数据库中已经存在。
     * @throws ParameterException     若 useranme 为空，抛出
     * @throws BusinessLogicException 预留
     */
    @Override
    public boolean checkUserNameIsAvailable(String username) throws ParameterException, BusinessLogicException {
        log.info("service checkUserNameIsAvailable request parameters username: " + username);

        //检验，参数是否有效。
        if (StringUtils.isBlank(username)) {
            log.error("username can not be empty." + username);
            throw new ParameterException("username can not be empty.");
        }

        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        int selectCount = mapper.selectCount(userBean);

        return selectCount == 0;
    }

    /**
     * 根据 userId ，查询出 业务逻辑删除标志为，正常状态的用户
     *
     * @param primaryKey userId
     * @return
     */
    @Override
    public UserBean getUserByPrimaryKeyAndDeleteFlagNormal(Object primaryKey) {
        UserBean userBean = super.selectByPrimaryKey(primaryKey);
        if (userBean == null || !ConstantsFinalValue.DELETE_FLAG_NORMAL.equals(userBean.getDeleteFlag())) {
            return null;
        }
        return userBean;
    }

    /**
     * 根据 userId，查询出 当前用户状态为 激活状态的用户
     *
     * @param primaryKey userId
     * @return
     */
    @Override
    public UserBean getUserByPrimaryKeyStatusActivated(Object primaryKey) {

        UserBean userBean = getUserByPrimaryKeyAndDeleteFlagNormal(primaryKey);

        if (userBean == null || !ConstantsFinalValue.USER_STATUS_ENABLE.equals(userBean.getStatus())) {
            return null;
        }
        return userBean;
    }


    @Override
    public UserBean save(UserBean entity) {
        log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

        if (entity == null) {
            log.error("parameter error, check  entity is null");
            throw new ParameterException("parameter error, check  entity is null");
        }

        if (StringUtils.isEmpty(entity.getUsername())) {
            log.error("parameter error, check  userName is null");
            throw new ParameterException("parameter error, check userName is null");
        }

        if (StringUtils.isEmpty(entity.getType())) {
            log.error("parameter error, check  userType is null");
            throw new ParameterException("parameter error, check userType is null");
        }

        if (ConstantsFinalValue.USER_TYPE_EMPLOYEE.equals(entity.getType().trim())) {
            entity.setSn("");
        } else {
            entity.setSn("1");
            if (StringUtils.isEmpty(entity.getSn())) {
                log.error("The current user type for the customer, the customer must first select");
                throw new ParameterException("The current user type for the customer, the customer must first select");
            }
        }

        UserBean record = new UserBean();
        record.setUsername(entity.getUsername().trim());
        int selectCount = mapper.selectCount(record);
        if (selectCount > 0) {
            log.error("Current userName already exists, please re-enter another name.");
            throw new BusinessLogicException("Current userName already exists, please re-enter another name.");
        }

        if (entity.getRoles() != null) {
            String strRoles = entity.getRoles();
            if (strRoles.contains(",")) {
                strRoles = strRoles.replace(",", "|");
            }
            entity.setRoles(strRoles);
        }

        entity.setCreateDateTime(new Date());
        entity.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        return super.save(entity);
    }


    @Override
    public void updateNotNull(UserBean entity) {
        log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

        if (entity == null) {
            log.error("parameter error, check  entity is null");
            throw new ParameterException("parameter error, check  entity is null");
        }

        if (StringUtils.isEmpty(entity.getUsername())) {
            log.error("parameter error, check  userName is null");
            throw new ParameterException("parameter error, check userName is null");
        }

        if (StringUtils.isEmpty(entity.getType())) {
            log.error("parameter error, check  userType is null");
            throw new ParameterException("parameter error, check userType is null");
        }

        if (ConstantsFinalValue.USER_TYPE_EMPLOYEE.equals(entity.getType().trim())) {
            entity.setSn("");
        } else {
            entity.setSn("1");
            if (StringUtils.isEmpty(entity.getSn())) {
                log.error("The current user type for the customer, the customer must first select");
                throw new ParameterException("The current user type for the customer, the customer must first select");
            }
        }

        UserBean record = new UserBean();
        record.setId(entity.getId());
        record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        int selectCount = mapper.selectCount(record);
        if (selectCount != 1) {
            log.error("Current User does not exist");
            throw new BusinessLogicException("Current User does not exist");
        }

        Example example = new Example(UserBean.class);
        example.createCriteria().andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL)
                .andNotEqualTo("id", entity.getId()).andEqualTo("username", entity.getUsername().trim());
        selectCount = mapper.selectCountByExample(example);
        if (selectCount > 0) {
            log.error("Change the current userName already exists, please re-enter another name");
            throw new BusinessLogicException("Change the current userName already exists, please re-enter another name");
        }

        super.updateNotNull(entity);
    }

}
