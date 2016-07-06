package com.comodin.fleet.service.impl;

import com.comodin.fleet.service.ITaskTransactionService;
import test.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class TaskTransactionServiceTest extends BaseTest {

    @Inject
    private ITaskTransactionService taskTransactionService;

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetListAllByTaskId() throws Exception {
        taskTransactionService.getListAllByTaskId(1);
    }

}