package com.comodin.fleet.portal.quartz;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.BaseTest;

import javax.inject.Inject;

/**
 * Created by supeng on 2016/06/18.
 */
public class TestMonitorLatLngDriverJobTest extends BaseTest {

    @Inject
    TestMonitorLatLngDriverJob testMonitorLatLngDriverJob;

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitData() throws Exception {
        testMonitorLatLngDriverJob.doIt();
    }

}