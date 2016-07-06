package com.comodin.fleet.service.impl;


import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBean;
import com.comodin.fleet.service.IClientService;
import com.comodin.fleet.vo.ClientBeanVo;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.BaseTest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by supeng on 5/10/2016.
 */
public class ClientServiceTest extends BaseTest {

    @Inject
    private IClientService clientService;

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @DataProvider(name = "clientBeanVoList")
    public Object[][] clientBeanVoList() {

        ClientBeanVo clientBeanVo = new ClientBeanVo();
        clientBeanVo.setStart(0);
        clientBeanVo.setLength(10);

        ClientBeanVo clientBeanVo1 = new ClientBeanVo();
        clientBeanVo1.setStart(11);
        clientBeanVo1.setLength(20);
        clientBeanVo1.setName("沃尔玛 test");
        return new Object[][]{{clientBeanVo}, {clientBeanVo1}};
    }


    @DataProvider(name = "clientBean")
    public Object[][] clientBean() {

        ClientBean clientBean = new ClientBean();
        clientBean.setId(null);
        clientBean.setName("沃尔玛 test");
        clientBean.setPhone("11111111111");
        clientBean.setStatus(ConstantsFinalValue.CLIENT_STATUS_DISABLE);
        clientBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        clientBean.setCreateDateTime(new Date());

        return new Object[][]{{clientBean}};
    }

    @DataProvider(name = "clientBeanList")
    public Object[][] clientBeanList() {
        List<ClientBean> clientBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ClientBean clientBean = new ClientBean();
            clientBean.setId(null);
            clientBean.setName("沃尔玛 test" + i);
            clientBean.setPhone("11111111111");
            clientBean.setStatus(ConstantsFinalValue.CLIENT_STATUS_DISABLE);
            clientBean.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
            clientBean.setCreateDateTime(new Date());
            clientBeanList.add(clientBean);
        }

        Object[][] result = new Object[clientBeanList.size()][];
        for (int i = 0, len = clientBeanList.size(); i < len; i++) {
            result[i] = new Object[]{clientBeanList.get(i)};
        }
        return result;
    }

    @Test(dataProvider = "clientBeanVoList")
    public void testGetListByVo(ClientBeanVo vo) throws Exception {
        vo.setName("沃尔玛");
        clientService.getListByVo(vo);
    }

    @Test(expectedExceptions = {ParameterException.class}, dataProvider = "clientBeanVoList")
    public void testGetListByVoParamError(ClientBeanVo vo) throws Exception {
        vo.setStart(null);

        clientService.getListByVo(vo);
    }


    @Test(dataProvider = "clientBeanList")
    public void testSaveList(ClientBean clientBean) throws Exception {
        clientService.save(clientBean);
    }


    @Test(dataProvider = "clientBean")
    public void testUpdate(ClientBean clientBean) throws Exception {
        clientBean.setId(1);
        clientService.updateNotNull(clientBean);
    }


}