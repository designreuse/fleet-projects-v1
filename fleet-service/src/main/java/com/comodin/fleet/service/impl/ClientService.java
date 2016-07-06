package com.comodin.fleet.service.impl;

import com.alibaba.fastjson.JSON;
import com.comodin.fleet.basic.exception.BusinessLogicException;
import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.BaseService;
import com.comodin.fleet.constants.ConstantsFinalValue;
import com.comodin.fleet.core.bean.ClientBean;
import com.comodin.fleet.core.bean.ClientBranchBean;
import com.comodin.fleet.core.bean.TaskBean;
import com.comodin.fleet.service.IClientBranchService;
import com.comodin.fleet.service.IClientService;
import com.comodin.fleet.service.ITaskService;
import com.comodin.fleet.service.ITaskTransactionService;
import com.comodin.fleet.util.FleetBasiUtil;
import com.comodin.fleet.vo.ClientBeanVo;
import com.comodin.fleet.vo.ClientBranchBeanVo;
import com.comodin.fleet.vo.TaskBeanVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class ClientService extends BaseService<ClientBean> implements IClientService {
	private static final Logger log = Logger.getLogger(ClientService.class);

	@Autowired
	private ITaskService taskService;
	@Autowired
	private IClientBranchService clientBranchService;
	private ITaskTransactionService taskTransactionService;

	@Override
	public List<ClientBean> getListByVo(ClientBeanVo vo) {
		log.info("service parameters vo JSON: " + JSON.toJSONString(vo));

		if (vo == null || vo.getStart() == null || vo.getLength() == null) {
			log.info("Query parameter error, check paging start length");
			throw new ParameterException("Query parameter error, check paging start length");
		}

		Example example = new Example(ClientBean.class);
		Example.Criteria criteria = example.createCriteria();

		if (StringUtils.isNotBlank(vo.getName())) {
			criteria.andLike("name", FleetBasiUtil.likePercent(vo.getName().trim()));
		}
		if (StringUtils.isNotBlank(vo.getPhone())) {
			criteria.andLike("phone", FleetBasiUtil.likePercent(vo.getPhone().trim()));
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

	@Override
	public List<ClientBean> getListByClientNameLike(String clientName) throws ParameterException {
		log.info("service parameters clientName: " + clientName);

		if (StringUtils.isBlank(clientName)) {
			throw new ParameterException("Query parameter error, check  clientName is null");
		}

		ClientBeanVo vo = new ClientBeanVo();
		vo.setStart(0);
		vo.setLength(5);
		vo.setName(clientName.trim());
		return getListByVo(vo);
	}

	@Override
	public List<TaskBean> getListTaskByClientId(Integer currentOperatorId, Integer clientId, TaskBeanVo vo) {
		log.info("service parameters currentOperatorId: " + currentOperatorId);
		log.info("service parameters clientId: " + clientId);

		vo.setClientId(clientId);
		return taskService.getListByVo(vo);
	}

	@Override
	public TaskBean getTaskDetailsByTaskId(Integer currentOperatorId, Integer clientId, Integer taskId) {
		log.info("service parameters clientId: " + clientId);
		log.info("service parameters taskId: " + taskId);

		// 检验，参数是否有效。
		if (clientId == null) {
			log.error("request  parameter error, clientId is null.");
			throw new ParameterException("clientId is null");
		}
		if (taskId == null) {
			log.error("request  parameter error, taskId is null.");
			throw new ParameterException("taskId is null");
		}

		// 1、根据 taskId 调用 Task Service，判断是否有该任务ID
		TaskBean taskBean = taskService.selectByPrimaryKey(taskId);
		// 1.1、若不存在，抛出 业务逻辑异常，代表传递的任务不存在。
		if (taskBean == null) {
			log.error("Current Task does not exist.");
			throw new BusinessLogicException("Current Task does not exist.");
		}

		// 2、判断：参数中clientId 是否与，task对象中.clientId，是否一至；
		if (taskBean.getClientId() == null) {
			log.error("Current Task unassigned client");
			throw new BusinessLogicException("Current Task unassigned client");
		}
		// 2.1、若不一致，抛出 业务逻辑异常，该任务不是当前司机客户的任务
		if (clientId.intValue() != taskBean.getClientId().intValue()) {
			log.error("The current client: " + clientId + " do not have this task: " + taskBean.getClientId());
			throw new BusinessLogicException("The current client do not have this task");
		}

		return taskBean;
	}

	@Override
	public List<ClientBranchBean> getListClientBranchByClientId(Integer currentOperatorId, Integer clientId,
			ClientBranchBeanVo vo) {
		log.info("service parameters clientId: " + clientId);
		log.info("service parameters vo JSON: " + JSON.toJSONString(vo));

		return clientBranchService.getListClientBranchByClientId(clientId, vo);

	}

	@Override
	public ClientBean save(ClientBean entity) {
		log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

		if (entity == null) {
			log.error("parameter error, check  entity is null");
			throw new ParameterException("parameter error, check  entity is null");
		}

		if (StringUtils.isEmpty(entity.getName())) {
			log.error("parameter error, check  name is null");
			throw new ParameterException("parameter error, check name is null");
		}

		ClientBean record = new ClientBean();
		record.setName(entity.getName().trim());
		int selectCount = mapper.selectCount(record);
		if (selectCount > 0) {
			log.error("Current Client Name already exists, please re-enter another name.");
			throw new BusinessLogicException("Current Client Name already exists, please re-enter another name.");
		}

		entity.setCreateDateTime(new Date());
		entity.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
		return super.save(entity);
	}

	@Override
	public void updateNotNull(ClientBean entity) {
		log.info("service parameters entity JSON: " + JSON.toJSONString(entity));

		if (entity == null) {
			log.error("parameter error, check  entity is null");
			throw new ParameterException("parameter error, check  entity is null");
		}

		if (StringUtils.isEmpty(entity.getName())) {
			log.error("parameter error, check  name is null");
			throw new ParameterException("parameter error, check name is null");
		}

		if (entity.getId() == null) {
			log.error("parameter error, check  Client is null");
			throw new ParameterException("parameter error, check Client is null");
		}

		ClientBean record = new ClientBean();
		record.setId(entity.getId());
		record.setDeleteFlag(ConstantsFinalValue.DELETE_FLAG_NORMAL);
		int selectCount = mapper.selectCount(record);
		if (selectCount != 1) {
			log.error("Current Client does not exist");
			throw new BusinessLogicException("Current Client does not exist");
		}

		Example example = new Example(ClientBean.class);
		example.createCriteria().andEqualTo("deleteFlag", ConstantsFinalValue.DELETE_FLAG_NORMAL)
				.andNotEqualTo("id", entity.getId()).andEqualTo("name", entity.getName().trim());
		selectCount = mapper.selectCountByExample(example);
		if (selectCount > 0) {
			log.error("Change the current Client name already exists, please re-enter another name");
			throw new BusinessLogicException(
					"Change the current Client name already exists, please re-enter another name");
		}

		super.updateNotNull(entity);
	}
}
