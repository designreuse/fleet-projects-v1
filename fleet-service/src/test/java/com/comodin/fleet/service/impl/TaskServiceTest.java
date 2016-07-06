package com.comodin.fleet.service.impl;

import com.comodin.fleet.service.ITaskService;
import test.BaseTest;
import com.comodin.fleet.vo.TaskBeanVo;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskServiceTest extends BaseTest {

    @Inject
    private ITaskService taskService;

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetListByVo() throws Exception {
        TaskBeanVo vo = new TaskBeanVo();
        vo.setStart(0);
        vo.setLength(10);
        taskService.getListByVo(vo);
    }

    @Test
    public void testSaveTaskByClientIdAndClientBranchId() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date executionDate = sdf.parse("2016-05-17");
        //taskService.saveTaskByClientIdAndClientBranchId(2, 2, 7, executionDate);
    }

}