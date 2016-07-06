package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.RoleBean;
import com.comodin.fleet.core.bean.UserBean;
import com.comodin.fleet.service.IRoleService;
import com.comodin.fleet.service.IUserService;
import test.BaseTest;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.vo.UserBeanVo;
import org.testng.annotations.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.testng.AssertJUnit.assertNotNull;

public class UserServiceTest extends BaseTest {

    @Inject
    private IUserService userService;
    @Inject
    private IRoleService roleService;


    @BeforeClass
    public void beforeClass() throws Exception {
        List<RoleBean> roleBeanList = roleService.selectAll();
        FleetBasiUtil.setRedisRoleList(roleBeanList);
    }

    @AfterClass
    public void afterClass() {

    }

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @DataProvider(name = "userBeans")
    public Object[][] userBeans() {
        List<UserBean> userBeanLIst = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserBean userBean = new UserBean();
            userBean.setId(null);

            userBean.setUsername("dataProvider");
            userBean.setPassword("dataProvider");

            userBean.setType(ConstantsFinalValue.USER_TYPE_EMPLOYEE);
            userBean.setSn("");

            userBean.setFirstName("firstUser tFirstName");
            userBean.setLastName("firstUser LastName");

            userBean.setBirthday(new Date());
            userBean.setCurp("123456789012345678");
            userBean.setGender(ConstantsFinalValue.USER_GENDER_FEMALE);
            userBean.setPhone("11111111111");
            userBean.setLandline("010-12345678");
            userBean.setEmail("firstUser email");
            userBean.setAddress("firstUser address");
            userBean.setPersonalPhoto("firstUser 头像");
            userBean.setPersonalProfile("firstUser 个人简介");
            userBean.setStatus(ConstantsFinalValue.USER_STATUS_ENABLE);
            userBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
            userBean.setCreateDateTime(new Date());
            userBean.setRoles("1|2|3");

            userBeanLIst.add(userBean);
        }

        Object[][] result = new Object[userBeanLIst.size()][];
        for (int i = 0, len = userBeanLIst.size(); i < len; i++) {
            result[i] = new Object[]{userBeanLIst.get(i)};
        }
        return result;
    }

    /**
     * Asserts that User Properties are not null.
     *
     * @param userBean
     */
    private void assertNotNullUserProperties(UserBean userBean) {
        assertNotNull("User must not be null!", userBean);
        assertNotNull("Id must not be null!", userBean.getId());
        assertNotNull("UserName must not be null!", userBean.getUsername());
    }

    @Test
    public void testGetListByVo1() throws Exception {
        UserBeanVo userBeanVo = new UserBeanVo();
        userBeanVo.setStart(0);
        userBeanVo.setLength(100);
        userService.getListByVo(userBeanVo);
    }

    @Test
    public void testGetListByVo2() throws Exception {
//        UserBeanVo userBeanVo = new UserBeanVo();
//        userBeanVo.setStart(-1);
//        userBeanVo.setLength(5);
//        userService.getListByVo(userBeanVo);
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testGetListByVoParamEmpty() throws Exception {
        userService.getListByVo(null);
    }



    @Test(dataProvider = "userBeans")
    public void testSave(UserBean userBean) throws Exception {
        UserBean save = userService.save(userBean);
        System.out.println("insert UserBean id: " + userBean.getId() + "\t" + JSON.toJSON(userBean));
        assertNotNullUserProperties(save);
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testSaveParamEmpty() throws Exception {
        UserBean userBean = new UserBean();
        userBean.setUsername(null);

        UserBean save = userService.save(userBean);
        assertNotNullUserProperties(save);
    }

    @Test(expectedExceptions = BusinessLogicException.class)
    public void testSaveUserNameIsExists() throws Exception {
        UserBean userBean = new UserBean();
        userBean.setUsername("admin1");
        userBean.setType(ConstantsFinalValue.USER_TYPE_EMPLOYEE);

        UserBean save = userService.save(userBean);
        assertNotNullUserProperties(save);
    }

    @Test
    public void testLogin() throws Exception {
        userService.login("test1", "test1");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testLoginParamEmpty() throws Exception {
        userService.login("", "");
    }
    @Test(expectedExceptions = BusinessLogicException.class)
    public void testLoginWrong() throws Exception {
        userService.login("test1", "sdafsadf");
    }


    @Test
    public void testLogout() throws Exception {
        userService.logout("safd");
    }

    @Test(expectedExceptions = ParameterException.class)
    public void testLogoutParamEmpty() throws Exception {
        userService.logout("");
    }


    @Test
    public void testCheckUserNameIsAvailable() throws Exception {

    }

}