package com.comodin.fleet.portal.quartz;

import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.service.IClientBranchService;
import com.comodin.fleet.service.ITaskService;
import com.comodin.fleet.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class SystemGeneratesTasksJob {
    private static final Logger log = Logger.getLogger(SystemGeneratesTasksJob.class);

    @Autowired
    private IClientBranchService clientBranchService;
    @Autowired
    private ITaskService taskService;



    public void doIt() throws ParseException {
        log.info("SystemGeneratesTasksJob start.....");
        ClientBranchBean branchRecord = new ClientBranchBean();
        branchRecord.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
        List<ClientBranchBean> branchBeanList = clientBranchService.select(branchRecord);

        if (branchBeanList == null) {
            log.error(" Database does not have any information about the current client organization .....");
            return;
        }

        for (ClientBranchBean branchBean : branchBeanList) {
            String branchBeanInfo = "branchBean ---------id: " + branchBean.getId() + " ---------name: " + branchBean.getName();

            String cronExpressionStr = branchBean.getCronExpression();
            if (StringUtils.isEmpty(cronExpressionStr)) {
                log.error(branchBeanInfo + " branchBean.cronExpression is Empty " + branchBean.getCronExpression());
                continue;
            }


            Date currentDate = DateUtil.timeStampToDate(String.valueOf(new Date().getTime()), ConstantsFinalValue.DATE_FORMAT);
            //校验，当前客户的分支机构中的，周期计划，是否是与当前服务器日期符合；若不符合即退出；
            boolean validCronExpression = validCronExpression(cronExpressionStr, currentDate);
            if (!validCronExpression) {
                log.error(branchBeanInfo + " branchBean.cronExpression validCronExpression Error " + branchBean.getCronExpression());
                continue;
            }

            Integer currentOperatorId = ConstantsFinalValue.TASK_CREATE_TYPE_SYSTEM_CODE;
            String currentOperatorName = ConstantsFinalValue.TASK_CREATE_TYPE_SYSTEM_MARK;
            Integer clientId = branchBean.getClientId();
            Integer clientBranchId = branchBean.getId();
            String executionDate = String.valueOf(new Date().getTime());
            String taskType = ConstantsFinalValue.TASK_TYPE_RECEIVE_MONEY;

            try {
                taskService.addTask(currentOperatorId, currentOperatorName, clientId, clientBranchId, executionDate, taskType);
            } catch (BusinessLogicException e) {
                log.error(branchBeanInfo + " add task Error " + e.getMessage());
            }

        }
    }

    private boolean validCronExpression(String cronExpressionStr, Date currentDate) {
        try {
            CronExpression expression = new CronExpression(cronExpressionStr);
            return expression.isSatisfiedBy(currentDate);
        } catch (ParseException e) {
            log.error(" branchBean.cronExpression Expression Error: " + cronExpressionStr + " e: " + e.getMessage());
            return false;
        }
    }
}
