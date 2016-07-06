package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.core.bean.KeychainBean;
import com.comodin.fleet.service.IKeychainService;
import test.BaseTest;
import com.comodin.fleet.vo.KeychainBeanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by supeng on 05/25/2016.
 */
public class KeychainServiceTest extends BaseTest {

    @Autowired
    IKeychainService keychainService;

    @Test
    public void testGetListByVo() throws Exception {
        KeychainBeanVo vo = new KeychainBeanVo();
        vo.setStart(0);
        vo.setLength(10);

        List<KeychainBean> list = keychainService.getListByVo(vo);

        System.out.println(JSON.toJSONString(list));
    }


    @Test(expectedExceptions = {BusinessLogicException.class})
    public void testSave() throws Exception {
        KeychainBean keychainBean = new KeychainBean();
        keychainBean.setName("test10001");
        keychainService.save(keychainBean);
    }

}