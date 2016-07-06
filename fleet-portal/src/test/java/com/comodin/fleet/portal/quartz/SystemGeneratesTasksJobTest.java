package com.comodin.fleet.portal.quartz;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.BaseTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SystemGeneratesTasksJobTest extends BaseTest {

    @Autowired
    private SystemGeneratesTasksJob systemGeneratesTasksJob;

    @DataProvider(name = "cronExpressionList")
    public Object[][] userBeans() {
        List<String> cronExpressionList = new ArrayList<>();
//        cronExpressionList.add("10	6		*	*	*	?");//#每天早上6点10分
//        cronExpressionList.add("0	*/2		*	*	*	?");//#每两个小时
//        cronExpressionList.add("0	23-7/2，8	*	*	*	?");//#晚上11点到早上8点之间每两个小时，早上8点
//        cronExpressionList.add("0	11		4	*	1-3	?");//#每个月的4号和每个礼拜一到礼拜三的早上11点
//        cronExpressionList.add("0	4		1	1	*	?");//#1月1日早上4点
//        cronExpressionList.add("30	21		*	*	*	?");//#每晚的21:30
//        cronExpressionList.add("45	4		1,10,22	*	*	?");//#每月1、10、22日的4 : 45
//        cronExpressionList.add("10	1		*	*	6,7	?");//#每周六、周日的1 : 10
//        cronExpressionList.add("0,30	18-23		*	*	*	?");//#每天18 : 00至23 : 00之间每隔30分钟
//        cronExpressionList.add("0	23		*	*	6	?");//#每星期六的11 : 00 pm
//        cronExpressionList.add("*	*/1		*	*	*	?");//#每一小时
//        cronExpressionList.add("*	23-7/1		*	*	*	?");//#晚上11点到早上7点之间，每隔一小时
//        cronExpressionList.add("0	11		4	*	1-3	?");//#每月的4号与每周一到周三的11点
//        cronExpressionList.add("0	4		1	1	*	?");//#一月一号的4点重启

        //CronTrigger配置完整格式为： [秒] [分] [小时]      [日]   [月]     [周]    [年]
        cronExpressionList.add("* * * 1-5 * ? *");//#每个月1-5号
        cronExpressionList.add("* * * 1-5 1 ? *");//#每个月1-5号
        cronExpressionList.add("* * * ? * MON-FRI *");//#每周1-5
        cronExpressionList.add("* * * ? * SUN,TUE,THU, *");//#每周1,3,5
        cronExpressionList.add("* * * 1-9,11-31 * ? *");//每个月10号不执行

        Object[][] result = new Object[cronExpressionList.size()][];
        for (int i = 0, len = cronExpressionList.size(); i < len; i++) {
            result[i] = new Object[]{cronExpressionList.get(i)};
        }
        return result;
    }

    @Test(dataProvider = "cronExpressionList")
    public void testBuildExpression(String cronExpression) throws Exception {
        CronExpression expression = new CronExpression(cronExpression);
        System.out.println(expression.getCronExpression());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-1")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-2")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-3")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-4")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-5")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-6")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-7")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-8")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-9")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-10")));
        System.out.println(expression.isSatisfiedBy(format.parse("2016-05-11")));
    }

    @Test
    public void testDoIt() throws Exception {
        systemGeneratesTasksJob.doIt();
    }

}