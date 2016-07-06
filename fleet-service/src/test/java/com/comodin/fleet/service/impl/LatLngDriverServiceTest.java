package com.comodin.fleet.service.impl;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.service.IDriverService;
import com.comodin.fleet.service.ILatLngDriverService;
import com.comodin.fleet.util.date.DateUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import test.BaseTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by supeng on 06/08/2016.
 */
public class LatLngDriverServiceTest extends BaseTest {

    @Autowired
    private ILatLngDriverService listByDriverUsername;
    @Autowired
    private IDriverService driverService;

    @Test
    public void testListByDriverUsername() throws Exception {
        List<LatLngDriverBean> coordinatesDriverBeanList = listByDriverUsername.listByDriverUsername("driver7", "2016-06-16", "1465308112000");
        System.out.println(coordinatesDriverBeanList.size());
    }

    @Test
    public void testListByDriverUsernameLastLatLngTimeStamp() throws Exception {

        String currentDriver = "driver10";
        String currentDate = "2016-06-17";
        List<LatLngDriverBean> latLngDriverBeanList = listByDriverUsername.listByDriverUsername(currentDriver, currentDate, null);


        int countTotal = latLngDriverBeanList.size();
        int countNormal = 0;
        int countAbnormal = 0;
        List<String> listCountAbnormal = new ArrayList<>();
        int countRepeat = 0;
        List<LatLngDriverBean> listCountRepeat = new ArrayList<>();

        String startTime = "";
        String lastTime = "";

        Long lastTimeUploadLatLngTimestamp = null;
        for (LatLngDriverBean latLngDriverBean : latLngDriverBeanList) {
            Long currentTimeUploadLatLngTimestamp = latLngDriverBean.getPhoneUploadTimestamp().getTime();
            if (lastTimeUploadLatLngTimestamp == null) {
                lastTimeUploadLatLngTimestamp = currentTimeUploadLatLngTimestamp;
                startTime = DateUtil.timeStamp2Date(String.valueOf(currentTimeUploadLatLngTimestamp), null);
                countNormal++;
                continue;
            }

            if (lastTimeUploadLatLngTimestamp.equals(currentTimeUploadLatLngTimestamp)) {
                //重复记录
                countRepeat++;
                listCountRepeat.add(latLngDriverBean);
            } else {
                Long temp = lastTimeUploadLatLngTimestamp + 3000;
                if (!Objects.equals(temp, currentTimeUploadLatLngTimestamp)) {
                    countAbnormal++;
                    System.out.println("\t\t\t\t" + DateUtil.timeStamp2Date(String.valueOf(temp), null));
                    //System.out.println("currentTimeUploadLatLngTimestamp " + DateUtil.timeStamp2Date(String.valueOf(currentTimeUploadLatLngTimestamp), null));
                } else {
                    //丢失数据
                    countNormal++;
                    listCountAbnormal.add(DateUtil.timeStamp2Date(String.valueOf(temp), null));
                }
            }

            lastTimeUploadLatLngTimestamp = currentTimeUploadLatLngTimestamp;

            lastTime = DateUtil.timeStamp2Date(String.valueOf(currentTimeUploadLatLngTimestamp), null);
            System.out.println(currentTimeUploadLatLngTimestamp + "\t" + lastTime);
        }


        System.out.println("重复插入数据的，获取经纬度时间，数据");
        StringBuilder inTime = new StringBuilder();
        for (LatLngDriverBean latLngDriverBean : listCountRepeat) {
            String id = latLngDriverBean.getDriverName();
            String lat = latLngDriverBean.getPhoneLat();
            String lng = latLngDriverBean.getPhoneLng();
            String phoneUploadTimestamp = DateUtil.timeStamp2Date(String.valueOf(latLngDriverBean.getPhoneUploadTimestamp().getTime()), null);

            StringBuffer sb = new StringBuffer();
            sb.append("id: " + id)
                    .append("\t")
                    .append("lat " + lat)
                    .append("\t")
                    .append("lng " + lng)
                    .append("\t")
                    .append("phoneUploadTimestamp: " + phoneUploadTimestamp);
            System.out.println(sb.toString());

            inTime.append("'").append(phoneUploadTimestamp).append("'").append(",");
        }


        System.out.println("【开始时间：" + startTime + "   " + lastTime + "】" + "\n" +
                "上传：" + countTotal + "\n" +
                "正常：" + countNormal + " 百分率：" + LatLngDriverServiceTest.processingPercentage(countNormal, countTotal, 2) + "\n" +
                "丢失：" + countAbnormal + " 百分率：" + LatLngDriverServiceTest.processingPercentage(countAbnormal, countTotal, 2) + "\n" +
                "重复：" + countRepeat + " 百分率：" + LatLngDriverServiceTest.processingPercentage(countRepeat, countTotal, 2) + "\n"
        );


        if (inTime.length() < 1) {
            return;
        }
        StringBuilder sql = null;
        sql = new StringBuilder();
        System.out.println("#查询语句 条数：" + countTotal);
        sql.append("SELECT count(*) FROM t_latlng_driver WHERE latlng_driver_username = '")
                .append(currentDriver)
                .append("' AND latlng_phone_upload_timestamp >= '").append(startTime).append("' ")
                .append(" AND latlng_phone_upload_timestamp <= '").append(lastTime).append("';");
        System.out.println(sql);

        sql = new StringBuilder();
        System.out.println("#查询语句 条数：" + countTotal);
        sql.append("SELECT * FROM t_latlng_driver WHERE latlng_driver_username = '")
                .append(currentDriver)
                .append("' AND latlng_phone_upload_timestamp >= '").append(startTime).append("' ")
                .append(" AND latlng_phone_upload_timestamp <= '").append(lastTime).append("';");
        System.out.println(sql);


        sql = new StringBuilder();
        System.out.println("#所有，重复插入数据的，获取经纬度时间，数据  SQL语句 条数：" + countRepeat);
        sql.append("SELECT * FROM t_latlng_driver WHERE latlng_driver_username = '")
                .append(currentDriver)
                .append("' AND latlng_phone_upload_timestamp in (")
                .append(inTime.substring(0, inTime.length() - 1))
                .append(");");
        System.out.println(sql);

        System.out.println("\n\n");
    }

    @SuppressWarnings("Duplicates")
    @Test(expectedExceptions = BusinessLogicException.class)
    public void testVald() throws IOException {
        String latlngDriver1JSON = "";
        //latlngDriver1JSON = IOUtils.toString(DriverServiceTest.class.getClass().getResourceAsStream("/LatLngDriver1-2016-06-16 phone.json"), StandardCharsets.UTF_8);
        latlngDriver1JSON = IOUtils.toString(DriverServiceTest.class.getClass().getResourceAsStream("/LatLngDriver1-2016-06-16 server.json"), StandardCharsets.UTF_8);

        String driverUsername = "phone";
        String phoneImei = "phone";
        String driverLocalIp = "192.168.1.1";

        driverService.uploadPhoneLatLngData(driverUsername, phoneImei, driverLocalIp, latlngDriver1JSON);

    }


    private static String processingPercentage(double divisor, double dividend, Integer minimumFractionDigits) {
        //这里的数后面加“D”是表明它是Double类型，否则相除的话取整，无法正常使用
        double percent = divisor / dividend;
        //输出一下，确认你的小数无误
        //System.out.println("小数：" + percent);
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        minimumFractionDigits = (minimumFractionDigits == null) ? 2 : minimumFractionDigits;
        nt.setMinimumFractionDigits(minimumFractionDigits);
        //最后格式化并输出
        //System.out.println("百分数：" + nt.format(percent));
        return nt.format(percent);
    }

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(String.valueOf(sdf.parse("2016-06-16 10:58:09").getTime()));

        System.out.println(DateUtil.timeStamp2Date("1466045888822", null));
        System.out.println(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(new Date().getTime());
    }

}